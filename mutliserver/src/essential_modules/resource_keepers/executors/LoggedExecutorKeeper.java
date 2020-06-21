package essential_modules.resource_keepers.executors;

import essential_modules.resource_keepers.executors.ExecutorType;
import essential_modules.resource_keepers.executors.ExecutorsKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;

public final class LoggedExecutorKeeper extends ExecutorsKeeper {
    private final Logger LOG = LoggerFactory.getLogger(ExecutorsKeeper.class);

    @Override
    protected synchronized boolean addNewExecutor(ExecutorType type, ExecutorService service) {
        boolean result =  super.addNewExecutor(type, service);
        if (result)
            LOG.info(String.format("New %s was successfully added", type.name()));
        else LOG.warn(String.format("New %s wasn't added", type.name()));
        return result;
    }

    @Override
    protected synchronized boolean shutdownAllTypeExecutors(ExecutorType type) {
        boolean result = super.shutdownAllTypeExecutors(type);
        if (result)
            LOG.info(String.format("All %s threads were successfully terminated", type.name()));
        else LOG.warn(String.format("There were some %s threads which wasn't terminated successfully", type.name()));
        return result;
    }
}
