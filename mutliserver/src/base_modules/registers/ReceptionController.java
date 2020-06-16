package base_modules.registers;

import base_modules.readers.readertasks.ClientPackageRetrievingTask;
import base_modules.registers.reg_tasks.RegistrationTask;
import base_modules.registers.reg_tasks.UserDataRetrievingTask;
import communication.Report;
import communication.ReportsFormatter;
import extension_modules.ClassUtils;
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

    // Take another client's socketChannel
    @Override
    public Report register(RegistrationBag registrationData) {
        SocketChannel client = null;
        Future<SocketChannel> resultClient = REGISTRAR.submit(new RegistrationTask(registrationData.getChannel()));

        //while (!resultClient.isDone()) logger.info("Getting and configuring client channel...");
        try {
            client = resultClient.get();
        } catch (InterruptedException interruptedException) {
            logger.error("There were some problems while getting client channel because of unexpected interruption");
            return ReportsFormatter.makeUpUnsuccessReport(ClassUtils.retrieveExecutedMethod());
        } catch (ExecutionException executionException) {
            logger.error("There were problems while getting client channel at execution time");
            return ReportsFormatter.makeUpUnsuccessReport(ClassUtils.retrieveExecutedMethod());
        }
        //
        System.out.println(Thread.currentThread().getName());
        //
        USER_DATA_RETRIEVER.execute(new ClientPackageRetrievingTask(this, client));
        return ReportsFormatter.makeUpSuccessReport(ClassUtils.retrieveExecutedMethod());
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
        return ReportsFormatter.makeUpSuccessReport(ClassUtils.retrieveExecutedMethod());
    }
}
