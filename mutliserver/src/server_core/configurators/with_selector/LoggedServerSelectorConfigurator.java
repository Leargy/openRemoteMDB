package server_core.configurators.with_selector;

import parameters.server.BaseServerParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public final class LoggedServerSelectorConfigurator extends ServerSelectorConfigurator {
    private final Logger LOG = LoggerFactory.getLogger(LoggedServerSelectorConfigurator.class);

    // 1 stage: open channel
    @Override
    protected final ServerSocketChannel openServerChannel() {
        LOG.info("Trying open server channel");
        ServerSocketChannel serverSocketChannel = null;
        try {
            serverSocketChannel = ServerSocketChannel.open();
            return serverSocketChannel;
        } catch (IOException ioException) {
            LOG.error("Failed opening the server socket channel");
            return null;
        }
    }

    // 2 stage: bind the channel
    @Override
    protected final ServerSocketChannel bindServerChannel(BaseServerParameters params, ServerSocketChannel openedChannel) {
        LOG.info("Trying bind server channel to address and port");
        try {
            return openedChannel.bind(new InetSocketAddress(params.ADDRESS, params.PORT));
        } catch (UnknownHostException unknownHostException) {
            LOG.error("Failed binding the server socket channel: host not found");
            return null;
        } catch (IOException ioException) {
            LOG.error("Failed binding the server socket channel: errors in streams");
            return null;
        }
    }

    // 3 stage: configure server channel
    @Override
    protected final ServerSocketChannel configureServerChannel(ServerSocketChannel bindedChannel) {
        LOG.info("Trying configure server channel in unblocking mode");
        try {
            return (ServerSocketChannel) bindedChannel.configureBlocking(false);
        } catch (IOException ioException) {
            LOG.error("Failed configuring server channel");
            return null;
        }
    }

    // 4 stage: open the selector
    @Override
    protected final Selector openServerSelector() {
        LOG.info("Trying open server selector");
        try {
            return Selector.open();
        } catch (IOException ioException) {
            LOG.error("Failed opening server selector");
            return null;
        }
    }

    // 5 stage: register configured server channel to selector
    @Override
    protected final void registerServer2Selector(ServerSocketChannel configuredServerChannel, Selector selector) {
        LOG.info("Trying register server to selector");
        try {
            configuredServerChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (ClosedChannelException closedChannelException) {
            LOG.error("Sorry, but server channel has already closed");
            return;
        }
    }
}
