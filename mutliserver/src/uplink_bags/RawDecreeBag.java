package uplink_bags;

import entities.User;
import instructions.rotten.RawDecree;

import java.nio.channels.SocketChannel;

public class RawDecreeBag extends ChanneledBag {
    public final RawDecree UNINVOKABLE_COMMAND;
    public final User USER;

    public RawDecreeBag(SocketChannel someChannel, RawDecree query,User user) {
        super(someChannel);
        UNINVOKABLE_COMMAND = query;
        USER = user;
    }

    @Override
    public SocketChannel getChannel() {
        return (SocketChannel) super.getChannel();
    }
}
