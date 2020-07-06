package sample.drawing_utils.builders.rooms;

import javafx.scene.paint.Paint;
import sample.drawing_utils.materials.*;

public class RoofBuilder extends RoomBuilder {
    public static final int SITE = 7;
    public static final int BOTTOM_BEAM = 0;
    public static final int TOP_BEAM = 1;
    public static final int LEFT_BEAM = 0;
    public static final int RIGHT_BEAM = 1;
    @Override
    public void buildFacade(double x, double y, double width, double height, Paint fill) {
        product.installFacade(new Facade(x + width / 3.5, y + height / 2, width / 2, height / 2, fill));
    }

    @Override
    public void buildWindows(double x, double y, double width, double height, Paint fill) {
    }

    @Override
    public void buildDoors(double x, double y, double width, double height, Paint fill) {
    }

    @Override
    public void buildHBeams(double x, double y, double width, double height, Paint fill) {
        product.installHBeam(BOTTOM_BEAM, new Beam(x + width / 3 - width / 10, y + height - 1, width * 1.3 / 2, height / 20, fill));
        product.installHBeam(TOP_BEAM, new Beam(x + width / 3 - width / 10, y + height / 2 - 1, width * 1.3 / 2, height / 20, fill));
    }

    @Override
    public void buildVBeams(double x, double y, double width, double height, Paint fill) {
        product.installVBeam(LEFT_BEAM, new Beam(x + width / 3.5, y + height / 2, width / 20, height / 2, fill));
        product.installVBeam(RIGHT_BEAM, new Beam(x + width / 3.5 + width / 2, y + height / 2, width / 20, height / 2, fill));
    }

    @Override
    public void buildFlags(double x, double y, double width, double height, Paint fill) {
        product.installFlag(0, new Flag(x + 0.55 * width, y + height / 2, width / 2, height / 2, fill));
    }
}
