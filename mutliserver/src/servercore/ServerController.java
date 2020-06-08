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
import communication.uplinkbags.ValuableBags;
import patterns.mediator.Component;
import patterns.mediator.Controllers;

public class ServerController implements Controllers {
    private final Registers REGISTER;
    private final Perusals PERUSALER;
    private final Processors SUBPROCESSOR;
    private final Dispatchers DISPATCHER;

    public ServerController() {
        REGISTER = new ReceptionController(this);
        PERUSALER = new PerusalController();
        SUBPROCESSOR = new SubProcessorController();
        DISPATCHER = new DispatchController();
    }

    @Override
    public Report notify(Component sender, ValuableBags parcel) {
        if (sender == PERUSALER)
            SUBPROCESSOR.process(null);
        else if (sender == SUBPROCESSOR)
            DISPATCHER.sendResults2Client(null);
        return new Report("Sending successful");
    }

    public void connectNewClient() {
    }

    public void serviceClientQuery() {

    }
}
