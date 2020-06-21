package server_core.configurators.with_selector;

import parameters.server.BaseServerParameters;
import parameters.server.ConfiguredBaseServerParameters;
import parameters.server.ConfiguredServerSelectorParameters;
import server_core.configurators.ServerConfigurators;

import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public abstract class ServerSelectorConfigurator implements ServerConfigurators {

    protected abstract ServerSocketChannel openServerChannel();

    protected abstract ServerSocketChannel bindServerChannel(BaseServerParameters params, ServerSocketChannel openedChannel);

    protected abstract ServerSocketChannel configureServerChannel(ServerSocketChannel bindedChannel);

    protected abstract Selector openServerSelector();

    protected abstract void registerServer2Selector(ServerSocketChannel configuredServerChannel, Selector selector);

    @Override
    public final ConfiguredBaseServerParameters configure(BaseServerParameters params) {
        try {
            // 1 stage: open channel
            ServerSocketChannel installingServerChannel = openServerChannel();

            // 2 stage: bind the channel
            ServerSocketChannel bindedServerChannel = bindServerChannel(params, installingServerChannel);

            // 3 stage: configure server channel
            ServerSocketChannel configuredServerChannel = configureServerChannel(bindedServerChannel);

            // 4 stage: open the selector
            Selector openedSelector = openServerSelector();

            // 5 stage: register configured server channel to selector
            registerServer2Selector(configuredServerChannel, openedSelector);

            // 6 stage: returning new parameters
            return new ConfiguredServerSelectorParameters(params, configuredServerChannel, openedSelector);
        } catch (NullPointerException nullPointerException) {
            return null;
        }
    }
}
