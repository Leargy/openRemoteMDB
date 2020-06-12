package base_modules.readers.readertasks;

import communication.ClientPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import patterns.mediator.Component;
import patterns.mediator.Controllers;
import uplink_bags.ClientPackBag;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ClientPackageRetrievingTask implements Component, Runnable {
    private static final int BUFFER_MAX_SIZE = 10240;
    private final ByteBuffer BYTE_BUFFER = ByteBuffer.allocate(BUFFER_MAX_SIZE);
    protected final Controllers CONTROLLER;
    protected final SocketChannel READY_CLIENT_CHANNEL;
    protected final Logger logger = LoggerFactory.getLogger(ClientPackageRetrievingTask.class);

    public ClientPackageRetrievingTask(Controllers controller, SocketChannel readyClientChannel) {
        CONTROLLER = controller;
        READY_CLIENT_CHANNEL = readyClientChannel;
    }

    @Override
    public void run() {
        ByteBuffer readBuffer = null;
        logger.info("Trying get data from client channel");
        try {
            readBuffer = readFromClientChannel(READY_CLIENT_CHANNEL);
        } catch (EOFException eofException) {
            logger.error("Reached the unexpected end of stream");
            return;
        } catch (IOException ioException) {
            logger.error("There were some errors while trying read client channel");
            return;
        }

        ClientPackage receivedPackage = null;
        logger.info("Trying parse received byte array");
        try {
            receivedPackage = readDataFromObjStream(readBuffer);
        } catch (ClassNotFoundException classNotFoundException) {
            logger.error("Sorry, but received class doesn't match any of recognizable classes");
            return;
        } catch (IOException ioException) {
            logger.error("There were some problems while trying read data from object stream");
            return;
        }

        CONTROLLER.notify(this, new ClientPackBag(READY_CLIENT_CHANNEL, receivedPackage));
    }

    protected ByteBuffer readFromClientChannel(SocketChannel readyClientChannel) throws IOException {
        BYTE_BUFFER.clear();
        if (readyClientChannel.read(BYTE_BUFFER) == -1)
            throw new EOFException();
        else return BYTE_BUFFER;
    }

    protected ClientPackage readDataFromObjStream(ByteBuffer filledBuffer) throws IOException, ClassNotFoundException {
        BYTE_BUFFER.flip();
        ClientPackage receivedPackage = null;
        try (
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(filledBuffer.array());
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)
        ) {
            receivedPackage = (ClientPackage) objectInputStream.readObject();
        }
        return receivedPackage;
    }

    @Override
    public Controllers getController() { return CONTROLLER; }
}
