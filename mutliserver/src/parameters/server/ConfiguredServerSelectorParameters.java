package parameters.server;

import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public final class ConfiguredServerSelectorParameters extends ConfiguredBaseServerParameters {
    private final Selector SELECTOR;

    public ConfiguredServerSelectorParameters(String address, int port, ServerSocketChannel serverChannel, Selector selector) {
        super(address, port, serverChannel);
        SELECTOR = selector;
    }

    public ConfiguredServerSelectorParameters(BaseServerParameters params, ServerSocketChannel serverChannel, Selector selector) {
        super(params, serverChannel);
        SELECTOR = selector;
    }
}
