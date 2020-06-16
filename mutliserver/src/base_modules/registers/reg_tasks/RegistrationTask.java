package base_modules.registers.reg_tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uplink_bags.RegistrationBag;

import java.io.IOException;
import java.nio.channels.*;
import java.util.concurrent.Callable;

public class RegistrationTask implements Callable<SocketChannel> {
    private final ServerSocketChannel SERVER_CHANNEL;
    private final Logger logger = LoggerFactory.getLogger(RegistrationTask.class);

    public RegistrationTask(ServerSocketChannel serverChannel) {
        SERVER_CHANNEL = serverChannel;
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

//        SocketChannel configuredChannel = null;
//        logger.info("Trying configure client channel");
//        try {
//            configuredChannel = configureChannel(connectedChannel);
//        } catch (IOException ioException) {
//            logger.error("There were problems while configuring client channel");
//            throw new IOException();
//        }
//        logger.info("Client channel configured successfully");

//        SocketChannel registeredChannel = null;
//        try {
//            registeredChannel = registerChannel(configuredChannel);
//        } catch (ClosedChannelException closedChannelException) {
//            logger.error("There were problems while registering channel to selector");
//            throw new IOException();
//        }
//        logger.info("Client channel registered successfully");
//        return registeredChannel;
        return connectedChannel;
    }

    private SocketChannel acceptNewClient(ServerSocketChannel serverChannel) throws IOException {
        System.out.println(serverChannel.isBlocking());
        return serverChannel.accept();
    }

//    private SocketChannel configureChannel(SocketChannel connectedChannel) throws IOException {
//        return (SocketChannel) connectedChannel.configureBlocking(false);
//    }

//    private SocketChannel registerChannel(SocketChannel configuredChannel) throws ClosedChannelException {
//        configuredChannel.register( SelectionKey.OP_READ);
//        return configuredChannel;
//    }
}
