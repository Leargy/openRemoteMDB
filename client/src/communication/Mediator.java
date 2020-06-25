package communication;

import data_section.enumSection.Markers;
import dispatching.ADispatcher;
import receiver.AReceiver;
import сlient.Client;
import сlient.Servant;
import dispatching.Dispatcher;
import receiver.Receiver;
import сlient.AServant;

import java.io.IOException;

/**
 * Класс организации обращений разных частей программы друг к другу.(паттерн "Посредник")
 * @author Come_1LL_F00 aka Lenar Khannanov
 * @author Leargy aka Anton Sushkevich
 */
public class Mediator implements Mediating {
    private volatile Client client;
    private final AReceiver RECEIVER;
    private final ADispatcher DISPATCHER;
    private final AServant SERVANT;

    public Mediator() {
        client = new Client(this);
        RECEIVER = new Receiver(this);
        DISPATCHER = new Dispatcher(this);
        SERVANT = new Servant(this);
    }

    /**
     * Возвращает модуль ответственный за получение сообщений от клиента, установление объявления подключения к серверу.
     * @return AServant
     */
    public AServant getServant() {
        return SERVANT;
    }

    /**
     * Возвращает модуль ответственный за установление/завершение подключения с сервером, и организаци принятия/отправления сообщения.
     * @return Client
     */
    @Override
    public Client getClient() {
        return client;
    }

    /**
     * Метод определющий свзязи между молдулями.Выясняет какому модулю направить обращение.
     * @param component
     * @param parcel
     * @throws IOException
     */
    @Override
    public void notify(Component component, Segment parcel) {
//        if (component == receiver && parcel.getMarker() == Markers.INTERRUPTED) client.dropReceives();
//        if (component == receiver && parcel.getMarker() == Markers.WRITE) servant.setIsReplying(true);
        if (component == client && parcel.getMarker() == Markers.INTERRUPTED) {
//            System.out.println(Thread.currentThread().getName() + " from client");
            ((Servant)SERVANT).setIsIncoming(true);
            SERVANT.resetConnection(true);
        }
        if (component == SERVANT && parcel.getMarker() == Markers.WRITE) DISPATCHER.giveOrder(parcel);
        if (component == DISPATCHER && parcel.getMarker() == Markers.GOODINPUTCONDITION) {
            SERVANT.setIsReplying(true);
            client.setInputCondition(true);
        }
        if (component == DISPATCHER && parcel.getMarker() == Markers.BADINPUTCONDITION) {
            SERVANT.setIsReplying(false);
            client.setInputCondition(false);
        }
        if (component == DISPATCHER && parcel.getMarker() == Markers.INTERRUPTED) {
//            System.out.println(Thread.currentThread().getName() + " from dispatcher");
            ((Servant)SERVANT).setIsIncoming(true);
            SERVANT.resetConnection(true);
        }
        if ((component == DISPATCHER || component == SERVANT) && parcel.getMarker() == Markers.STOP) client.stopAndClose();
        if (component == client && parcel.getMarker() == Markers.WRITE) SERVANT.order(parcel);
        if (component == client && parcel.getMarker() == Markers.READ) RECEIVER.receive(parcel);
        if (component == RECEIVER && parcel.getMarker() == Markers.INTERRUPTED) {
//            System.out.println(Thread.currentThread().getName() + " from receiver");
//            servant.setIsReplying(false);
            ((Servant)SERVANT).setIsIncoming(true);
            client.killSocket();
            SERVANT.resetConnection(true);
            DISPATCHER.confirm(false);
        }
        if (component == RECEIVER && parcel.getMarker() == Markers.WRITE) {
            ((Servant)SERVANT).setIsIncoming(true);
//            System.out.println(Thread.currentThread().getName() + " me incoming");
            SERVANT.notification(parcel);
            client.setInputCondition(true);
        }
        if (component == RECEIVER && parcel.getMarker() == Markers.CONFIRMING) {
            DISPATCHER.confirm(parcel.getClientPackage().getReport().getIsConfirmed());
//            ((Servant)servant).setIsIncoming(true);
//            servant.notification(parcel);
        }
    }
}
