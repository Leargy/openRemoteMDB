package patterns.mediator;

import communication_tools.Report;
import communication_tools.uplink_bags.TransportableBag;

public interface Controllers {
    Report notify(Component sender, TransportableBag parcel);
}
