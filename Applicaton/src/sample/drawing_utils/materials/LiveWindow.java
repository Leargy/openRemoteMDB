package sample.drawing_utils.materials;

import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.animation.Transition;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import sample.drawing_utils.directors.rooms.RoomDirector;

public final class LiveWindow extends Window {
    public static final double ANIMATION_CYCLE_DURATION = 2000;

    public LiveWindow(double x, double y, double width, double height, Paint colour) {
        super(x, y, width, height, colour);
        FillTransition transit = new FillTransition();
        transit.setDuration(Duration.millis(ANIMATION_CYCLE_DURATION));
        transit.setFromValue(RoomDirector.WINDOWS_COLOUR_OPENED);
        transit.setToValue(RoomDirector.WINDOWS_COLOUR_CLOSED);
        transit.setShape(this);
        transit.setAutoReverse(true);
        transit.setCycleCount(Animation.INDEFINITE);
        transit.play();
    }
}
