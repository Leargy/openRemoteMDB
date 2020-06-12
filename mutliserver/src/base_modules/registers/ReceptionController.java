package base_modules.registers;

import base_modules.registers.reg_tasks.RegistrationTask;
import base_modules.registers.reg_tasks.UserDataRetrievingTask;
import communication.Report;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import patterns.mediator.Component;
import patterns.mediator.Controllers;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import uplink_bags.RegistrationBag;
import uplink_bags.TransportableBag;

import java.nio.channels.SocketChannel;
import java.util.concurrent.*;

public class ReceptionController implements Registers {
    private final Controllers CONTROLLER;
    private final ExecutorService USER_DATA_RETRIEVER = Executors.newCachedThreadPool();
    private final ExecutorService REGISTRAR = Executors.newSingleThreadExecutor();
    private final Logger logger = LoggerFactory.getLogger(ReceptionController.class);

    public ReceptionController(Controllers controller) {
        CONTROLLER = controller;
    }

    @Override
    public Report register(RegistrationBag registrationData) {
        SocketChannel client = null;
        Future<SocketChannel> resultClient = REGISTRAR.submit(new RegistrationTask(registrationData));
        while (!resultClient.isDone()) logger.info("Getting and configuring client channel...");
        try {
            resultClient.get();
        } catch (InterruptedException interruptedException) {
            logger.error("There were some problems while getting client channel because of unexpected interruption");
            // TODO: return Report with Report formatter
        } catch (ExecutionException executionException) {
            logger.error("There were problems while getting client channel at execution time");
            // TODO: return Report with Report formatter
        }
        USER_DATA_RETRIEVER.execute(new UserDataRetrievingTask(this, client));
        return null; // TODO: return successful report using reports formatter
    }

    @Override
    public Controllers getController() {
        return CONTROLLER;
    }

    // TODO: write real registration with database

    @Override
    public Report notify(Component sender, TransportableBag parcel) {
        if (sender instanceof UserDataRetrievingTask)
            throw new NotImplementedException(); // TODO: send on registration
        return null; // TODO: return successful report using reports formatter
    }
}
