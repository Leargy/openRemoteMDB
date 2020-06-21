package server_core.configurators.thread_pooled;

import parameters.server.BaseServerParameters;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.nio.channels.ServerSocketChannel;

public final class StdOutThreadPooledServerConfigurator extends ThreadPooledServerConfigurator {
    @Override
    protected ServerSocketChannel bindChannelWithBaseParameters(BaseServerParameters baseParameters, ServerSocketChannel openedChannel) {
        throw new NotImplementedException();
    }

    @Override
    protected ServerSocketChannel openServerChannel() {
        throw new NotImplementedException();
    }
}
