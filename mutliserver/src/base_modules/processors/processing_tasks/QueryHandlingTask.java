package base_modules.processors.processing_tasks;

import patterns.mediator.Controllers;
import uplink_bags.RawDecreeBag;

public class QueryHandlingTask implements Runnable {
    private final Controllers CONTROLLER;
    private final RawDecreeBag ON_EXECUTED_QUERY;

    public QueryHandlingTask(Controllers controller, RawDecreeBag parcel) {
        CONTROLLER = controller;
        ON_EXECUTED_QUERY = parcel;
    }

    @Override
    public void run() {

    }


}
