package base_modules.dispatchers.sending_tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import patterns.mediator.Component;
import patterns.mediator.Controllers;
import uplink_bags.ClientPackBag;
import uplink_bags.NotifyBag;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

public class SendingResultsTask implements Component, Runnable {
    private static final int BUFFER_MAX_SIZE = 4 * 1024;
    private final ByteBuffer BYTE_BUFFER = ByteBuffer.allocate(BUFFER_MAX_SIZE);
    public final Controllers CONTROLLER;
//    public final NotifyBag NOTIFICATION;
    public final ClientPackBag CLIENTPACKBAG;
    public final Logger logger = LoggerFactory.getLogger(SendingResultsTask.class);

//    public SendingResultsTask(Controllers controller, NotifyBag notification) {
//        CONTROLLER = controller;
//        NOTIFICATION = notification;
//    }

    public SendingResultsTask(Controllers controller, ClientPackBag notification) {
        CONTROLLER = controller;
        CLIENTPACKBAG = notification;
    }

    @Override
    public void run() {
        logger.info("Trying to create byte and object stream");
        try (
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)
        ) {
            logger.info("Trying writing data into stream");
            objectOutputStream.writeObject(CLIENTPACKBAG.getClientPacket());
            objectOutputStream.flush();
            logger.info("Trying sending data to client");
            CLIENTPACKBAG.getChannel().write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
        } catch (IOException ioException) {
            logger.error("Unable to complete transaction to client");
            return;
        }
        logger.info("Transaction completed successfully");
    }

}
