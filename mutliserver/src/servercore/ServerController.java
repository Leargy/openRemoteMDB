package servercore;

import base_modules.dispatchers.DispatchController;
import base_modules.dispatchers.Dispatchers;
import base_modules.processors.Processors;
import base_modules.processors.SubProcessorController;
import base_modules.readers.PerusalController;
import base_modules.readers.Perusals;
import base_modules.registers.ReceptionController;
import base_modules.registers.Registers;
import communication.Report;
import parameters.server.ConfiguredServerParameters;
import patterns.mediator.Component;
import patterns.mediator.Controllers;
import servercore.servertasks.inputtasks.InputServerTask;
import servercore.servertasks.maintasks.MainServerTask;
import uplink_bags.ChanneledBag;
import uplink_bags.RegistrationBag;
import uplink_bags.TransportableBag;

public class ServerController implements Controllers, Component {
    private static final int MAX_SIMULTANEOUS_SENDS = 10;
    private final Registers REGISTER;
    private final Perusals PERUSALER;
    private final Processors SUBPROCESSOR;
    private final Dispatchers DISPATCHER;
    private final MainServerTask MAIN_SERVER_TASK;

    public ServerController(ConfiguredServerParameters CSP) {
        MAIN_SERVER_TASK = new MainServerTask(CSP,this);
        REGISTER = new ReceptionController(this);
        PERUSALER = new PerusalController(this);
        SUBPROCESSOR = new SubProcessorController(this);
        DISPATCHER = new DispatchController(this, MAX_SIMULTANEOUS_SENDS);

    }

    @Override
    public Report notify(Component sender, TransportableBag parcel) {
        if (parcel == null) {
            Thread killer = new Thread(() -> {
                SUBPROCESSOR.notify(this,parcel);
            });
            try {
                killer.join();
            }catch (InterruptedException ex) { /*NOPE*/}
                MAIN_SERVER_TASK.closeServerSocketChannel();
            }
        if (sender == MAIN_SERVER_TASK) REGISTER.register((RegistrationBag) parcel);
        if (sender == REGISTER) {
//            System.out.println("me here");
            ((PerusalController)PERUSALER).notify(this, parcel);
            DISPATCHER.notify(this, parcel); //sending server_key
        }
        if (sender == PERUSALER) SUBPROCESSOR.notify(this,parcel);
        if (sender == SUBPROCESSOR) DISPATCHER.notify(this,parcel);
        return new Report(0,"Sending successful");
    }

    public MainServerTask getMAIN_SERVER_TASK() {
        return MAIN_SERVER_TASK;
    }

}