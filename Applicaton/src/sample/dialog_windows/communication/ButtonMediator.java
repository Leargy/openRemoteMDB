package sample.dialog_windows.communication;

import sample.dialog_windows.*;
import sample.dialog_windows.communication.enum_section.Markers;

public class ButtonMediator implements Mediating {
    private SceneWriterActions sceneWriter;
    private WindowOwner windowOwner;
    private Commander totalCommander;

    public ButtonMediator() {
        totalCommander = new TotalCommander(this);
        windowOwner = new WindowOwner(this);
        sceneWriter = new SceneWriter(totalCommander, windowOwner);
    }

    @Override
    public void notify(Component component, Parcel parcel) {
        if (component == totalCommander && parcel.getMarker() == Markers.SENDALLERT) windowOwner.initAlertBox(parcel.getMessage());
        if (parcel.getMarker() == Markers.NEXTSTAGE) sceneWriter.setNextScene();
        if (parcel.getMarker() == Markers.PRIVIOUSSTAGE) sceneWriter.setPreviousScene();


    }
    public void start() { sceneWriter.startShow();}
}
