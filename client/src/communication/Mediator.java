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
    private final AReceiver receiver;
    private final ADispatcher dispatcher;
    private final AServant servant;

    public Mediator() {
        client = new Client(this);
        receiver = new Receiver(this);
        dispatcher = new Dispatcher(this);
        servant = new Servant(this);
    }

    /**
     * Возвращает модуль ответственный за получение сообщений от клиента, установление объявления подключения к серверу.
     * @return AServant
     */
    public AServant getServant() {
        return servant;
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
        if (component == receiver && parcel.getMarker() == Markers.WRITE) servant.setIsReplying(true);
        if (component == client && parcel.getMarker() == Markers.INTERRUPTED) {
//            System.out.println(Thread.currentThread().getName() + " from client");
            ((Servant)servant).setIsIncoming(true);
            servant.resetConnection(true);
        }
        if (component == servant && parcel.getMarker() == Markers.WRITE) dispatcher.giveOrder(parcel);
        if (component == dispatcher && parcel.getMarker() == Markers.GOODINPUTCONDITION) {
            servant.setIsReplying(true);
            client.setInputCondition(true);
        }
        if (component == dispatcher && parcel.getMarker() == Markers.BADINPUTCONDITION) {
            servant.setIsReplying(false);
            client.setInputCondition(false);
        }
        if (component == dispatcher && parcel.getMarker() == Markers.INTERRUPTED) {
//            System.out.println(Thread.currentThread().getName() + " from dispatcher");
            ((Servant)servant).setIsIncoming(true);
            servant.resetConnection(true);
        }
        if ((component == dispatcher || component == servant) && parcel.getMarker() == Markers.STOP) client.stopAndClose();
        if (component == client && parcel.getMarker() == Markers.WRITE) servant.order(parcel);
        if (component == client && parcel.getMarker() == Markers.READ) receiver.receive(parcel);
        if (component == receiver && parcel.getMarker() == Markers.INTERRUPTED) {
//            System.out.println(Thread.currentThread().getName() + " from receiver");
            client.killSocket();
            ((Servant)servant).setIsIncoming(true);
//            servant.setIsReplying(false);
            servant.resetConnection(true);
        }
        if (component == receiver && parcel.getMarker() == Markers.WRITE) {
            ((Servant)servant).setIsIncoming(true);
//            System.out.println(Thread.currentThread().getName() + " me incoming");
            servant.notification(parcel);
        }
        if (component == receiver && parcel.getMarker() == Markers.CONFIRMING) {
            dispatcher.confirm(parcel.getClientPackage().getReport().isSuccessful());
            ((Servant)servant).setIsIncoming(true);
            servant.notification(parcel);
        }
    }
}
