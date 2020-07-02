package base_modules.readers;

import base_modules.processors.SubProcessorController;
import base_modules.readers.readertasks.ClientPackageRetrievingTask;
import communication.Report;
import parameters.entities.UsersParameters;
import patterns.mediator.Component;
import patterns.mediator.Controllers;
import uplink_bags.ChanneledBag;
import uplink_bags.TransportableBag;

import javax.swing.*;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PerusalController implements Perusals, Controllers {
    private Executor readCachedThreadPool = Executors.newCachedThreadPool();
    private final Controllers SERVER_CONTROLLER;
//    private final SubProcessorController SUB_PROCESS_CONTROLLER;
//    private ClientPackageRetrievingTask CLIENT_PACKAGE_RETRIEVING_TASK;

    public PerusalController(Controllers serverController) {
        this.SERVER_CONTROLLER = serverController;
//        SUB_PROCESS_CONTROLLER = new SubProcessorController(this);
//        CLIENT_PACKAGE_RETRIEVING_TASK = new ClientPackageRetrievingTask(this);
    }

    @Override
    public Report notify(Component sender, TransportableBag parcel) {
        if (sender == SERVER_CONTROLLER) readCachedThreadPool.execute(new ClientPackageRetrievingTask(this, (SocketChannel)((ChanneledBag)parcel).getChannel()));
//        if (sender == SUB_PROCESS_CONTROLLER) SERVER_CONTROLLER.notify(this,parcel);
        if (sender instanceof ClientPackageRetrievingTask) SERVER_CONTROLLER.notify(this, parcel);
        return null;
    }
}
