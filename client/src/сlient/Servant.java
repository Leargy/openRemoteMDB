package сlient;

import communication.Report;
import dataSection.enumSection.Markers;
import communication.Mediating;
import communication.Segment;
import dispatching.validators.Filters;

/**
 * Класс через который вызывается метод клиентского класса для создания подключения с сервером.
 * Также этот класс принимает ввод от юзера.
 *  @author Come_1LL_F00 aka Lenar Khannanov
 *  @author Leargy aka Anton Sushkevich
 * @see AServant
 */
public class Servant extends AServant {
    private String ip = "";
    private int port =0;
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
        port = Filters.scanInt((x)->(x > 48654 && x < 65536),"Enter server's port: ", scanner);
        client = mediator.getClient();
        if (resetConnection()) {
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
    public boolean resetConnection() {
        int tick = 3;
        while (true) {
            try {
                client.killSocket();
            } catch (NullPointerException e) { /*NOPE*/}
            if (client.connect(ip, port)) break;
            if (--tick == -1) {
                System.err.println("Server time out! \n" +
                        "Enter \"ip\" and \"port\" again.");
                setConnection();
            }
            String answer;
            while (true) {
                pipeOut.println("Retry connection [y/n]?");
                answer = debrief();
                if (answer.equals("y")) break;
                else if (answer.equals("n")) {
                    mediator.notify(this, new Segment(Markers.STOP));
                } else continue;
            }
        }
        return true;
    }

    /**
     * Метод, принимающий сообщение от клиента и уведомление посредника для выполнения следующего действия.
     * @param parcel
     */
    @Override
    public void order(Segment parcel) {
        String orderData = debrief();
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
        while (true) {
            pipeOut.print(">");
            stringData = scanner.nextLine();
            if (stringData.equals("")) {
                continue;
            }
            else {
                break;
            }
        }
        return stringData;
    }

    /**
     * метод по выводу ответа от сервера.
     * @param parcel
     */
    @Override
    public void notification(Segment parcel) {
        Report serverReport = parcel.getClientPackage().getReport();
        if (serverReport.isSuccessful()) {
            pipeOut.println("Server> " +serverReport.Message());
        }
        else {
            System.err.printf("Server error> " +serverReport.Message());
        }
    }
}
