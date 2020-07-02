package uplink_bags;

import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class RegistrationBag extends ChanneledBag {
    private ServerSocketChannel serverSocketChannel;
    public RegistrationBag(ServerSocketChannel someChannel) {
        super(someChannel);
    }
    @Override
    public ServerSocketChannel getChannel() {
        return (ServerSocketChannel) super.getChannel();
    }
}
