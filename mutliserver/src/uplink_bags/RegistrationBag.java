package uplink_bags;

import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class RegistrationBag extends ChanneledBag {
    private final Selector SERVER_SELECTOR;
    public RegistrationBag(ServerSocketChannel someChannel, Selector selector) {
        super(someChannel);
        SERVER_SELECTOR = selector;
    }

    public Selector getServerSelector() {
        return SERVER_SELECTOR;
    }

    @Override
    public ServerSocketChannel getChannel() {
        return (ServerSocketChannel) super.getChannel();
    }
}
