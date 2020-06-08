package base_modules.readers;

import communication.treasures.parameters.entities.UsersParameters;
import communication.uplinkbags.QueryBag;
import patterns.mediator.Component;

import java.nio.channels.SocketChannel;

public interface Perusals extends Component {
    UsersParameters retrieveUserParameters(SocketChannel readableChannel);
    QueryBag retrieveClientQuery(SocketChannel readableChannel);
    default int getMaximumByteBufferCapacity() { return 10 * 1024; }
}
