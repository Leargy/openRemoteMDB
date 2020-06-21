package server_core;

import parameters.server.ConfiguredBaseServerParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server_core.configurators.thread_pooled.LoggedThreadPooledServerConfigurator;
import server_core.configurators.thread_pooled.StdOutThreadPooledServerConfigurator;
import server_core.tasks.exiting_tasks.StdOutTotalClosingTask;
import server_core.tasks.exiting_tasks.TotalClosingTask;
import server_core.tasks.input_tasks.InputTasks;
import server_core.tasks.input_tasks.LoggedInputTasks;
import server_core.tasks.input_tasks.StdOutInputTask;
import server_core.tasks.install_tasks.InstallationTask;
import server_core.tasks.install_tasks.LoggedInstallServerTask;
import server_core.tasks.install_tasks.StdOutInstallServerTask;
import server_core.tasks.service_tasks.LoggedServiceTask;
import server_core.tasks.service_tasks.ServiceTask;
import server_core.tasks.service_tasks.StdOutServiceTask;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Server {
    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    private static volatile Server instance;
    // Basic server tasks
    private static final InstallationTask INSTALL_TASK = new LoggedInstallServerTask(
            System.in,
            System.out,
            new LoggedThreadPooledServerConfigurator()
    ); // to configure server socket in thread pooled mode

    private final ConfiguredBaseServerParameters PARAMETERS;

    private final ServerController SERVER_CONTROLLER;

    private Server(ConfiguredBaseServerParameters params) {
        PARAMETERS = params;
        SERVER_CONTROLLER = new ServerController();
    }

    public static Server getInstance(ConfiguredBaseServerParameters params) {
        if (instance == null)
            synchronized (Server.class) {
                if (instance == null) {
                    instance = new Server(params);
                }
            }
        return instance;
    }

    public ServerController getController() { return SERVER_CONTROLLER; }

    public ConfiguredBaseServerParameters getConfiguredParameters() { return PARAMETERS; }

    public static Server install() {
        logger.info("Trying installing server");
        Server installedServer = null;
        ExecutorService installExecutor = Executors.newSingleThreadExecutor();
        Future<ConfiguredBaseServerParameters> installingProcess = installExecutor.submit(INSTALL_TASK);
        while (!installingProcess.isDone()) Thread.yield();
        try {
            installedServer = getInstance(installingProcess.get());
            installedServer
                    .SERVER_CONTROLLER
                    .KEEPER
                    .LINKS
                    .setNewServer(installedServer.PARAMETERS.SERVER_CHANNEL.socket());
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

    public void launch() {
        if (instance == null) {
            logger.error("Server isn't installed. Did you forgive invoke install before launching?");
            return;
        }
        logger.info("Server is starting...");
        ServiceTask mainTask = new LoggedServiceTask(
                SERVER_CONTROLLER,
                PARAMETERS);
        Thread inputTaskThread = new Thread(new LoggedInputTasks(mainTask,
                System.in,
                System.out,
                getController().KEEPER.SYSTEMS,
                getController().KEEPER.LINKS));
        Thread mainTaskThread = new Thread(mainTask);
        inputTaskThread.start();
        logger.info("Server's command input starts");
        mainTaskThread.start();
        logger.info("Client servicing starts");
        logger.info("Server successfully launched");
    }
}
