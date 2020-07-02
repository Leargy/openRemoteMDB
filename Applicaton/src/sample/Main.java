package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import sample.dialog_windows.communication.ButtonMediator;
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
        buttonMediator.start();
//        mainStage = primaryStage;
//        ipAndPortInputWindow.renderWindow();
//        mainStage.close();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
