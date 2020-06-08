package base_modules.dispatchers;

import communication.Report;
import communication.uplinkbags.NotifyBag;
import communication.uplinkbags.ValuableBags;
import patterns.mediator.Component;
import patterns.mediator.Controllers;

public class DispatchController implements Dispatchers {

    @Override
    public Report sendResults2Client(NotifyBag parcel) {
        return new Report("Hello, user");
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
