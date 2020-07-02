package base_modules.processors;

import communication.Report;
import patterns.mediator.Component;
import patterns.mediator.Controllers;
import uplink_bags.RawDecreeBag;

public interface Processors extends Controllers, Component {
    Report handleQuery(RawDecreeBag parcel);
}
