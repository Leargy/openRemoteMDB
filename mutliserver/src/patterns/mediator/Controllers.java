package patterns.mediator;

import communication.Report;
import uplink_bags.TransportableBag;

public interface Controllers{
    Report notify(Component sender, TransportableBag parcel);
}
