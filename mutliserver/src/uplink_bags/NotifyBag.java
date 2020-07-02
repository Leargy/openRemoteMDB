package uplink_bags;

import communication.Report;

import java.nio.channels.SocketChannel;

public class NotifyBag extends ChanneledBag {
    private final Report REPORT;

    public NotifyBag(SocketChannel clientChannel, Report result) {
        super(clientChannel);
        REPORT = result;
    }

    public Report getReport() { return REPORT; }

    @Override
    public SocketChannel getChannel() {
        return (SocketChannel) super.getChannel();
    }
}
