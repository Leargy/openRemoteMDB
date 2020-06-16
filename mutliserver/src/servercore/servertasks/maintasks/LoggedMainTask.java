package servercore.servertasks.maintasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parameters.server.ConfiguredServerParameters;
import servercore.ServerController;

public final class LoggedMainTask extends MainServerTask {
    private final Logger logger = LoggerFactory.getLogger(LoggedMainTask.class);

    public LoggedMainTask(ConfiguredServerParameters CSP, ServerController serverController) {
        super(CSP,serverController);
    }

    @Override
    public void run() {
        logger.warn("You can shutdown server by typing \"exit\" in console at any time");
        // TODO: write logic
    }
}
