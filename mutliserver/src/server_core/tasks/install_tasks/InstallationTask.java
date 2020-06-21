package server_core.tasks.install_tasks;

import parameters.server.BaseServerParameters;
import parameters.server.ConfiguredBaseServerParameters;
import server_core.configurators.ServerConfigurators;

import java.io.*;
import java.util.concurrent.Callable;

/**
 * Template of server installation. Contains
 * step-methods describing sequence of actions that needs
 * to configuring server. All methods except call can be overriden
 * to insert new functionality. Realization of template-method pattern.
 * @author come_ill_foo aka Lenar Khannanov
 * @author Leargy aka Anton Sushkevich
 */
public abstract class InstallationTask implements Callable<ConfiguredBaseServerParameters> {
    protected final int MAX_INPUT_ATTEMPTS = 3;
    protected final InputStream SOURCE;
    protected final OutputStream UPLINK;
    protected final ServerConfigurators CONFIGURATOR;


    public InstallationTask(InputStream dataSource, OutputStream channel, ServerConfigurators config) {
        SOURCE = dataSource;
        UPLINK = channel;
        CONFIGURATOR = config;
    }

    /**
     * Main method of retrieving server
     * parameters and configuring it.
     * @return configured parameters
     * @throws Exception some exception
     */
    @Override
    public final ConfiguredBaseServerParameters call() throws Exception {
        BaseServerParameters baseServerParameters = promptBaseServerParameters(SOURCE, UPLINK);
        return CONFIGURATOR.configure(baseServerParameters);
    }

    protected abstract BaseServerParameters promptBaseServerParameters(InputStream in, OutputStream out);

    protected abstract String promptServerAddress(InputStream in, OutputStream out);

    protected abstract int promptServerPort(InputStream in, OutputStream out);

}
