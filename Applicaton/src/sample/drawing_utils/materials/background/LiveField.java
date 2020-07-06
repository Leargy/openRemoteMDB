package sample.drawing_utils.materials.background;

import javafx.scene.Group;

import java.util.Random;

public final class LiveField extends Field {
    public LiveField(Group resident) {
        super(resident);
    }

    @Override
    protected final LiveBush createRandomBush(double minx, double miny, double width, double height) {
        Random random = new Random();
        double x = random.nextDouble() * width + minx;
        double y = random.nextDouble() * height + miny;
        return new LiveBush(x, y, Bush.DEFAULT_WIDTH, Bush.DEFAULT_HEIGHT, Bush.DAY_COLOUR);
    }


}
