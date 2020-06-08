package base_modules.processors;

import communication.Report;
import communication.uplinkbags.QueryBag;
import communication.uplinkbags.ValuableBags;
import patterns.mediator.Component;
import patterns.mediator.Controllers;

public class SubProcessorController implements Processors {
    @Override
    public Report process(QueryBag query) {
        return null;
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
