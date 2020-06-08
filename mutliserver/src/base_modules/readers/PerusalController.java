package base_modules.readers;

import communication.Report;
import communication.treasures.parameters.entities.UsersParameters;
import communication.uplinkbags.QueryBag;
import communication.uplinkbags.ValuableBags;
import patterns.mediator.Component;
import patterns.mediator.Controllers;

import java.nio.channels.SocketChannel;

public class PerusalController implements Perusals, Controllers {
    @Override
    public UsersParameters retrieveUserParameters(SocketChannel readableChannel) {
        return null;
    }

    @Override
    public QueryBag retrieveClientQuery(SocketChannel readableChannel) {
        return null;
    }

    @Override
    public Controllers getController() {
        return null;
    }

    @Override
    public Report notify(Component sender, ValuableBags parcel) {
        return null;
    }
}
