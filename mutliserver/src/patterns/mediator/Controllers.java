package patterns.mediator;

import communication.Report;
import communication.uplinkbags.ValuableBags;

public interface Controllers {
    Report notify(Component sender, ValuableBags parcel);
}
