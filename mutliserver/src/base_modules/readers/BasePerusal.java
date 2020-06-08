package base_modules.readers;

import communication.ClientPackage;
import communication.treasures.parameters.entities.UsersParameters;
import communication.uplinkbags.QueryBag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import patterns.mediator.Controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public final class BasePerusal implements Perusals {
    private final Logger logger = LoggerFactory.getLogger(BasePerusal.class);
    private final Controllers CONTROLLER;
    private final ByteBuffer BYTE_BUFFER = ByteBuffer.allocate(getMaximumByteBufferCapacity());
    
    public BasePerusal(Controllers controller) {
        CONTROLLER = controller;
    }
    
    @Override
    public Controllers getController() { return CONTROLLER; }

    @Override
    public UsersParameters retrieveUserParameters(SocketChannel readableChannel) {
        // clearing the buffer
        BYTE_BUFFER.clear();

        // 1 stage: reading from channel to buffer
        logger.info("Trying reading data from client channel");
        try {
            readFromClient(readableChannel, BYTE_BUFFER);
        } catch (IOException ioException) {
            logger.error("Failed reading data from client channel");
            return null;
        }

        // flip the byte buffer
        BYTE_BUFFER.flip();

        // 2 stage: creating byte buffer streams
        logger.info("Trying creating streams for reading from byte buffer");
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = createStreams2ReadObjectsFromBuffer(BYTE_BUFFER);
        } catch (IOException ioException) {
            logger.error("Failed to create streams from byte buffer");
            return null;
        }

        // 3 stage: reading real object from stream
        logger.info("Trying reading object from stream");
        ClientPackage query = null;
        try {
            query = readObjectFromStream(objectInputStream);
        } catch (ClassNotFoundException classNotFoundException) {
            logger.error("Received package isn't recognised");
            return null;
        } catch (IOException ioException) {
            logger.error("Unable to read received package");
            return null;
        }

        // 4 stage: forming user parameters
        return formUserParameters(query);
    }

    @Override
    public QueryBag retrieveClientQuery(SocketChannel readableChannel) {
        return null;
    }

    protected ByteBuffer readFromClient(SocketChannel clientChannel, ByteBuffer destination) throws IOException {
        if (clientChannel.read(destination) == -1)
            throw new IOException();
        else return destination;
    }

    protected ObjectInputStream createStreams2ReadObjectsFromBuffer(ByteBuffer buffer) throws IOException {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer.array());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
            return objectInputStream;
        }
    }

    protected ClientPackage readObjectFromStream(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        return (ClientPackage) objectInputStream.readObject();
    }

    protected UsersParameters formUserParameters(ClientPackage parcel) { throw new UnsupportedOperationException(); }
}
