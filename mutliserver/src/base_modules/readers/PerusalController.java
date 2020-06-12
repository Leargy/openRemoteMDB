package base_modules.readers;

import communication.Report;
import parameters.entities.UsersParameters;
import patterns.mediator.Component;
import patterns.mediator.Controllers;
import uplink_bags.TransportableBag;

import java.nio.channels.SocketChannel;

public class PerusalController implements Perusals, Controllers {
    @Override
    public Controllers getController() {
        return null;
    }

    @Override
    public Report notify(Component sender, TransportableBag parcel) {
        return null;
    }
}
