package sample.dialog_windows;

import sample.buttons.AbstractButtonsFactory;
import sample.buttons.ButtonFactory;
import sample.dialog_windows.communication.Component;
import sample.dialog_windows.communication.Mediating;

public class SceneWriter implements SceneWriterActions, Component {
    private Dialog[] scenes;
    private int tempScene;
    private WindowOwner windowOwner;
    private ButtonFactory buttonFactory;
    public final Mediating mediator;

    public SceneWriter(Commander totalCommander, WindowOwner owner) {
        mediator = owner.mediator;
        windowOwner = owner;
        buttonFactory = new AbstractButtonsFactory(totalCommander);

        scenes = new Dialog[] {
                new IpAndPortInputWindow().setButtonActioner(buttonFactory.createConnectionButton()),
                new LogInWindow(),
        };
    }


    @Override
    public void startShow() {
        tempScene = 0;
        windowOwner.setTempWindow(scenes[tempScene]);
        windowOwner.renderTempWindow();
    }

    @Override
    public void setNextScene() {
        if(tempScene == (scenes.length-1)) {
            System.err.println("tempScene id is equals to " + (scenes.length - 1));
            return;
        }
        tempScene++;
        windowOwner.setTempWindow(scenes[tempScene]);
        windowOwner.renderTempWindow();
    }

    @Override
    public void setPreviousScene() {
        if(tempScene == 0) {
            System.err.println("tempScene id is equals to 0");
            return;
        }
        tempScene--;
        windowOwner.setTempWindow(scenes[tempScene]);
        windowOwner.renderTempWindow();
    }
}
