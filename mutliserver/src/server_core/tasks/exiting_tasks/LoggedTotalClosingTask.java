package server_core.tasks.exiting_tasks;

import essential_modules.resource_keepers.TransmissionsKeeper;
import essential_modules.resource_keepers.executors.ExecutorsKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggedTotalClosingTask extends TotalClosingTask {
    private final Logger LOG = LoggerFactory.getLogger(TotalClosingTask.class);

    public LoggedTotalClosingTask(ExecutorsKeeper executorsKeeper, TransmissionsKeeper links) {
        super(executorsKeeper, links);
    }
}
