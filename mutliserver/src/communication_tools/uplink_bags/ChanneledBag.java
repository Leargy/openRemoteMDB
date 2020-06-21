package communication_tools.uplink_bags;

import java.nio.channels.spi.AbstractSelectableChannel;

public class ChanneledBag implements TransportableBag {
    private final AbstractSelectableChannel SOME_CHANNEL;

    public ChanneledBag(AbstractSelectableChannel someChannel) {
        SOME_CHANNEL = someChannel;
    }

    public AbstractSelectableChannel getChannel() {
        return SOME_CHANNEL;
    }
}
