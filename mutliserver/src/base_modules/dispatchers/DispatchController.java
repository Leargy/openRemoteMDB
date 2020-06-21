package base_modules.dispatchers;

import base_modules.dispatchers.sending_tasks.SendingResultsTask;
import communication.Report;
import communication.ReportsFormatter;
import extension_modules.ClassUtils;
import patterns.mediator.Component;
import patterns.mediator.Controllers;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import uplink_bags.NotifyBag;
import uplink_bags.TransportableBag;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DispatchController implements Dispatchers {
    public final Controllers MAIN_SERVER_CONTROLLER;
    private final ExecutorService DISPATCHER;

    public DispatchController(Controllers controller, int poolSize) {
        MAIN_SERVER_CONTROLLER = controller;
        DISPATCHER = Executors.newFixedThreadPool(poolSize);
    }

    @Override
    public Report sendResults2Client(NotifyBag parcel) {
        DISPATCHER.submit(new SendingResultsTask(this, parcel));
//        DISPATCHER.execute(new SendingResultsTask(this, parcel));
        return ReportsFormatter.makeUpSuccessReport(ClassUtils.retrieveExecutedMethod());
    }

    @Override
    public Report notify(Component sender, TransportableBag parcel) {
        //TODO: make logging
        System.out.println("me under dispatching");
        if (sender == MAIN_SERVER_CONTROLLER) this.sendResults2Client((NotifyBag) parcel);
        return ReportsFormatter.makeUpSuccessReport(ClassUtils.retrieveExecutedMethod());
    }
}
