package essential_modules.resource_keepers.executors;

import essential_modules.resource_keepers.ResourceKeeper;

import java.util.*;
import java.util.concurrent.ExecutorService;

public class ExecutorsKeeper extends ResourceKeeper {
    private final Map<ExecutorType, List<ExecutorService>> EXECUTORS = new HashMap<>();

    public ExecutorsKeeper() {
        Arrays.stream(ExecutorType.values())
                .forEach(
                        (type)->{
                            EXECUTORS.put(type, new ArrayList<>());
                        }
                );
    }

    public final boolean addRegisterExecutor(ExecutorService register) {
        return addNewExecutor(ExecutorType.REGISTRY, register);
    }

    public final boolean addPerusalExecutor(ExecutorService perusal) {
        return addNewExecutor(ExecutorType.PERUSAL, perusal);
    }

    public final boolean addProcessExecutor(ExecutorService processor) {
        return addNewExecutor(ExecutorType.PROCESS, processor);
    }

    public final boolean addDispatchExecutor(ExecutorService dispatcher) {
        return addNewExecutor(ExecutorType.DISPATCH, dispatcher);
    }

    protected synchronized boolean addNewExecutor(ExecutorType type, ExecutorService service) {
        return EXECUTORS.get(type).add(service);
    }

    public final boolean shutdownAllRegisters() {
        return shutdownAllTypeExecutors(ExecutorType.REGISTRY);
    }

    public final boolean shutdownAllPerusals() {
        return shutdownAllTypeExecutors(ExecutorType.PERUSAL);
    }

    public final boolean shutdownAllProcessors() {
        return shutdownAllTypeExecutors(ExecutorType.PROCESS);
    }

    public final boolean shutdownAllDispatchers() {
        return shutdownAllTypeExecutors(ExecutorType.DISPATCH);
    }

    protected synchronized boolean shutdownAllTypeExecutors(ExecutorType type) {
        boolean[] result = new boolean[]{true};
        EXECUTORS.get(type)
                .stream()
                .filter((xqtr)->(xqtr.isShutdown() || xqtr.isTerminated()))
                .forEach((xqtr)->{
                    EXECUTORS.get(type).remove(xqtr);
                });
        EXECUTORS.get(type)
                .stream()
                .forEach(
                        (xqtr)->{
                            try {
                                xqtr.shutdown();
                            } catch (SecurityException safe) {
                                result[0] = false;
                            }
                            EXECUTORS.get(type).remove(xqtr);
                        }
                );
        return result[0];
    }
}
