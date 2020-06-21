package server_core.tasks.exiting_tasks;

import essential_modules.resource_keepers.TransmissionsKeeper;
import essential_modules.resource_keepers.executors.ExecutorsKeeper;

public class StdOutTotalClosingTask extends TotalClosingTask {
    public StdOutTotalClosingTask(ExecutorsKeeper executorsKeeper, TransmissionsKeeper links) {
        super(executorsKeeper, links);
    }
}
