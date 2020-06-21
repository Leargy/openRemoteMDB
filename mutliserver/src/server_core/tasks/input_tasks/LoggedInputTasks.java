package server_core.tasks.input_tasks;

import essential_modules.resource_keepers.TransmissionsKeeper;
import essential_modules.resource_keepers.executors.ExecutorsKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server_core.tasks.service_tasks.ServiceTask;

import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Input tasks with log. Checks the user input
 * and looks for exit command
 */
public final class LoggedInputTasks extends InputTasks {
    private final Logger LOG = LoggerFactory.getLogger(InputTasks.class);

    public LoggedInputTasks(ServiceTask mainTask, InputStream source, OutputStream connect, ExecutorsKeeper systems, TransmissionsKeeper links) {
        super(mainTask, source, connect, systems, links);
    }

    @Override
    protected void printPrompt(String invitationText, OutputStream out) throws IOException {
        LOG.info("Scanning new server command...");
        Writer output = new PrintWriter(new OutputStreamWriter(out));
        output.write(invitationText);
        output.flush();
    }

    @Override
    protected String scanLine(InputStream in) {
        try {
            Scanner scan = new Scanner(in);
            String line = scan.nextLine();
            return line;
        } catch (NoSuchElementException element) {
            return "";
        }
    }

    @Override
    protected boolean checkExit(String checkLine, OutputStream out) {
        LOG.info("Checking command...");
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
            LOG.error("Errors happened while printing messages to stream\n");
        }
        return result;
    }
}
