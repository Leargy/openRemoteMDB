package server_core.tasks.service_tasks;

import communication_tools.uplink_bags.RegistrationBag;
import essential_modules.resource_keepers.TransmissionsKeeper;
import essential_modules.resource_keepers.executors.ExecutorsKeeper;
import parameters.server.ConfiguredBaseServerParameters;
import patterns.mediator.Component;
import patterns.mediator.Controllers;
import server_core.tasks.exiting_tasks.LoggedTotalClosingTask;

import java.io.IOException;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public abstract class ServiceTask implements Runnable, Component {
    private volatile boolean isStopped = false;
    private final ConfiguredBaseServerParameters SERVER_PARAMETERS;
    private final Controllers CONTROLLER;
    public ServiceTask(Controllers controller, ConfiguredBaseServerParameters params) {
        CONTROLLER = controller;
        SERVER_PARAMETERS = params;
    }

    public boolean shutdownServicing() {
        return isStopped = true;
    }

    @Override
    public final void run() {
        ServerSocketChannel serverChannel = SERVER_PARAMETERS.SERVER_CHANNEL;
        while (!isStopped) {
            SocketChannel clientChannel = null;
            try {
                clientChannel = serverChannel.accept();
            } catch (IOException ioException) {

            }
            CONTROLLER.notify(this, new RegistrationBag(clientChannel));
        }
    }

    @Override
    public final Controllers getController() {
        return CONTROLLER;
    }
}
