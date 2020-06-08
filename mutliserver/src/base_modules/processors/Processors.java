package base_modules.processors;

import communication.Report;
import communication.uplinkbags.QueryBag;
import patterns.mediator.Component;
import patterns.mediator.Controllers;

public interface Processors extends Controllers, Component {
    Report process(QueryBag query);
}
