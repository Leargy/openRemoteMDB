package receiver;

import communication.ClientPackage;
import communication.Report;
import data_section.enumSection.Markers;
import communication.Mediating;
import communication.Segment;
import instructions.Decree;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Класс получатель, в метод принимает посылку от сервера и десериализует в пригодный для обработки вид.
 * @author Come_1LL_F00 aka Lenar Khannanov
 * @author Leargy aka Anton Sushkevich
 * @see AReceiver
 */
public class Receiver extends AReceiver{
    private ByteArrayInputStream byteArrayInputStream;
    private ObjectInputStream objectInputStream;
    private final ByteBuffer byteBuffer = ByteBuffer.allocate(20 * 1024);
    private Lock lock = new ReentrantLock();


    public Receiver(Mediating mediator){
        super(mediator);
    }

    /**
     * метод receive() - метод класса родителя, ответственене за десериализацию.
     * @param parcel
     */
    @Override
    public void receive(Segment parcel) {
        lock.lock();
        if(parcel.getSocketChannel().toString().matches(".*\\[closed\\]")){
//            System.out.println(Thread.currentThread().getName());
//            parcel.setMarker(Markers.WAIKUP);
//            mediator.notify(this, parcel);
            lock.unlock();
            return;
        }
        byteBuffer.clear();
        try {
//            System.out.println(System.currentTimeMillis() + " " + Thread.currentThread().getName());
            if(parcel.getSocketChannel().read(byteBuffer) == -1) {
//                mediator.notify(this, new Segment(Markers.WRITE));
                throw new EOFException();
            }
            mediator.notify(this, new Segment(Markers.WAIKUP));
            byteBuffer.flip();
            byteArrayInputStream = new ByteArrayInputStream(byteBuffer.array());
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
//            Report query = (Report) objectInputStream.readObject();
            ClientPackage result = (ClientPackage) objectInputStream.readObject();
            Report query = result.getReport();
            parcel.setClientPackage(result);
//            try {
//                System.out.println(System.currentTimeMillis() + " " + Thread.currentThread().getName() + " " + result.getCommand().getClass());//+ result.getCommand().getClass());
//                System.out.println(result.getReport().Message());//+ result.getCommand().getClass());
//            }catch (NullPointerException ex) {/**/}
            if (result.getCommand() != null) {
                parcel.setMarker(Markers.UPDATE);
                mediator.notify(this,parcel);
                return;
            }
            // Block of checking if user's login and pass was confirmed.
//            lock.unlock();
            if (query.Message().matches("SERVER_KEY:.*")) {
                checkForKey(query);
            }else {
                if (query.getIsConfirmed() != null) {
                    parcel.setMarker(Markers.CONFIRMING);
                    mediator.notify(this, parcel);
                }
                parcel.setMarker(Markers.WRITE);
                mediator.notify(this, parcel);
            }
        }catch (IOException ex) {
            if (!ex.getMessage().equals("null")){
                parcel.setStringData(new String[]{"Server closed connection!"});
            }
            parcel.setMarker(Markers.INTERRUPTED);
            mediator.notify(this, parcel);
        }catch (ClassNotFoundException ex) {
//            System.out.println(ex.getMessage());
            //TODO:write an handling to this type of error
        }
        finally {
            byteBuffer.clear();
            lock.unlock();
        }
    }

    private void checkForKey(Report report) {
        Segment segment = new Segment(Markers.HASSERVERKEY);
        segment.setClientPackage(new ClientPackage(null,report));
        mediator.notify(this, segment);
    }
}