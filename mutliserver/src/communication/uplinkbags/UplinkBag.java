package communication.uplinkbags;

import java.nio.channels.spi.AbstractSelectableChannel;

public class UplinkBag {
    protected final AbstractSelectableChannel CHANNEL;

    public UplinkBag(AbstractSelectableChannel channel) { CHANNEL = channel; }

    public AbstractSelectableChannel getChannel() { return CHANNEL; }
}
