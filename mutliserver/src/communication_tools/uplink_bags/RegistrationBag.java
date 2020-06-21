package communication_tools.uplink_bags;

import java.nio.channels.SocketChannel;
import java.nio.channels.spi.AbstractSelectableChannel;

public class RegistrationBag extends ChanneledBag {

    @Override
    public SocketChannel getChannel() {
        return (SocketChannel) super.getChannel();
    }

    public RegistrationBag(SocketChannel clientChannel) {
        super(clientChannel);
    }
}
