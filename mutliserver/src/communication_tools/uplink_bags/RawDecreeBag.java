package communication_tools.uplink_bags;

import instructions.raw.RawDecree;

import java.nio.channels.SocketChannel;

public class RawDecreeBag extends ChanneledBag {
    private final RawDecree UNINVOKABLE_COMMAND;

    public RawDecreeBag(SocketChannel someChannel, RawDecree query) {
        super(someChannel);
        UNINVOKABLE_COMMAND = query;
    }

    public RawDecree getRawDecree() {
        return UNINVOKABLE_COMMAND;
    }

    @Override
    public SocketChannel getChannel() {
        return (SocketChannel) super.getChannel();
    }
}
