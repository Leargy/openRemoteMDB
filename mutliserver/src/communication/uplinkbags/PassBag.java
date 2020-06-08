package communication.uplinkbags;

import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public final class PassBag extends UplinkBag {
    private final Selector SELECTOR;

    public PassBag(ServerSocketChannel registrar, Selector keyKeeper) {
        super(registrar);
        SELECTOR = keyKeeper;
    }

    @Override
    public ServerSocketChannel getChannel() { return (ServerSocketChannel) super.getChannel(); }

    public Selector getSelector() { return SELECTOR; }
}
