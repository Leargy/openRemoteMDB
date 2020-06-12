package servercore;

import parameters.server.ConfiguredServerParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servercore.installers.LoggedServerInstaller;
import servercore.servertasks.inputtasks.InputServerTask;
import servercore.servertasks.installtasks.InstallServerTask;
import servercore.servertasks.installtasks.LoggedInstallTask;
import servercore.servertasks.maintasks.MainServerTask;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Server {
    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    private static volatile Server instance;
    private static final MainServerTask MAIN_TASK = new MainServerTask();
    private static final InstallServerTask INSTALL_TASK = new LoggedInstallTask(new LoggedServerInstaller());
    private static final InputServerTask INPUT_TASK = new InputServerTask();
    private final ConfiguredServerParameters PARAMETERS;

    private final ServerController SERVER_CONTROLLER;

    private Server(ConfiguredServerParameters params) {
        PARAMETERS = params;
        SERVER_CONTROLLER = new ServerController();
    }

    public static Server getInstance(ConfiguredServerParameters params) {
        if (instance == null)
            synchronized (Server.class) {
                if (instance == null) {
                    instance = new Server(params);
                }
            }
        return instance;
    }

    public ServerController getController() { return SERVER_CONTROLLER; }

    public ConfiguredServerParameters getConfiguredParameters() { return PARAMETERS; }

    public static Server install() {
        logger.info("Trying installing server");
        Server installedServer = null;
        ExecutorService installExecutor = Executors.newSingleThreadExecutor();
        Future<Server> installingProcess = installExecutor.submit(INSTALL_TASK);
        while (!installingProcess.isDone()) logger.info("Waiting server installing");
        try {
            installedServer = installingProcess.get();
        } catch (InterruptedException interruptedException) {
            logger.error("Sorry, but server installing was interrupted");
            System.exit(interruptedException.hashCode());
        } catch (ExecutionException executionException) {
            logger.error("Sorry, but server haven't been installed");
            System.exit(executionException.hashCode());
        }
        installExecutor.shutdown();
        return installedServer;
    }

    public static void launch() {
        if (instance == null) {
            logger.error("Server isn't installed. Did you forgive invoke install before launching?");
            return;
        }
        logger.info("Server is starting...");
        Thread inputTaskThread = new Thread(INPUT_TASK);
        Thread mainTaskThread = new Thread(MAIN_TASK);
        inputTaskThread.start();
        logger.info("Server's command input starts");
        mainTaskThread.start();
        logger.info("Client service starts");
        logger.info("Server successfully launched");
    }
}
