package base_modules.processors;

import base_modules.processors.processing_tasks.AuthenticationTask;
import base_modules.processors.processing_tasks.QueryHandlingTask;
import communication.Report;
import communication.ReportsFormatter;
import extension_modules.ClassUtils;
import parsing.InstructionBuilder;
import patterns.mediator.Component;
import patterns.mediator.Controllers;
import uplink_bags.RawDecreeBag;
import uplink_bags.TransportableBag;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class SubProcessorController implements Processors {
    private final Controllers CONTROLLER;
    private final ExecutorService PROCESS_UNIT = Executors.newCachedThreadPool();
    private final AuthenticationTask AUTHENTICATION_TASK;
    private final InstructionBuilder INSTRUCTION_BUILDER;

    public SubProcessorController(Controllers controller) {
        CONTROLLER = controller;
        AUTHENTICATION_TASK = new AuthenticationTask(this);
        INSTRUCTION_BUILDER = new InstructionBuilder(this);

    }

    @Override
    public Controllers getController() {
        return CONTROLLER;
    }

    @Override
    public Report notify(Component sender, TransportableBag parcel) {
        if (sender == CONTROLLER) AUTHENTICATION_TASK.identify(parcel);
        if (sender == AUTHENTICATION_TASK) INSTRUCTION_BUILDER.make(parcel,);
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
