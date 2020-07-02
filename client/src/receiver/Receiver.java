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
    private final ByteBuffer byteBuffer = ByteBuffer.allocate(3 * 1024);
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
        //System.out.println(Thread.currentThread().getName());
        lock.lock();
        if(parcel.getSocketChannel().toString().matches(".*\\[closed\\]")){
            parcel.setMarker(Markers.WAIKUP);
            mediator.notify(this, parcel);
            lock.unlock();
            return;
        }
        byteBuffer.clear();
        try {
            if(parcel.getSocketChannel().read(byteBuffer) == -1) {
                mediator.notify(this, new Segment(Markers.WRITE));
//                throw new EOFException();
            }
            byteBuffer.flip();
            byteArrayInputStream = new ByteArrayInputStream(byteBuffer.array());
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Report query = (Report) objectInputStream.readObject();
            parcel.setClientPackage(new ClientPackage(null, query));
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
            if (!ex.getMessage().equals("null")) System.out.println(ex.getMessage());
            parcel.setMarker(Markers.INTERRUPTED);
            mediator.notify(this, parcel);
        }catch (ClassNotFoundException ex) {
//            System.out.println(ex.getException());
            //TODO:write an handling to this type of error
        }
        finally {
            lock.unlock();
        }
    }

    private void checkForKey(Report report) {
        Segment segment = new Segment(Markers.HASSERVERKEY);
        segment.setClientPackage(new ClientPackage(null,report));
        mediator.notify(this, segment);
    }
}