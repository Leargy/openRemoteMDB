package sample.dialog_windows.Controllers;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;

public class ConnectionProgress extends Application {
    private static Timer refreshTimer = new Timer();
    private static TimerTask refreshTask;
    private static Label label = new Label();

    @Override
    public void start(Stage primaryStage) throws Exception {
        ProgressBar connectionWaiting = new ProgressBar();
        connectionWaiting.setPrefWidth(100);
        connectionWaiting.setMinWidth(200);
        FlowPane progressPannel = new FlowPane();
        progressPannel.setMaxHeight(120);
        progressPannel.setMaxWidth(250);

        progressPannel.setPadding(new Insets(10,25,30,25));
        progressPannel.setHgap(100);


        label.setFont(Font.font("Georgia Italic"));
        label.setText("Setting connection.");
//        label.setAlignment(Pos.CENTER);
//        label.setLayoutX(100);
        label.setPadding(new Insets(10,30,30,30));
        progressPannel.getChildren().add(connectionWaiting);

        progressPannel.getChildren().add(label);

        Scene progressScene = getProgressScene(progressPannel);

        primaryStage.setTitle("Connection");

        primaryStage.setMaxHeight(120);
        primaryStage.setMaxWidth(250);
        primaryStage.setResizable(false);
        primaryStage.setScene(progressScene);
//        System.out.println(Thread.currentThread().getName());
        primaryStage.showAndWait();
    }

//    @FXML
//    void initialize() {
//        System.out.println(Thread.currentThread().getName());
//        refreshTask = new TimerTask() {
//            @Override
//            public void run() {
//
//                label.setText(renderLabel(label.getText()));
//            }
//        };
//        Platform.runLater(() -> refreshTimer.schedule(refreshTask,20L, 200L));
//    }

        public Scene getProgressScene(Parent root) {
        Scene newProgressScene = new Scene(root, 250,100);
        return newProgressScene;
    }

//    private String renderLabel(String string) {
//        System.out.println(string);
//        switch (string.length()) {
//            case 10: return "Connecting.";
//            case 11: return "Connecting..";
//            case 12: return "Connecting...";
//            default: return "Connecting";
//        }
//    }
}
