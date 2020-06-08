package base_modules.dispatchers;

import communication.Report;
import communication.uplinkbags.NotifyBag;
import patterns.mediator.Component;
import patterns.mediator.Controllers;

public interface Dispatchers extends Controllers, Component {
    Report sendResults2Client(NotifyBag parcel);
}
