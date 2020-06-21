package essential_modules.processors;

import communication_tools.Report;
import patterns.mediator.Component;
import patterns.mediator.Controllers;
import communication_tools.uplink_bags.RawDecreeBag;

public interface Processors extends Controllers, Component {
    Report handleQuery(RawDecreeBag parcel);
}
