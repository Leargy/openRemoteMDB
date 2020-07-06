package sample.drawing_utils.builders.rooms;

import javafx.scene.paint.Paint;
import sample.drawing_utils.materials.Beam;
import sample.drawing_utils.materials.Door;
import sample.drawing_utils.materials.Facade;
import sample.drawing_utils.materials.Window;

public class OfficeBuilder extends RoomBuilder {
    public static final int SITE = 1;
    public static final int UPPER_LEFT_WINDOW = 0;
    public static final int UPPER_RIGHT_WINDOW = 1;
    public static final int LOWER_LEFT_WINDOW = 2;
    public static final int LOWER_RIGHT_WINDOW = 3;
    public static final int LEFT_BEAM = 0;
    public static final int CENTER_BEAM = 1;
    public static final int RIGHT_BEAM = 2;
    public static final int TOP_BEAM = 0;
    public static final int BOTTOM_BEAM = 1;

    @Override
    public void buildFacade(double x, double y, double width, double height, Paint fill) {
        product.installFacade(new Facade(x, y, width, height, fill));
    }

    @Override
    public void buildWindows(double x, double y, double width, double height, Paint fill) {
        product.installWindow(UPPER_LEFT_WINDOW, new Window(x + width * 0.2,  y + height * 0.15, width / 5, height * 0.3, fill));
        product.installWindow(UPPER_RIGHT_WINDOW, new Window(x + 0.7 * width, y + height * 0.15, width / 5, height * 0.3, fill));
        product.installWindow(LOWER_LEFT_WINDOW, new Window(x + width * 0.2, y + height * 0.6, width / 5, height * 0.3, fill));
        product.installWindow(LOWER_RIGHT_WINDOW, new Window(x + width * 0.7, y + height * 0.6, width / 5, height * 0.3, fill));
    }

    @Override
    public void buildDoors(double x, double y, double width, double height, Paint fill) {
        System.out.println("There are no doors in offices");
    }

    @Override
    public void buildHBeams(double x, double y, double width, double height, Paint fill) {
        product.installHBeam(TOP_BEAM, new Beam(x - width / 10, y, width * 1.27, height / 10, fill));
        product.installHBeam(BOTTOM_BEAM, new Beam(x - width / 10, y + height - 1, width * 1.2, height / 10, fill));
    }

    @Override
    public void buildVBeams(double x, double y, double width, double height, Paint fill) {
        product.installVBeam(LEFT_BEAM, new Beam(x, y, width / 10, height, fill));
        product.installVBeam(CENTER_BEAM, new Beam(x + width / 2, y, width / 10, height, fill));
        product.installVBeam(RIGHT_BEAM, new Beam(x + width, y, width / 10, height, fill));
    }

    @Override
    public void buildFlags(double x, double y, double width, double height, Paint fill) {
        System.out.println("There are no flags in hall");
    }
}
