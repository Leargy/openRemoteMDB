package sample.drawing_utils.builders.rooms;

import javafx.scene.paint.Paint;
import sample.drawing_utils.materials.Beam;
import sample.drawing_utils.materials.Door;
import sample.drawing_utils.materials.Facade;
import sample.drawing_utils.materials.Window;

public class StorageBuilder extends RoomBuilder {
    public static final int SITE = 3;
    public static final int LEFT_BEAM = 0;
    public static final int LEFT_MIDDLE_BEAM = 1;
    public static final int RIGHT_MIDDLE_BEAM = 2;
    public static final int RIGHT_BEAM = 3;
    public static final int BOTTOM_BEAM = 0;
    public static final int LOWER_MIDDLE_BEAM = 1;
    public static final int UPPER_MIDDLE_BEAM = 2;
    public static final int TOP_BEAM = 3;

    @Override
    public void buildFacade(double x, double y, double width, double height, Paint fill) {
        product.installFacade(new Facade(x, y, width, height, fill));
    }

    @Override
    public void buildWindows(double x, double y, double width, double height, Paint fill) {
    }

    @Override
    public void buildDoors(double x, double y, double width, double height, Paint fill) {
    }

    @Override
    public void buildHBeams(double x, double y, double width, double height, Paint fill) {
        product.installHBeam(BOTTOM_BEAM, new Beam(x - width / 10, y + height - 0.5, width * 1.3, height / 10, fill));
        product.installHBeam(LOWER_MIDDLE_BEAM, new Beam(x - width / 10, y + 0.6 * height - 1, width * 1.3, height / 10, fill));
        product.installHBeam(UPPER_MIDDLE_BEAM, new Beam(x - width / 10, y + 0.3 * height - 1, width * 1.3, height / 10, fill));
        product.installHBeam(TOP_BEAM, new Beam(x - width / 10, y, width * 1.3, height / 10, fill));
    }

    @Override
    public void buildVBeams(double x, double y, double width, double height, Paint fill) {
        product.installVBeam(LEFT_BEAM, new Beam(x, y, width / 10, height, fill));
        product.installVBeam(LEFT_MIDDLE_BEAM, new Beam(x + width * 0.3, y, width / 10, height, fill));
        product.installVBeam(RIGHT_MIDDLE_BEAM, new Beam(x + width * 0.7, y, width / 10, height, fill));
        product.installVBeam(RIGHT_BEAM, new Beam(x + width, y, width / 10, height, fill));
    }

    @Override
    public void buildFlags(double x, double y, double width, double height, Paint fill) {
        System.out.println("There are no flags in storage");
    }
}
