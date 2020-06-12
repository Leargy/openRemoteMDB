package base_modules.registers.reg_tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uplink_bags.RegistrationBag;

import java.io.IOException;
import java.nio.channels.*;
import java.util.concurrent.Callable;

public class RegistrationTask implements Callable<SocketChannel> {
    private final ServerSocketChannel SERVER_CHANNEL;
    private final Selector SELECTOR;
    private final Logger logger = LoggerFactory.getLogger(RegistrationTask.class);

    public RegistrationTask(ServerSocketChannel serverChannel, Selector selector) {
        SERVER_CHANNEL = serverChannel;
        SELECTOR = selector;
    }

    public RegistrationTask(RegistrationBag registrationBag) {
        this(registrationBag.getChannel(), registrationBag.getServerSelector());
    }

    @Override
    public SocketChannel call() throws Exception {
        SocketChannel connectedChannel = null;
        logger.info("Trying accept new client");
        try {
            connectedChannel = acceptNewClient(SERVER_CHANNEL);
        } catch (IOException ioException) {
            logger.error("There were problems while accepting new channel");
            throw new IOException();
        }
        logger.info("Internet connection installed successfully");
        logger.info("Connected client data: " + connectedChannel.socket().toString());

        SocketChannel configuredChannel = null;
        logger.info("Trying configure client channel");
        try {
            configuredChannel = configureChannel(connectedChannel);
        } catch (IOException ioException) {
            logger.error("There were problems while configuring client channel");
            throw new IOException();
        }
        logger.info("Client channel configured successfully");

        SocketChannel registeredChannel = null;
        try {
            registeredChannel = registerChannel(configuredChannel, SELECTOR);
        } catch (ClosedChannelException closedChannelException) {
            logger.error("There were problems while registering channel to selector");
            throw new IOException();
        }
        logger.info("Client channel registered successfully");
        return registeredChannel;
    }

    private SocketChannel acceptNewClient(ServerSocketChannel serverChannel) throws IOException {
        return serverChannel.accept();
    }

    private SocketChannel configureChannel(SocketChannel connectedChannel) throws IOException {
        return (SocketChannel) connectedChannel.configureBlocking(false);
    }

    private SocketChannel registerChannel(SocketChannel configuredChannel, Selector selector) throws ClosedChannelException {
        configuredChannel.register(selector, SelectionKey.OP_READ);
        return configuredChannel;
    }
}
