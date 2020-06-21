package server_core.tasks.input_tasks;

import essential_modules.resource_keepers.TransmissionsKeeper;
import essential_modules.resource_keepers.executors.ExecutorsKeeper;
import server_core.tasks.exiting_tasks.LoggedTotalClosingTask;
import server_core.tasks.service_tasks.ServiceTask;

import java.io.*;

public abstract class InputTasks implements Runnable {
    private final InputStream SOURCE;
    private final OutputStream UPLINK;
    private final ServiceTask MAIN;
    private final ExecutorsKeeper SYSTEMS;
    private final TransmissionsKeeper LINKS;

    public InputTasks(ServiceTask mainTask, InputStream source, OutputStream connect, ExecutorsKeeper systems, TransmissionsKeeper links) {
        SOURCE = source;
        UPLINK = connect;
        MAIN = mainTask;
        SYSTEMS = systems;
        LINKS = links;
    }

    @Override
    public final void run() {
        try {
            String sysAdminInput = "";
            do {
                printPrompt(">> ", UPLINK);
                sysAdminInput = scanLine(SOURCE);
            } while (!checkExit(sysAdminInput, UPLINK));
            try (Writer output = new BufferedWriter(new OutputStreamWriter(UPLINK))) {
                output.write("The exit command has been recognised\n");
                output.write("Shutdowning the server...\n");
            }
            MAIN.shutdownServicing();
            new Thread(new LoggedTotalClosingTask(SYSTEMS, LINKS)).start();
        } catch (IOException ioException) {
            System.err.println("Errors happened while reading data from input");
            System.err.println("Now, you would never shutdown the server. Oh ho ho ho ho!");
            return;
        }
    }

    protected abstract void printPrompt(String invitationText, OutputStream out) throws IOException;

    protected abstract String scanLine(InputStream in);

    protected abstract boolean checkExit(String checkLine, OutputStream out);
}
