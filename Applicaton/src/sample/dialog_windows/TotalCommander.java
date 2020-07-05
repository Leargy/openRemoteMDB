package sample.dialog_windows;

import instructions.rotten.base.*;
import organization.Organization;
import sample.dialog_windows.communication.Component;
import sample.dialog_windows.communication.Mediating;
import sample.dialog_windows.communication.ApplicationParcel;
import sample.dialog_windows.communication.enum_section.Markers;
import sample.dialog_windows.handlers.ConnectionHandler;
import sample.dialog_windows.handlers.Handler;
import sample.dialog_windows.handlers.exceptions.*;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TotalCommander implements Commander, Component {
    private Handler connectionHandler;
    public final Mediating mediator;
    private final Exchanger<Organization> organizationExchanger = new Exchanger<>();
    private final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);


    public TotalCommander(Mediating mediator) {
        this.mediator = mediator;
        connectionHandler = new ConnectionHandler();
    }

    @Override
    public void setConnection(ApplicationParcel applicationParcel) {
        try {
            Thread.sleep(1000);
        }catch (InterruptedException ex) { /*NOPE*/}
        try {
            applicationParcel = connectionHandler.handle(applicationParcel);
        }catch (CommonHandlerException ex) {
            fixedThreadPool.submit(() ->mediator.notify(this, new ApplicationParcel(ex.getMessage(), Markers.SENDALLERT)));
            return;
        }
        ApplicationParcel finalApplicationParcel = applicationParcel;
        fixedThreadPool.submit(() ->mediator.notify(this, new ApplicationParcel(finalApplicationParcel.getMessage(), Markers.SETCONNECTION)));

    }

    @Override
    public void back() {
        fixedThreadPool.submit(() -> mediator.notify(this, new ApplicationParcel(Markers.RESETCONNECCTION)));
        mediator.notify(this, new ApplicationParcel(Markers.PRIVIOUSSTAGE));
    }

    @Override
    public void signIn(String login, String password) {
        ApplicationParcel applicationParcel = new ApplicationParcel(RawSignIn.NAME + " " + login + " " + password, Markers.SENDCOMMAND);
        fixedThreadPool.submit(() -> mediator.notify(this, applicationParcel));
    }


    @Override
    public void signOut() {
        ApplicationParcel applicationParcel = new ApplicationParcel(RawSignOut.NAME , Markers.SENDCOMMAND);
        fixedThreadPool.submit(() -> mediator.notify(this, applicationParcel));
//        mediator.notify(this, new ApplicationParcel(Markers.PRIVIOUSSTAGE));
    }

    @Override
    public void signUp(String login, String password) {
        ApplicationParcel applicationParcel = new ApplicationParcel(RawSignUp.NAME + " " + login + " " + password, Markers.SENDCOMMAND);
        fixedThreadPool.submit(() -> mediator.notify(this, applicationParcel));
    }


    @Override
    public void clear() {
        ApplicationParcel applicationParcel = new ApplicationParcel(RawClear.NAME, Markers.SENDCOMMAND);
        fixedThreadPool.submit(() -> mediator.notify(this, applicationParcel));
    }

    @Override
    public void insert(String orgParametrs) {
        ApplicationParcel applicationParcel = new ApplicationParcel(RawInsertByStep.NAME + " " + 666 + " " +orgParametrs, Markers.SENDCOMMAND);
        fixedThreadPool.submit(() -> mediator.notify(this, applicationParcel));
    }

    @Override
    public void update(String id, String orgParametrs) {
        ApplicationParcel applicationParcel = new ApplicationParcel(RawUpdateByStep.NAME + " " + id + " " + orgParametrs, Markers.SENDCOMMAND);
        fixedThreadPool.submit(() -> mediator.notify(this, applicationParcel));
    }

    @Override
    public void info() {
        ApplicationParcel applicationParcel = new ApplicationParcel(RawInfo.NAME, Markers.SENDCOMMAND);
        fixedThreadPool.submit(() -> mediator.notify(this, applicationParcel));
    }

    @Override
    public void remove(String id) {
        ApplicationParcel applicationParcel = new ApplicationParcel(RawRemoveKey.NAME + " " + id, Markers.SENDCOMMAND);
        fixedThreadPool.submit(() -> mediator.notify(this, applicationParcel));
    }


//    public boolean confirmAction(boolean isConfirmed) {
//
//        return false;
//    }
//
//    public Organization exchangeOrg(Organization organization) {
//        try {
//            return organizationExchanger.exchange(organization);
//        }catch (InterruptedException ex) {
//            System.out.println(ex.getMessage());
//            return null;
//        }
//    }
}
