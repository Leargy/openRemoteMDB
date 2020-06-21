package essential_modules.dispatchers;

import communication_tools.Report;
import patterns.mediator.Component;
import patterns.mediator.Controllers;
import communication_tools.uplink_bags.NotifyBag;

public interface Dispatchers extends Controllers, Component {
    Report sendResults2Client(NotifyBag parcel);
}
