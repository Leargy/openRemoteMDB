package base_modules.processors;

import communication.Report;
import patterns.mediator.Component;
import patterns.mediator.Controllers;
import uplink_bags.TransportableBag;

public class SubProcessorController implements Processors {

    @Override
    public Controllers getController() {
        return null;
    }

    @Override
    public Report notify(Component sender, TransportableBag parcel) {
        return null;
    }
}
