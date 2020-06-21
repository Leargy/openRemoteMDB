package parameters.server;

import java.nio.channels.ServerSocketChannel;

public class ConfiguredBaseServerParameters extends BaseServerParameters {
    public final ServerSocketChannel SERVER_CHANNEL;

    public ConfiguredBaseServerParameters(String address, int port, ServerSocketChannel serverChannel) {
        super(address, port);
        SERVER_CHANNEL = serverChannel;
    }

    public ConfiguredBaseServerParameters(BaseServerParameters params, ServerSocketChannel serverChannel) {
        this(params.ADDRESS, params.PORT, serverChannel);
    }
}
