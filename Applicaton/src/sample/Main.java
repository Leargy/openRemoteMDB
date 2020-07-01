package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import sample.dialogWindows.IpAndPortInputWindow;

import java.io.IOException;

public class Main extends Application {
    private IpAndPortInputWindow ipAndPortInputWindow;
    private Stage mainStage;
    {
        ipAndPortInputWindow = new IpAndPortInputWindow();

    }

    @Override
    public void start(Stage primaryStage){
        mainStage = primaryStage;
        try {
            ipAndPortInputWindow.renderWindow();
        }catch (IOException ex) {
            System.err.println(ex.getMessage());
        }finally {
            mainStage.close();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
