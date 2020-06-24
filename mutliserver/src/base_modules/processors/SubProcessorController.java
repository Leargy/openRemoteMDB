package base_modules.processors;

import base_modules.processors.processing_tasks.AuthenticationTask;
import base_modules.processors.processing_tasks.QueryHandlingTask;
import communication.Report;
import communication.ReportsFormatter;
import czerkaloggers.customer.B_4D4_GE3;
import extension_modules.dbinteraction.DataBaseConnector;
import organization.OrganizationWithUId;
import extension_modules.ClassUtils;
import parsing.FondleEmulator;
import parsing.InstructionBuilder;
import parsing.LilyInvoker;
import parsing.customer.bootstrapper.LoaferLoader;
import parsing.customer.bootstrapper.NakedCrateLoader;
import parsing.customer.distro.LimboKeeper;
import parsing.customer.distro.ShedBlock;
import patterns.mediator.Component;
import patterns.mediator.Controllers;
import uplink_bags.*;

import java.nio.channels.SocketChannel;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class SubProcessorController implements Processors {
    public final Controllers SERVER_CONTROLLER;
    public final ExecutorService PROCESS_UNIT = Executors.newCachedThreadPool();
    public final AuthenticationTask AUTHENTICATION_TASK;
    public final InstructionBuilder INSTRUCTION_BUILDER;
    public final LimboKeeper  TOTAL_COMMANDER;
    public final LoaferLoader<OrganizationWithUId> NAKED_CREATE_LOADER;
    public final FondleEmulator LILY_INVOKER;

    public SubProcessorController(Controllers controller) {
        SERVER_CONTROLLER = controller;
        AUTHENTICATION_TASK = new AuthenticationTask(this);
        INSTRUCTION_BUILDER = new InstructionBuilder(this, AUTHENTICATION_TASK); //adding link to authentication task to set in in authorization commands
        NAKED_CREATE_LOADER = new NakedCrateLoader(DataBaseConnector.getInstance());
        TOTAL_COMMANDER = new ShedBlock(NAKED_CREATE_LOADER,new B_4D4_GE3(this)); //TODO: нужен логгер
        LILY_INVOKER = new LilyInvoker(this);
        TOTAL_COMMANDER.DataRebase(NAKED_CREATE_LOADER.load());
    }

    @Override
    public Report notify(Component sender, TransportableBag parcel) {
//        System.out.println(parcel.getClass().getSimpleName());
        if (parcel == null) {

        }
        if (sender == SERVER_CONTROLLER && parcel.getClass().getSimpleName().equals("ChanneledBag") ) {
            AUTHENTICATION_TASK.removeAuthorizedUser((SocketChannel) ((ChanneledBag) parcel).getChannel());
            instaSave();
        } else if (sender == SERVER_CONTROLLER && parcel.getClass().getSimpleName().equals("ClientPackBag")) AUTHENTICATION_TASK.identify(parcel); //sending to determine, if temp client was authorized
        if (sender == AUTHENTICATION_TASK && parcel.getClass().getSimpleName().equals("NotifyBag")){ SERVER_CONTROLLER.notify(this, parcel);
        } else if (sender == AUTHENTICATION_TASK && parcel.getClass().getSimpleName().equals("RawDecreeBag")) INSTRUCTION_BUILDER.make(parcel,TOTAL_COMMANDER); // sending to determine the concrete command
        if (sender == INSTRUCTION_BUILDER) ((LilyInvoker)LILY_INVOKER).invoke((ExecuteBag) parcel); //sending to execute command
        if (sender == LILY_INVOKER) SERVER_CONTROLLER.notify(this, parcel); //sending to dispatcher
        if (sender instanceof QueryHandlingTask) SERVER_CONTROLLER.notify(this, parcel);
        return ReportsFormatter.makeUpSuccessReport(ClassUtils.retrieveExecutedMethod());
    }

    private void instaSave() {
        TOTAL_COMMANDER.save();
    }

    @Override
    public Report handleQuery(RawDecreeBag parcel) {
        PROCESS_UNIT.execute(new QueryHandlingTask(this, parcel));
        return ReportsFormatter.makeUpSuccessReport(ClassUtils.retrieveExecutedMethod());
    }
}
