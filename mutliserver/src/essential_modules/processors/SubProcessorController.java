package essential_modules.processors;

import essential_modules.processors.processing_tasks.QueryHandlingTask;
import communication_tools.Report;
import communication_tools.ReportsFormatter;
import extension_modules.ClassUtils;
import patterns.mediator.Component;
import patterns.mediator.Controllers;
import communication_tools.uplink_bags.RawDecreeBag;
import communication_tools.uplink_bags.TransportableBag;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class SubProcessorController implements Processors {
    private final Controllers CONTROLLER;
    private final ExecutorService PROCESS_UNIT = Executors.newCachedThreadPool();

    public SubProcessorController(Controllers controller) {
        CONTROLLER = controller;
    }

    @Override
    public Controllers getController() {
        return CONTROLLER;
    }

    @Override
    public Report notify(Component sender, TransportableBag parcel) {
        if (sender instanceof QueryHandlingTask)
            CONTROLLER.notify(this, parcel);
        return ReportsFormatter.makeUpSuccessReport(ClassUtils.retrieveExecutedMethod());
    }

    @Override
    public Report handleQuery(RawDecreeBag parcel) {
        PROCESS_UNIT.execute(new QueryHandlingTask(this, parcel));
        return ReportsFormatter.makeUpSuccessReport(ClassUtils.retrieveExecutedMethod());
    }
}
