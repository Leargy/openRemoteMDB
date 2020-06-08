package servercore.servertasks.maintasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LoggedMainTask extends MainServerTask {
    private final Logger logger = LoggerFactory.getLogger(LoggedMainTask.class);

    @Override
    public void run() {
        logger.warn("You can shutdown server by typing \"exit\" in console at any time");
        // TODO: write logic
    }
}
