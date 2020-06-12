package base_modules.dispatchers;

import communication.Report;
import patterns.mediator.Component;
import patterns.mediator.Controllers;
import uplink_bags.NotifyBag;

public interface Dispatchers extends Controllers, Component {
    Report sendResults2Client(NotifyBag parcel);
}
