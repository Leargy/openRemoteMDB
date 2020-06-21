package essential_modules.readers;

import communication_tools.Report;
import patterns.mediator.Component;
import patterns.mediator.Controllers;
import communication_tools.uplink_bags.TransportableBag;

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
