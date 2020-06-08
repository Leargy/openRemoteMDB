package servercore.installers;

import communication.treasures.parameters.server.ConfiguredServerParameters;
import communication.treasures.parameters.server.ServerParameters;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class ServerInstaller {

    public ConfiguredServerParameters configureServer(ServerParameters params) {
        // 1 stage: open channel
        ServerSocketChannel installingServerChannel = null;
        try {
             installingServerChannel = openServerChannel();
        } catch (IOException ioException) {
            System.err.println("Failed open the server socket channel");
            System.exit(ioException.hashCode());
        }

        // 2 stage: bind the channel
        ServerSocketChannel bindedServerChannel = null;
        try {
            bindedServerChannel = bindServerChannel(params, installingServerChannel);
        } catch (IOException ioException) {
            System.err.println("Failed binding the server socket channel");
            System.exit(ioException.hashCode());
        }

        // 3 stage: configure server channel
        ServerSocketChannel configuredServerChannel = null;
        try {
            configuredServerChannel = configureServerChannel(bindedServerChannel);
        } catch (IOException ioException) {
            System.err.println("Failed configuring server channel");
            System.exit(ioException.hashCode());
        }

        // 4 stage: open the selector
        Selector openedSelector = null;
        try {
            openedSelector = openServerSelector();
        } catch (IOException ioException) {
            System.err.println("Failed opening server selector");
            System.exit(ioException.hashCode());
        }

        // 5 stage: register configured server channel to selector
        try {
            registerServer2Selector(configuredServerChannel, openedSelector);
        } catch (ClosedChannelException closedChannelException) {
            System.err.println("Sorry, but server channel has already closed");
            System.exit(closedChannelException.hashCode());
        }
        // 6 stage: returning new parameters
        return new ConfiguredServerParameters(params, openedSelector, configuredServerChannel);
    }

    protected final ServerSocketChannel openServerChannel() throws IOException {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            return serverSocketChannel;
        }
    }

    protected final ServerSocketChannel bindServerChannel(ServerParameters params, ServerSocketChannel openedChannel) throws UnknownHostException, IOException {
        return openedChannel.bind(new InetSocketAddress(params.getIPAddress(), params.getPort()));
    }

    protected final ServerSocketChannel configureServerChannel(ServerSocketChannel bindedChannel) throws IOException {
        return (ServerSocketChannel) bindedChannel.configureBlocking(false);
    }

    protected final Selector openServerSelector() throws IOException {
        try (Selector serverSelector = Selector.open()) {
            return serverSelector;
        }
    }

    protected final void registerServer2Selector(ServerSocketChannel configuredServerChannel, Selector selector) throws ClosedChannelException {
        configuredServerChannel.register(selector, SelectionKey.OP_ACCEPT);
    }
}
