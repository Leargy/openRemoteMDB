package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import sample.dialog_windows.Dialog;
import sample.dialog_windows.communication.ApplicationParcel;
import sample.dialog_windows.communication.ButtonMediator;
import sample.dialog_windows.communication.enum_section.Markers;
import сlient.KickStarter;

public class Main extends Application {
    private ButtonMediator buttonMediator;
//    private IpAndPortInputWindow ipAndPortInputWindow;
//    private Stage mainStage;
    {
        buttonMediator = new ButtonMediator();
//        ipAndPortInputWindow = new IpAndPortInputWindow();
    }

    @Override
    public void start(Stage primaryStage){
        primaryStage.show();
        Dialog.thisStage.setOnCloseRequest(event -> {
            buttonMediator.notify(null, new ApplicationParcel(Markers.STOP));
        });
        buttonMediator.start();
//        mainStage = primaryStage;
//        ipAndPortInputWindow.renderWindow();
//        mainStage.close();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
