package servercore.servertasks.inputtasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import patterns.mediator.Controllers;

import java.util.Scanner;

public final class LoggedInputTasks extends InputServerTask {
    private final Logger logger = LoggerFactory.getLogger(InputServerTask.class);
    public LoggedInputTasks(Controllers controllers) {
        super(controllers);
    }
    @Override
    public void run() {
        Scanner sysAdminInputScanner = new Scanner(System.in);
        String sysAdminInput = "";
        do {
            System.out.println(">> ");
            sysAdminInput = sysAdminInputScanner.nextLine();
            if ("exit".equals(sysAdminInput)) {
                logger.info("Entered the exit command");
                break;
            } else logger.info("Entered command hasn't been recognised");
        } while (true);
        logger.info("The exit command has been recognised");
        logger.info("Shutdowning the server...");
        SERVER_CONTROLLER.notify(this,null);
        System.exit(0);
    }
}
