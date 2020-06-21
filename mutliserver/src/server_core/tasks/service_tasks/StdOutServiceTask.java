package server_core.tasks.service_tasks;

import essential_modules.resource_keepers.TransmissionsKeeper;
import essential_modules.resource_keepers.executors.ExecutorsKeeper;
import parameters.server.ConfiguredBaseServerParameters;
import patterns.mediator.Controllers;

public final class StdOutServiceTask extends ServiceTask {
    public StdOutServiceTask(Controllers controller, ConfiguredBaseServerParameters params) {
        super(controller, params);
    }
}
