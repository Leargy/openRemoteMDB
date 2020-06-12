package base_modules.dispatchers;

import base_modules.dispatchers.sending_tasks.SendingResultsTask;
import communication.Report;
import patterns.mediator.Component;
import patterns.mediator.Controllers;
import uplink_bags.NotifyBag;
import uplink_bags.TransportableBag;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DispatchController implements Dispatchers {
    private final Controllers CONTROLLER;
    private final ExecutorService DISPATCHER;

    public DispatchController(Controllers controller, int poolSize) {
        CONTROLLER = controller;
        DISPATCHER = Executors.newFixedThreadPool(poolSize);
    }

    @Override
    public Report sendResults2Client(NotifyBag parcel) {
        DISPATCHER.execute(new SendingResultsTask(this, parcel));
        return null; // TODO: return successful Report with report formatter
    }

    @Override
    public Controllers getController() {
        return CONTROLLER;
    }

    @Override
    public Report notify(Component sender, TransportableBag parcel) {
        return null;
    }
}
