package communication_tools.uplink_bags;

import communication_tools.ClientPackage;

import java.nio.channels.SocketChannel;

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
