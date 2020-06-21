package server_core.tasks.service_tasks;

import essential_modules.resource_keepers.TransmissionsKeeper;
import essential_modules.resource_keepers.executors.ExecutorsKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parameters.server.ConfiguredBaseServerParameters;
import patterns.mediator.Controllers;

public final class LoggedServiceTask extends ServiceTask {
    private final Logger LOG = LoggerFactory.getLogger(ServiceTask.class);

    public LoggedServiceTask(Controllers controller, ConfiguredBaseServerParameters params) {
        super(controller, params);
    }
}
