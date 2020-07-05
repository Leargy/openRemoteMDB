package sample.dialog_windows.communication;

import communication.Mediator;
import javafx.application.Platform;
import sample.dialog_windows.*;
import sample.dialog_windows.communication.enum_section.Markers;
import sample.dialog_windows.TotalCommander;
import сlient.KickStarter;

import java.util.concurrent.CountDownLatch;

public class ButtonMediator implements Mediating {
    public final SceneWriterActions sceneWriter;
    public final WindowOwner windowOwner;
    public final Commander totalCommander;
    public final communication.Mediating clientsMediator;
    public final BorderConverter borderConverter;
    public final KickStarter accessToAppFunctionality;
    private CountDownLatch firstLoading = new CountDownLatch(2);

    public ButtonMediator() {
        accessToAppFunctionality = new KickStarter();
        clientsMediator = accessToAppFunctionality.mediator;
        clientsMediator.setApplicationMediator(this);
        borderConverter = BorderConverter.getInstance();
        totalCommander = new TotalCommander(this);
        windowOwner = new WindowOwner(this);
        sceneWriter = new SceneWriter(totalCommander, windowOwner);

    }

    @Override
    public void notify(Component component, ApplicationParcel applicationParcel) {
        if (component == null && applicationParcel.getMarker() == Markers.SIGNALSTAGE) Platform.runLater(() -> {
            windowOwner.signalSceneInstantly();
            if (!applicationParcel.getMessage().equals("")) windowOwner.initAlertBox(applicationParcel.getMessage());
        });
//        System.out.println(applicationParcel.getMarker());
        if (component == null && applicationParcel.getMarker() == Markers.STOP) clientsMediator.notify(null,borderConverter.convertToClientsPackage(applicationParcel));
        if (component == totalCommander && applicationParcel.getMarker() == Markers.RESETCONNECCTION) {
            clientsMediator.notify(null, borderConverter.convertToClientsPackage(applicationParcel));
        }
        if ((component == totalCommander || component == null) && applicationParcel.getMarker() == Markers.SENDALLERT) Platform.runLater(() -> {
            windowOwner.signalSceneInstantly();
            windowOwner.initAlertBox(applicationParcel.getMessage());
        });
        if (component == totalCommander && applicationParcel.getMarker() == Markers.SENDCOMMAND) {
            clientsMediator.notify(null, borderConverter.convertToClientsPackage(applicationParcel));
        }
        if (component == totalCommander && applicationParcel.getMarker() == Markers.SETCONNECTION) {
            String ip = applicationParcel.getMessage().split(" ")[0];
            Integer port = Integer.valueOf(applicationParcel.getMessage().split(" ")[1]);
            ((Mediator)clientsMediator).getServant().setConnection(ip, port);
        }
        if (applicationParcel.getMarker() == Markers.PRIVIOUSSTAGE) Platform.runLater(() -> sceneWriter.setPreviousScene());
        if (applicationParcel.getMarker() == Markers.NEXTSTAGE)
            Platform.runLater(() -> {
                windowOwner.signalSceneInstantly();
                sceneWriter.setNextScene();
            });

        if (component == null && applicationParcel.getMarker() == Markers.UPDATE) Platform.runLater(() -> windowOwner.updateTable(applicationParcel));
        if (component == null && applicationParcel.getMarker() == Markers.CLEAR) Platform.runLater(() -> windowOwner.clearTable(applicationParcel));
        if (component == null && applicationParcel.getMarker() == Markers.INSERT) Platform.runLater(() -> windowOwner.insertInTable(applicationParcel));
        if (component == null && applicationParcel.getMarker() == Markers.REMOVE) Platform.runLater(() -> windowOwner.removeFromTable(applicationParcel));
        if (component == null && applicationParcel.getMarker() == Markers.INFO) Platform.runLater(() -> {
            windowOwner.setInfo(applicationParcel.getMessage());
        });
        if (component == null && applicationParcel.getMarker() == Markers.USER_CONNECTED) Platform.runLater(() -> windowOwner.setConnectedUser(applicationParcel.getMessage()));
        if (component == null && applicationParcel.getMarker() == Markers.USER_DISCONNECTED) Platform.runLater(() -> windowOwner.removeDisconnectedUser(applicationParcel.getMessage()));
        if (component == null && applicationParcel.getMarker() == Markers.INTERRUPTED) Platform.runLater(() -> {
            windowOwner.initAlertBox(applicationParcel.getMessage());
            sceneWriter.setConnectionScene();
        });
    }

    public void start() { sceneWriter.startShow();}
}
