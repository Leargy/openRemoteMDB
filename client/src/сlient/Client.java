package сlient;

import communication.Component;
import data_section.enumSection.Markers;
import communication.Mediating;
import communication.Segment;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Класс модуля установления/завершение подключений, определения вектора действий.
 * @author Come_1LL_F00 aka Lenar Khannanov
 * @author Leargy aka Anton Sushkevich
 * @see AClient,Component,Runnable
 */
public class Client extends AClient implements Component, Runnable {
    private volatile SocketChannel socketChannel;
//    private Selector selector;
    private ExecutorService fixedTP;
    private ExecutorService cachedTP;
    private Lock lock = new ReentrantLock();
    private Condition replyCondition = lock.newCondition();
    private ByteBuffer byteBuffer = ByteBuffer.allocate(3 * 1024);
    private volatile boolean inputCondition = true;

    public synchronized SocketChannel getSocketChannel() {
        return socketChannel;
    }

    /**
     * Конструктор принимающий ссылку на посредника.
     * @param mediator
     */
    public Client(Mediating mediator) {
        super(mediator);
//        try {
//            selector = Selector.open();
//        } catch (IOException ex) {/*NOPE*/}
        cachedTP = Executors.newCachedThreadPool();
        fixedTP = Executors.newFixedThreadPool(3);
    }

    public synchronized boolean isConnected() {
        if (socketChannel != null) {
//            System.out.println((socketChannel.toString()).matches(".*\\[closed\\]"));
            return !(socketChannel.toString()).matches(".*\\[closed\\]");
        }else {
            return false;
        }
//        try {
//            System.out.println(socketChannel.toString().contains("j"));
//        }catch (NullPointerException ex) {/*NOPE*/}
//        finally {
//            return "";//false;
//        }
    }
    /**
     * Метод установки поключения.
     * @param hostName
     * @param serverPort
     * @return boolean
     */
    public synchronized boolean connect(String hostName,int serverPort){
        try {
            System.out.println("──────>Setting connection...");
            socketChannel = SocketChannel.open(new InetSocketAddress(hostName,serverPort));
            //socketChannel.configureBlocking(false);
//            while (!socketChannel.finishConnect()){
//                System.out.println("Waiting for connection...");
//            }
            System.out.println("──────>Connection is set<──────");
            System.out.println("Server ip: " + socketChannel.socket().getInetAddress().getHostAddress() + "\n"
                    + "Server port: " + socketChannel.socket().getPort());

            //socketChannel.register(selector,SelectionKey.OP_READ|SelectionKey.OP_WRITE);
            return true;
        } catch (UnknownHostException e) {
            return false;
        } catch (IOException e) {
//            System.err.println("──────>Connection is lost< <─w──");
            return false;
        }
    }

    /**
     * Метод, закрывающий сокет(поток сокета) клиента.
     */
    public synchronized void killSocket() {
        try {
            socketChannel.socket().close();
//            System.out.println(socketChannel.socket());
        } catch (IOException ex) {
            new IOException("Something went wrong during closing \"socket channel\"", ex);
        }
        //TODO:подумать над правильной обработкой остановки сервера.(TIME_OUT)
    }

    /**
     * Метод, использующий selector для определения дальнейшего вектора действий.
     */
    public void run() {
        genereteReplayCheckingTread();
        while (socketChannel.isConnected()) {
            lock.lock();
//            System.out.println("sending ask-task");
            //socket is not transmitted because of synchronization purposes
            fixedTP.submit(() -> mediator.notify(this, new Segment(Markers.WRITE)));
            try {
                replyCondition.await();
                if (!inputCondition) continue;
//                System.out.println("replay catcher released");
                genereteReplayCheckingTread();
//                cachedTP.submit(() -> mediator.notify(this, new Segment(socketChannel,Markers.READ)));

            }catch (InterruptedException ex ) {
                /*NOPE*/
            } finally {
                lock.unlock();
            }
            //TODO: Где-то происходит дедлок, найти исправить (после переподключения клиента к серверу)

//            try {
//                if(socketChannel.read(byteBuffer) == -1) {
//                    throw new EOFException();
//                }
//                mediator.notify(this,new Segment(byteBuffer,Markers.READ));
//            }catch (IOException ex) {
//                System.out.println("error while reading");
//                ex.getMessage();
//                mediator.notify(this, new Segment(Markers.INTERRUPTED));
//            }finally {
//                byteBuffer.clear();
//            }


//            lock.lock();
//            cachedTP.submit(() -> mediator.notify(this, new Segment((SocketChannel) socketChannel,Markers.READ)));
//            lock.unlock();


//            try {
//                Thread.sleep(50);
//            }catch (InterruptedException ex) {/*NOPE*/}
//            try {
//                if (selector.selectNow() == 0) continue;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            Set<SelectionKey> selectedKeys = selector.selectedKeys();
//            Iterator<SelectionKey> iter = selectedKeys.iterator();
//            while (iter.hasNext()) {
//                SelectionKey key = iter.next();
//                if (key.isReadable()) {
//                    //give thread the task
//                    cachedTP.submit(() -> mediator.notify(this, new Segment((SocketChannel) key.channel(),Markers.READ)));
//                }
//                //if connection was interrupted, the key has been deleted,so we should pass the writable check.
//                if(!key.isValid()) continue;
//                if (key.isWritable()) {
////                    mediator.notify(this, new Segment((SocketChannel) key.channel(), Markers.WRITE));
//                    //give thread the task
//                    //неудовлетворяет отрисовка приглошения к вводу
//                    fixedTP.submit(() -> mediator.notify(this, new Segment((SocketChannel) key.channel(), Markers.WRITE)));
//                }
//                iter.remove();
//            }
        }
    }
    public void genereteReplayCheckingTread() {
        cachedTP.submit(() -> mediator.notify(this, new Segment(socketChannel,Markers.READ)));
    }

    /**
     * Метод закрывающий сокет(поток сокета) и завершение работы приложения.
     * @throws IOException
     */
    public void stopAndClose() {
        try {
            cachedTP.shutdownNow();
            fixedTP.shutdownNow();
            socketChannel.shutdownInput();
            socketChannel.shutdownOutput();
            socketChannel.close();
        }catch (IOException e) {
            new IOException("Dropped an exception during closing socket.",e);
        }finally {
            System.exit(0);
        }
    }

    public void setInputCondition(boolean inputCondition) {
        lock.lock();
        this.inputCondition = inputCondition;
        replyCondition.signal();
        lock.unlock();
    }
}
