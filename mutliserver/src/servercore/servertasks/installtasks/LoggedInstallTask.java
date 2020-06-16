package servercore.servertasks.installtasks;

import parameters.server.ConfiguredServerParameters;
import parameters.server.ServerParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servercore.Server;
import servercore.installers.ServerInstaller;

public final class LoggedInstallTask extends InstallServerTask {
    private final Logger logger = LoggerFactory.getLogger(LoggedInstallTask.class);

    @Override
    public Server call() throws Exception {
        logger.info("Trying get server parameters");
        ServerParameters rawParameters = promptServerParameters();
        logger.info("Received server parameters");
        logger.info("Trying configure server with parameters");
        ConfiguredServerParameters configuredParameters = MAIN_INSTALLER.configureServer(rawParameters);
        logger.info("Server configured");
        logger.info("Returning configured server");
        return Server.getInstance(configuredParameters);
    }

    public LoggedInstallTask(ServerInstaller installer) {
        super(installer);
    }
}
