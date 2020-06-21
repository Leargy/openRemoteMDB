package server_core.tasks.exiting_tasks;

import essential_modules.resource_keepers.TransmissionsKeeper;
import essential_modules.resource_keepers.executors.ExecutorsKeeper;

public abstract class TotalClosingTask implements Runnable {
    private final ExecutorsKeeper ALLEXECUTORS;
    private final TransmissionsKeeper ALLUPLINK;
    public TotalClosingTask(ExecutorsKeeper executorsKeeper, TransmissionsKeeper links) {
        ALLEXECUTORS = executorsKeeper;
        ALLUPLINK = links;

    }

    @Override
    public final void run() {
        ALLUPLINK.disconnectAllClients();
        ALLEXECUTORS.shutdownAllRegisters();
        ALLEXECUTORS.shutdownAllProcessors();
        ALLEXECUTORS.shutdownAllPerusals();
        ALLEXECUTORS.shutdownAllDispatchers();
        // close all others threads
        // wait for closing threads
        // get data from database
        // if have got save to files
        // close all resources like channels and streams
        // and files
        ALLUPLINK.disconnectFromDatabase();
        // shutdown the server
        // system.exit(0);
        System.exit(0);
    }
}
