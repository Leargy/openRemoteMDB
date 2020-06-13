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
import patterns.mediator.Component;
import patterns.mediator.Controllers;
import uplink_bags.TransportableBag;

public class ServerController implements Controllers {
    private static final int MAX_SIMULTANEOUS_SENDS = 50;
    private final Registers REGISTER;
    private final Perusals PERUSALER;
    private final Processors SUBPROCESSOR;
    private final Dispatchers DISPATCHER;

    public ServerController() {
        REGISTER = new ReceptionController(this);
        PERUSALER = new PerusalController();
        SUBPROCESSOR = new SubProcessorController(this);
        DISPATCHER = new DispatchController(this, MAX_SIMULTANEOUS_SENDS);
    }

    @Override
    public Report notify(Component sender, TransportableBag parcel) {
        return new Report("Sending successful");
    }
}
