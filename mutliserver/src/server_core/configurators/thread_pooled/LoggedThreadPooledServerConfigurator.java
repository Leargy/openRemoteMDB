package server_core.configurators.thread_pooled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parameters.server.BaseServerParameters;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;

public final class LoggedThreadPooledServerConfigurator extends ThreadPooledServerConfigurator {
    private final Logger LOG = LoggerFactory.getLogger(LoggedThreadPooledServerConfigurator.class);

    @Override
    protected ServerSocketChannel bindChannelWithBaseParameters(BaseServerParameters params, ServerSocketChannel openedChannel) {
        LOG.info(String.format("Trying bind server channel to address %s and port %d", params.ADDRESS, params.PORT));
        ServerSocketChannel bindedChannel = null;
        try {
             bindedChannel = openedChannel.bind(new InetSocketAddress(params.ADDRESS, params.PORT));
        } catch (IOException ioException) {
            LOG.error("Unable to bind server channel to port: " + params.PORT + " and address: " + params.ADDRESS);
            return null;
        }
        LOG.info("Successful binding server channel");
        return bindedChannel;
    }

    @Override
    protected ServerSocketChannel openServerChannel() {
        LOG.info("Trying opening server channel");
        ServerSocketChannel openedChannel = null;
        try {
            openedChannel = ServerSocketChannel.open();
        } catch (IOException ioException) {
            LOG.error("Unable to open server socket channel");
            return null;
        }
        LOG.info("ServerSocketChannel successfully opened");
        return openedChannel;
    }
}
