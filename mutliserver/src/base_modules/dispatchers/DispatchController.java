package base_modules.dispatchers;

import base_modules.dispatchers.sending_tasks.SendingResultsTask;
import communication.ClientPackage;
import communication.Report;
import communication.ReportsFormatter;
import extension_modules.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import patterns.mediator.Component;
import patterns.mediator.Controllers;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import uplink_bags.ChanneledBag;
import uplink_bags.ClientPackBag;
import uplink_bags.NotifyBag;
import uplink_bags.TransportableBag;

import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.channels.SocketChannel;
import java.security.Key;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DispatchController implements Dispatchers {
    public final Controllers MAIN_SERVER_CONTROLLER;
    private final ExecutorService DISPATCHER;
    public final Logger logger = LoggerFactory.getLogger(DispatchController.class);
    private final String SERVER_KEY;

    public DispatchController(Controllers controller, int poolSize) {
        MAIN_SERVER_CONTROLLER = controller;
        DISPATCHER = Executors.newFixedThreadPool(poolSize);
        SERVER_KEY = "SERVER_KEY:" + prepareServerKey();
    }

    @Override
    public Report sendResults2Client(NotifyBag parcel) {
//        DISPATCHER.submit(new SendingResultsTask(this, parcel));
//        DISPATCHER.execute(new SendingResultsTask(this, parcel));
        return ReportsFormatter.makeUpSuccessReport(ClassUtils.retrieveExecutedMethod());
    }

    public Report sendResults2Client(ClientPackBag parcel) {
        if (parcel.getClientPacket().getCommand() != null) System.out.println(Thread.currentThread().getName() + " " + parcel.getClientPacket().getCommand().getClass());
        DISPATCHER.submit(new SendingResultsTask(this, parcel));
        return ReportsFormatter.makeUpSuccessReport(ClassUtils.retrieveExecutedMethod());
    }

    @Override
    public Report notify(Component sender, TransportableBag parcel) {
        if (parcel.getClass().getSimpleName().equals("ChanneledBag")) this.sendResults2Client(new ClientPackBag((SocketChannel) ((ChanneledBag)parcel).getChannel(),new ClientPackage(null,new Report(0, SERVER_KEY))));
        if (sender == MAIN_SERVER_CONTROLLER && parcel.getClass().getSimpleName().equals("NotifyBag")) this.sendResults2Client(new ClientPackBag(((NotifyBag)parcel).getChannel(),new ClientPackage(null,((NotifyBag)parcel).getReport())));
        if (sender == MAIN_SERVER_CONTROLLER && parcel.getClass().getSimpleName().equals("ClientPackBag")) this.sendResults2Client((ClientPackBag) parcel);
        return ReportsFormatter.makeUpSuccessReport(ClassUtils.retrieveExecutedMethod());
    }

    private String prepareServerKey() {
        logger.info("Preparing server key");
        String encriptedKey = "";
        Key ayes = new SecretKeySpec("TopSecretKey".getBytes(), "AES"); //encripting temp pepe (AES - шифр)
        encriptedKey = String.valueOf(ayes.hashCode());
        return encriptedKey;
    }
}
