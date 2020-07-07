package sample.drawing_utils.materials.background;

import javafx.animation.*;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import sample.drawing_utils.directors.rooms.RoomDirector;
import sample.drawing_utils.materials.LiveWindow;

public final class LiveBush extends Bush {
    public LiveBush(double x, double y, double width, double height, Paint colour) {
        super(x, y, width, height, colour);
        FillTransition colorChanging = new FillTransition();
        colorChanging.setDuration(Duration.millis(LiveWindow.ANIMATION_CYCLE_DURATION));
        colorChanging.setFromValue(Bush.DAY_COLOUR);
        colorChanging.setToValue(Bush.NIGHT_COLOUR);
        colorChanging.setAutoReverse(true);
        ScaleTransition sizeChanging = new ScaleTransition();
        sizeChanging.setDuration(Duration.millis(LiveWindow.ANIMATION_CYCLE_DURATION));
        sizeChanging.setToX(Bush.DEFAULT_WIDTH * 0.01);
        sizeChanging.setToY(Bush.DEFAULT_HEIGHT * 0.05);
        sizeChanging.setAutoReverse(true);
        TranslateTransition motionChanging = new TranslateTransition();
        motionChanging.setDuration(Duration.millis(LiveWindow.ANIMATION_CYCLE_DURATION));
        motionChanging.setToX(x + Bush.DEFAULT_WIDTH / 100000);
        motionChanging.setAutoReverse(true);
        ParallelTransition collection = new ParallelTransition();
        collection.setNode(this);
        collection.getChildren().addAll(colorChanging, sizeChanging, motionChanging);
        collection.setCycleCount(Animation.INDEFINITE);
        collection.play();
    }
}
