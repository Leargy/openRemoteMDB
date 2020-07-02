package sample.dialog_windows.communication;

import communication.Mediator;
import javafx.application.Platform;
import sample.dialog_windows.*;
import sample.dialog_windows.communication.enum_section.Markers;
import sample.dialog_windows.TotalCommander;
import сlient.KickStarter;

public class ButtonMediator implements Mediating {
    public final SceneWriterActions sceneWriter;
    public final WindowOwner windowOwner;
    public final Commander totalCommander;
    public final communication.Mediating clientsMediator;
    public final BorderConverter borderConverter;
    public final KickStarter accessToAppFunctionality;

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
        if (component == totalCommander && applicationParcel.getMarker() == Markers.RESETCONNECCTION) {
            clientsMediator.notify(null, borderConverter.convertToClientsPackage(applicationParcel));
        }
        if ((component == totalCommander || component == null) && applicationParcel.getMarker() == Markers.SENDALLERT) Platform.runLater(() -> {windowOwner.initAlertBox(applicationParcel.getMessage());});
        if (component == totalCommander && applicationParcel.getMarker() == Markers.SENDCOMMAND) {
            clientsMediator.notify(null, borderConverter.convertToClientsPackage(applicationParcel));
        }
        if (component == totalCommander && applicationParcel.getMarker() == Markers.SETCONNECTION) {
            String ip = applicationParcel.getMessage().split(" ")[0];
            Integer port = Integer.valueOf(applicationParcel.getMessage().split(" ")[1]);
            ((Mediator)clientsMediator).getServant().setConnection(ip, port);
        }
        if (applicationParcel.getMarker() == Markers.PRIVIOUSSTAGE) sceneWriter.setPreviousScene();
        if (applicationParcel.getMarker() == Markers.NEXTSTAGE)  Platform.runLater(() -> sceneWriter.setNextScene());

    }

    public void start() { sceneWriter.startShow();}
}
