package sample.dialog_windows;

import instructions.rotten.base.RawInsertByStep;
import instructions.rotten.base.RawSignIn;
import instructions.rotten.base.RawSignOut;
import instructions.rotten.base.RawSignUp;
import organization.Organization;
import sample.dialog_windows.communication.Component;
import sample.dialog_windows.communication.Mediating;
import sample.dialog_windows.communication.ApplicationParcel;
import sample.dialog_windows.communication.enum_section.Markers;
import sample.dialog_windows.handlers.ConnectionHandler;
import sample.dialog_windows.handlers.Handler;
import sample.dialog_windows.handlers.exceptions.*;

import java.util.concurrent.Exchanger;

public class TotalCommander implements Commander, Component {
    private Handler connectionHandler;
    public final Mediating mediator;
    private final Exchanger<Organization> organizationExchanger = new Exchanger<>();


    public TotalCommander(Mediating mediator) {
        this.mediator = mediator;
        connectionHandler = new ConnectionHandler();
    }

    @Override
    public void setConnection(ApplicationParcel applicationParcel) {
        try {
            applicationParcel = connectionHandler.handle(applicationParcel);
        }catch (CommonHandlerException ex) {
            mediator.notify(this,new ApplicationParcel(ex.getMessage(),Markers.SENDALLERT));
            return;
        }
        mediator.notify(this, new ApplicationParcel(applicationParcel.getMessage(), Markers.SETCONNECTION));
    }

    @Override
    public void back() {
        mediator.notify(this, new ApplicationParcel(Markers.RESETCONNECCTION));
        mediator.notify(this, new ApplicationParcel(Markers.PRIVIOUSSTAGE));
    }

    @Override
    public void signIn(String login, String password) {
        ApplicationParcel applicationParcel = new ApplicationParcel(RawSignIn.NAME + " " + login + " " + password, Markers.SENDCOMMAND);
        mediator.notify(this,applicationParcel);
    }


    @Override
    public void signOut() {
        ApplicationParcel applicationParcel = new ApplicationParcel(RawSignOut.NAME , Markers.SENDCOMMAND);
        mediator.notify(this,applicationParcel);
//        mediator.notify(this, new ApplicationParcel(Markers.PRIVIOUSSTAGE));
    }

    @Override
    public void signUp(String login, String password) {
        ApplicationParcel applicationParcel = new ApplicationParcel(RawSignUp.NAME + " " + login + " " + password, Markers.SENDCOMMAND);
        mediator.notify(this,applicationParcel);
    }


    @Override
    public void clear() {

    }

    @Override
    public void insert(String orgParametrs) {
        ApplicationParcel applicationParcel = new ApplicationParcel(RawInsertByStep.NAME + 6666 + " " +orgParametrs, Markers.SENDCOMMAND);
        mediator.notify(this,applicationParcel);
    }

    @Override
    public void update() {

    }

    @Override
    public void info() {

    }

    public boolean confirmAction(boolean isConfirmed) {

        return false;
    }

    public Organization exchange(Organization organization) {
        try {
            return organizationExchanger.exchange(organization);
        }catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
