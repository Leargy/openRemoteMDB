package uplink_bags;

import communication.ClientPackage;

import java.nio.channels.SocketChannel;
import java.nio.channels.spi.AbstractSelectableChannel;

public class ClientPackBag extends ChanneledBag {
    private final ClientPackage CLIENT_PACKET;

    public ClientPackBag(SocketChannel clientChannel, ClientPackage clientPackage) {
        super(clientChannel);
        CLIENT_PACKET = clientPackage;
    }

    public ClientPackage getClientPacket() { return CLIENT_PACKET; }
    @Override
    public SocketChannel getChannel() { return (SocketChannel) super.getChannel(); }
}
