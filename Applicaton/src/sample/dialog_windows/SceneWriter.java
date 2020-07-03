package sample.dialog_windows;

import sample.buttons.AbstractButtonsFactory;
import sample.buttons.ButtonFactory;
import sample.dialog_windows.Controllers.IpAndPortInputWindowController;
import sample.dialog_windows.Controllers.LogInWindowController;
import sample.dialog_windows.Controllers.MainWindowController;
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
        IpAndPortInputWindowController ipAndPortInputWindowController = new IpAndPortInputWindowController();
        ipAndPortInputWindowController.setCommander(totalCommander);
        MainWindowController mainWindowController = new MainWindowController();
        mainWindowController.setCommander(totalCommander);
        LogInWindowController logInWindowController = new LogInWindowController();
        logInWindowController.setCommander(totalCommander);

        scenes = new Dialog[] {
                ipAndPortInputWindowController,
                logInWindowController,
                mainWindowController
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
