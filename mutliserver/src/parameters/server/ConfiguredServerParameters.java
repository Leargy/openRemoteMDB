package parameters.server;

import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public final class ConfiguredServerParameters extends ServerParameters {
//    private final Selector SELECTOR;
    private final ServerSocketChannel SERVER_CHANNEL;

    public ConfiguredServerParameters(String address, int port, ServerSocketChannel serverChannel) {
        super(address, port);
//        SELECTOR = selector;
        SERVER_CHANNEL = serverChannel;
    }

    public ConfiguredServerParameters(ServerParameters params, ServerSocketChannel serverChannel) {
        this(params.getIPAddress(), params.getPort(), serverChannel);
    }

//    public Selector getSelector() {
//        return SELECTOR;
//    }

    public ServerSocketChannel getServerChannel() {
        return SERVER_CHANNEL;
    }
}
