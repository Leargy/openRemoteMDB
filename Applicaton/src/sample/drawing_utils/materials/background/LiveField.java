package sample.drawing_utils.materials.background;

import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.scene.Group;
import javafx.util.Duration;
import sample.drawing_utils.materials.LiveWindow;

import java.util.Random;

public final class LiveField extends Field {
    public LiveField(Group resident) {
        super(resident);
        FillTransition colorChanging = new FillTransition();
        colorChanging.setDuration(Duration.millis(LiveWindow.ANIMATION_CYCLE_DURATION));
        colorChanging.setFromValue(Field.SURFACE_DAY);
        colorChanging.setToValue(Field.SURFACE_NIGHT);
        colorChanging.setAutoReverse(true);
        colorChanging.setCycleCount(Animation.INDEFINITE);
        colorChanging.setShape(BACKGROUND);
        colorChanging.play();
    }

    @Override
    protected final LiveBush createRandomBush(double minx, double miny, double width, double height) {
        Random random = new Random();
        double x = random.nextDouble() * width + minx;
        double y = random.nextDouble() * height + miny;
        return new LiveBush(x, y, Bush.DEFAULT_WIDTH, Bush.DEFAULT_HEIGHT, Bush.DAY_COLOUR);
    }


}
