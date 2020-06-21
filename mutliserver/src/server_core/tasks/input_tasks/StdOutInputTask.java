package server_core.tasks.input_tasks;

import essential_modules.resource_keepers.TransmissionsKeeper;
import essential_modules.resource_keepers.executors.ExecutorsKeeper;
import server_core.tasks.service_tasks.ServiceTask;

import java.io.*;
import java.util.Scanner;

/**
 * Input tasks without log but printing
 * all messages in stdout. Checks the
 * user input and looks for exit command
 */
public final class StdOutInputTask extends InputTasks {

    public StdOutInputTask(ServiceTask mainTask, InputStream source, OutputStream connect, ExecutorsKeeper systems, TransmissionsKeeper links) {
        super(mainTask, source, connect, systems, links);
    }

    @Override
    protected void printPrompt(String invitationText, OutputStream out) throws IOException {
        System.out.println("Scanning new server command...");
        Writer output = new BufferedWriter(new OutputStreamWriter(out));
        output.write(invitationText);
        output.flush();
    }

    @Override
    protected String scanLine(InputStream in) {
        try (Scanner scan = new Scanner(in)) {
            return scan.nextLine();
        }
    }

    @Override
    protected boolean checkExit(String checkLine, OutputStream out) {
        System.out.println("Checking command...");
        boolean result = false;
        try {
            Writer output = new PrintWriter(new OutputStreamWriter(out));
            if ("exit".equals(checkLine)) {
                result = true;
                output.write("Entered the exit command\n");
                output.flush();
            } else {
                result = false;
                output.write("Entered command hasn't been recognised\n");
                output.flush();
            }
        } catch (IOException ioException) {
            System.err.println("Errors happened while printing messages to stream\n");
        }
        return result;
    }
}
