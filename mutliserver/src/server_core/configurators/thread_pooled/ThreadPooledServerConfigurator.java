package server_core.configurators.thread_pooled;

import parameters.server.BaseServerParameters;
import parameters.server.ConfiguredBaseServerParameters;
import server_core.configurators.ServerConfigurators;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.nio.channels.ServerSocketChannel;

public abstract class ThreadPooledServerConfigurator implements ServerConfigurators {

    @Override
    public ConfiguredBaseServerParameters configure(BaseServerParameters baseParameters) {
        ServerSocketChannel openedChannel = openServerChannel();
        if (openedChannel == null) return null;
        ServerSocketChannel bindedServerChannel = bindChannelWithBaseParameters(baseParameters, openedChannel);
        return new ConfiguredBaseServerParameters(baseParameters, bindedServerChannel);
    }

    protected abstract ServerSocketChannel bindChannelWithBaseParameters(BaseServerParameters baseParameters, ServerSocketChannel openedChannel);

    protected abstract ServerSocketChannel openServerChannel();
}
