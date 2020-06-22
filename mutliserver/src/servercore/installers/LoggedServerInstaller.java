package servercore.installers;

import parameters.server.ConfiguredServerParameters;
import parameters.server.ServerParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public final class LoggedServerInstaller extends ServerInstaller {
    private final Logger logger = LoggerFactory.getLogger(LoggedServerInstaller.class);

    @Override
    public ConfiguredServerParameters configureServer(ServerParameters params) {
        // 1 stage: open channel
        logger.info("Trying open server channel");
        ServerSocketChannel installingServerChannel = null;
        try {
            installingServerChannel = openServerChannel();
            logger.info("The server channel is opened");
        } catch (IOException ioException) {
            logger.error("Failed open the server socket channel");
            System.exit(ioException.hashCode());
        }

        // 2 stage: bind the channel
        logger.info("Trying to bind server channel");
        ServerSocketChannel bindedServerChannel = null;
        try {
            bindedServerChannel = bindServerChannel(params, installingServerChannel);
            logger.info("The server channel is binded");
        } catch (IOException ioException) {
            logger.error("Failed binding the server socket channel");
            System.exit(ioException.hashCode());
        }

        // 3 stage: configure server channel
//        logger.info("Trying configure server channel");
//        ServerSocketChannel configuredServerChannel = null;
//        try {
//            configuredServerChannel = configureServerChannel(bindedServerChannel);
//            logger.info("The server channel is configured");
//        } catch (IOException ioException) {
//            logger.error("Failed configuring server channel");
//            System.exit(ioException.hashCode());
//        }

        // 4 stage: open the selector
//        logger.info("Trying open server selector");
//        Selector openedSelector = null;
//        try {
//            openedSelector = openServerSelector();
//            logger.info("The server selector opened");
//        } catch (IOException ioException) {
//            logger.error("Failed opening server selector");
//            System.exit(ioException.hashCode());
//        }

        // 5 stage: register configured server channel to selector
//        logger.info("Trying register server channel to opened selector");
//        try {
//            registerServer2Selector(configuredServerChannel, openedSelector);
//            logger.info("Registering successful");
//        } catch (ClosedChannelException closedChannelException) {
//            logger.error("Sorry, but server channel has already closed");
//            System.exit(closedChannelException.hashCode());
//        }
        // 6 stage: returning new parameters
        return new ConfiguredServerParameters(params, bindedServerChannel);
//        return new ConfiguredServerParameters(params, openedSelector, configuredServerChannel);
    }
}
