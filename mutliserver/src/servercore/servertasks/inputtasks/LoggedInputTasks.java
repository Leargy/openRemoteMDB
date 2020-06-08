package servercore.servertasks.inputtasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public final class LoggedInputTasks extends InputServerTask {
    private final Logger logger = LoggerFactory.getLogger(InputServerTask.class);
    @Override
    public void run() {
        Scanner sysAdminInputScanner = new Scanner("System.in");
        do {
            System.out.println(">> ");
            String sysAdminInput = sysAdminInputScanner.nextLine();
            if ("exit".equals(sysAdminInput)) {
                logger.info("Entered the exit command");
                break;
            } else logger.info("Entered command hasn't been recognised");
        } while (true);
        logger.info("The exit command has been recognised");
        logger.info("Shutdowning the server...");
        System.exit(0);
    }
}
