package сlient;

import communication.InterruptingInputTask;
import communication.Report;
import data_section.enumSection.Markers;
import communication.Mediating;
import communication.Segment;
import dispatching.validators.Filters;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Класс через который вызывается метод клиентского класса для создания подключения с сервером.
 * Также этот класс принимает ввод от юзера.
 *  @author Come_1LL_F00 aka Lenar Khannanov
 *  @author Leargy aka Anton Sushkevich
 * @see AServant
 */
public class Servant extends AServant {
//    private Semaphore semaphore = new Semaphore(2,true);
    private Lock lock = new ReentrantLock();
//    private Lock writingLock = new ReentrantLock();
    private Condition receiveCondition = lock.newCondition();
//    private Condition waitingCondition = lock.newCondition();
    private volatile String ip = "";
    private volatile int port = 0;
    private volatile AtomicInteger counter = new AtomicInteger();
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private Future<String> future ;
    private boolean onExit = false;

    /**
     * Конструктор принимающий ссылку на посредника.
     * @param mediator
     */
    public Servant(Mediating mediator) {
        super(mediator);
    }
    //если очень захочется,можно будет задавать подключение через аргументы командной строки.

    /**
     * метод установки соединения
     * @return boolean
     */
    @Override
    public boolean setConnection() {
        ip = Filters.scanLine((x)->(x.matches("(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)"))
                ,"Enter server's ip: ",scanner);
        port = Filters.scanInt((x)->(x > 2400 && x < 65536),"Enter server's port: ", scanner); //48654
        client = mediator.getClient();
        counter.getAndIncrement();
        if (resetConnection(true)) {
            new Thread(client).start();
            return true;
        }
        return false;
    }

    /**
     * Метод, реализующий переустановку подключения к серверу.
     * @return boolean
     */
    @Override
    public boolean resetConnection(boolean droppedConnection) {
        if (onExit) return false;
        try {
            try {
                future.cancel(true);
            }catch (NullPointerException ex) {/*NOPE*/}
            lock.lock();
//            scanner.reset();
//            try {
//                Thread.sleep(200);
//            }catch (InterruptedException ex) { /*NOPE*/}
//            System.out.println(Thread.currentThread().getName() + " cl:" + client.isConnected());
            if (!client.isConnected()) {
//                System.out.println(counter.get());
                int tick = 3;
                while (true) {
//                    try {
//                        client.killSocket();
//                    } catch (NullPointerException e) { /*NOPE*/}
                    if (client.connect(ip, port)) break;
                    if (--tick == -1) {
                        System.err.println("Server time out! \n" +
                                "Enter \"ip\" and \"port\" again.");
                        setConnection();
                    }
                    String answer;
                    if (droppedConnection) {
                        System.err.println("────>Connection interrupted< <─w─");
                    }
                    while (true) {
//                        isReplying = false;
                        pipeOut.println("Retry connection [y/n]?");
//                        answer = debrief();
                        while (true) {
                            pipeOut.print(">");
                            answer = scanner.nextLine();
                            if (answer.equals("")) {
                                continue;
                            }
                            else {
                                break;
                            }
                        }
                        if (answer.equals("y")) break;
                        else if (answer.equals("n")) {
                            mediator.notify(this, new Segment(Markers.STOP));
                        } else continue;
                    }
                }
            }
//        }catch (InterruptedException ex){
            /*NOPE*/
        }finally {
//            System.out.println(Thread.currentThread().getName() + " cl:" + client.isConnected());
//            System.out.println(counter.get());
            counter.getAndDecrement();
            if (counter.get() == 0) {
                isReplying = false;
                receiveCondition.signal();
            }
            lock.unlock();
        }
        return true;
    }

    /**
     * Метод, принимающий сообщение от клиента и уведомление посредника для выполнения следующего действия.
     * @param parcel
     */
    @Override
    public synchronized void order(Segment segment) {
        // segment is not used because of the socketchannel sinhronization
        String orderData = debrief();
        if (orderData.equals("")) return;
        if (orderData.equals("exit")) onExit = true;
        Segment parcel = new Segment(client.getSocketChannel(),Markers.WRITE);
        parcel.setStringData(orderData.split(" "));
        //после получения пользовательской строки происходит обращение к модулю валидации введенных данных
        mediator.notify(this, parcel);
    }

    /**
     * Метод для общения с клиентом.
     * @return String
     */
    @Override
    public String debrief() {
        String stringData = "";
        try {
//            semaphore.acquire();
            lock.lock();
            receiveCondition.await(100,TimeUnit.MILLISECONDS);
//            System.out.println(Thread.currentThread().getName() + " 1 " + isReplying);
            if (isReplying) receiveCondition.await();
//            System.out.println(Thread.currentThread().getName() + " 1 " + isReplying);
            future = executorService.submit(new InterruptingInputTask());
            stringData = future.get();
            if (stringData.equals("")) Thread.currentThread().interrupt();
//            while (true) {
//                pipeOut.print(">");
//                stringData = scanner.nextLine();
//                if (stringData.equals("")) {
//                    continue;
//                }
//                else {
//                    break;
//                }
//            }
        }
        catch (Exception ex) {
//            future = null;
//            lock.unlock();
            return "";
//            System.out.println(ex.getMessage());
        }finally {
//            tempThreadTuskQueue.poll();
            future = null;
//            isReplying = true;
//            semaphore.release();
            lock.unlock();
        }
        return stringData;
    }

    /**
     * метод по выводу ответа от сервера.
     * @param parcel
     */
    @Override
    public void notification(Segment parcel) {
        try {
            try {
                future.cancel(true);
            }catch (NullPointerException ex) { /*NOPE*/ }
//            writingLock.lock();
            lock.lock();
            isReplying = true;
//            Thread.sleep(125);
            Report serverReport = parcel.getClientPackage().getReport();
            if (serverReport.isSuccessful()) {
                pipeOut.println("Server> " +serverReport.Message());
            }
            else {
                System.err.printf("Server error> " +serverReport.Message());
            }
        }
//        catch (InterruptedException ex) {
//        }
        finally {
            counter.getAndDecrement();
            if (counter.get() == 0) {
                isReplying = false;
//                System.out.println(scanner.hasNext());
//                scanner.reset();

                receiveCondition.signalAll();
            }
            lock.unlock();
//            writingLock.unlock();
        }
    }

    public void setIsIncoming(boolean isIncoming) {
        counter.getAndIncrement();
    }
}
