package communication;

import data_section.enumSection.Markers;
import dispatching.ADispatcher;
import instructions.rotten.base.RawClear;
import instructions.rotten.base.RawInfo;
import instructions.rotten.base.RawInsert;
import instructions.rotten.base.RawRemoveKey;
import receiver.AReceiver;
import sample.dialog_windows.communication.ApplicationParcel;
import sample.dialog_windows.communication.BorderConverter;
import сlient.Client;
import сlient.Servant;
import dispatching.Dispatcher;
import receiver.Receiver;
import сlient.AServant;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Класс организации обращений разных частей программы друг к другу.(паттерн "Посредник")
 * @author Come_1LL_F00 aka Lenar Khannanov
 * @author Leargy aka Anton Sushkevich
 */
public class Mediator implements Mediating {
    public volatile Client client;
    public final AReceiver RECEIVER;
    public final ADispatcher DISPATCHER;
    public final AServant SERVANT;
    private sample.dialog_windows.communication.Mediating applicationMediator;
    public final BorderConverter borderConverter;

    public Mediator() {
        client = new Client(this);
        RECEIVER = new Receiver(this);
        DISPATCHER = new Dispatcher(this);
        SERVANT = new Servant(this);
        borderConverter = BorderConverter.getInstance();
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

    @Override
    public void setApplicationMediator(sample.dialog_windows.communication.Mediating applicationMediator) {
        this.applicationMediator = applicationMediator;
    }

    /**
     * Метод определющий свзязи между молдулями.Выясняет какому модулю направить обращение.
     * @param component
     * @param parcel
     * @throws IOException
     */
    @Override
    public void notify(Component component, Segment parcel) {
//        if(parcel.getClientPackage() != null) System.out.println(parcel.getClientPackage().getCommand());
        if (component == SERVANT && parcel.getMarker() == Markers.SIGNALSTAGE) {
            ApplicationParcel applicationParcel = borderConverter.convertToApplicationPackage(parcel);
            applicationMediator.notify(null, applicationParcel);
        }

        if (component == RECEIVER && parcel.getMarker() == Markers.UPDATE) {
            client.setInputCondition(true);
//            System.out.println(System.currentTimeMillis() + " " + Thread.currentThread().getName() + " " + parcel.getClientPackage().getCommand().getClass());
            if (parcel.getClientPackage().getCommand() instanceof RawInsert) parcel.setMarker(Markers.INSERT);
            else if (parcel.getClientPackage().getCommand() instanceof RawClear) parcel.setMarker(Markers.CLEAR);
            else if (parcel.getClientPackage().getCommand() instanceof RawRemoveKey) parcel.setMarker(Markers.REMOVE);
            else if (parcel.getClientPackage().getCommand() instanceof RawInfo) parcel.setMarker(Markers.INFO);
            ApplicationParcel applicationParcel = borderConverter.convertToApplicationPackage(parcel);
            applicationMediator.notify(null, applicationParcel);
        }
        if (component == null && parcel.getMarker() == Markers.WRITE) {
            Segment tempSegment = new Segment(client.getSocketChannel(),Markers.WRITE);
            tempSegment.setStringData(parcel.getStringData());
            parcel = tempSegment;
        }
        if (component == null && parcel.getMarker() == Markers.INTERRUPTED) client.killSocket();
        if (component == RECEIVER && parcel.getMarker() == Markers.WAIKUP) client.setInputCondition(true);
        if (component == RECEIVER && parcel.getMarker() == Markers.HASSERVERKEY) ((Dispatcher)DISPATCHER).setServerKey(parcel.getClientPackage().getReport().Message());
        if (component == client && parcel.getMarker() == Markers.INTERRUPTED) {
            ((Servant)SERVANT).setIsIncoming(true);
//            SERVANT.resetConnection(true);
        }
        if ((component == SERVANT || component == null) && parcel.getMarker() == Markers.WRITE) DISPATCHER.giveOrder(parcel);
        if (component == SERVANT && parcel.getMarker() == Markers.GOODINPUTCONDITION) applicationMediator.notify(null, borderConverter.convertToApplicationPackage(parcel));
        if (component == DISPATCHER && parcel.getMarker() == Markers.GOODINPUTCONDITION) {
            client.setInputCondition(true);
        }
        if (component == DISPATCHER && parcel.getMarker() == Markers.BADINPUTCONDITION) {
            client.setInputCondition(false);
            applicationMediator.notify(null, borderConverter.convertToApplicationPackage(parcel));
        }
        if (component == DISPATCHER && parcel.getMarker() == Markers.INTERRUPTED) {
            ((Servant)SERVANT).setIsIncoming(true);
//            SERVANT.resetConnection(true);
        }
        if ((component == DISPATCHER || component == SERVANT || component == null ) && parcel.getMarker() == Markers.STOP) client.stopAndClose();
        if (component == client  && parcel.getMarker() == Markers.WRITE) SERVANT.order(parcel);
        if (component == client && parcel.getMarker() == Markers.READ) RECEIVER.receive(parcel);
        if (component == RECEIVER && parcel.getMarker() == Markers.INTERRUPTED) {
            client.killSocket();
            ApplicationParcel applicationParcel = borderConverter.convertToApplicationPackage(parcel);
            applicationMediator.notify(null, applicationParcel);
            ((Servant)SERVANT).setIsIncoming(true);
//            SERVANT.resetConnection(true);
            DISPATCHER.confirm(false);
        }
        if (component == RECEIVER && parcel.getMarker() == Markers.WRITE) {
            if (parcel.getClientPackage().getReport().isSuccessful() == false) {
                parcel.setMarker(Markers.BADINPUTCONDITION);
                ApplicationParcel applicationParcel =  borderConverter.convertToApplicationPackage(parcel);
                Thread appThread = new Thread(() -> applicationMediator.notify(null, applicationParcel));
                appThread.setDaemon(true);
                appThread.start();
            }

            parcel = checkForFeatures(parcel);

            if (parcel.getMarker() == Markers.USER_DISCONNECTED || parcel.getMarker() == Markers.USER_CONNECTED) {
                ApplicationParcel applicationParcel =  borderConverter.convertToApplicationPackage(parcel);
                Thread appThread = new Thread(() -> applicationMediator.notify(null, applicationParcel));
                appThread.setDaemon(true);
                appThread.start();
                client.setInputCondition(true);
            }

            ((Servant)SERVANT).setIsIncoming(true);
            SERVANT.notification(parcel);
            client.setInputCondition(true);
        }
        if ((component == SERVANT || component == RECEIVER) && parcel.getMarker() == Markers.CONFIRMING) {
            client.setInputCondition(true);
            ApplicationParcel applicationParcel = borderConverter.convertToApplicationPackage(parcel);
            if (applicationParcel != null) {
                applicationMediator.notify(null, applicationParcel);
            }
        }
        if (component == RECEIVER  && parcel.getMarker() == Markers.CONFIRMING) DISPATCHER.confirm(parcel.getClientPackage().getReport().getIsConfirmed());
    }

    private Segment checkForFeatures(Segment parcel) {
        if (parcel.getClientPackage().getReport().Message().matches(".* вышел .*")) parcel.setMarker(Markers.USER_DISCONNECTED);
        if (parcel.getClientPackage().getReport().Message().matches(".* зашел .*")) parcel.setMarker(Markers.USER_CONNECTED);
        return parcel;
    }
}
