package sample.drawing_utils.builders.rooms;

import javafx.scene.paint.Paint;
import sample.drawing_utils.materials.Beam;
import sample.drawing_utils.materials.Facade;
import sample.drawing_utils.materials.Window;

public class CanteenBuilder extends RoomBuilder {
    public static final int SITE = 2;
    public static final int LEFT_WINDOW = 0;
    public static final int CENTER_WINDOW = 1;
    public static final int RIGHT_WINDOW = 2;
    public static final int LEFT_BEAM = 0;
    public static final int LEFT_WINDOW_ROUNDING = 1;
    public static final int CENTER_WINDOW_ROUNDING = 2;
    public static final int RIGHT_WINDOW_ROUNDING = 3;
    public static final int RIGHT_BEAM = 4;
    public static final int TOP_BEAM = 0;
    public static final int TOP_MIDDLE_BEAM = 1;
    public static final int LOW_MIDDLE_BEAM = 2;
    public static final int BOTTOM_BEAM = 3;

    @Override
    public void buildFacade(double x, double y, double width, double height, Paint fill) {
        product.installFacade(new Facade(x, y, width, height, fill));
    }

    @Override
    public void buildWindows(double x, double y, double width, double height, Paint fill) {
        product.installWindow(LEFT_WINDOW, new Window(x + width / 5.4, y + height / 2.2, width / 5, height * 0.2, fill));
        product.installWindow(CENTER_WINDOW, new Window(x + width / 2.16, y + height / 2.2, width / 5, height * 0.2, fill));
        product.installWindow(RIGHT_WINDOW, new Window(x + width / 2  + 2 + width / 5, y + height / 2.2, width / 5, height * 0.2, fill));
    }

    @Override
    public void buildDoors(double x, double y, double width, double height, Paint fill) {
        System.out.println("There are no doors in canteen");
    }

    @Override
    public void buildHBeams(double x, double y, double width, double height, Paint fill) {
        product.installHBeam(TOP_BEAM, new Beam(x - width / 10, y, width * 1.3, height / 10, fill));
        product.installHBeam(TOP_MIDDLE_BEAM, new Beam(x - 4.5, y + height / 3, width * 1.22, height / 10, fill));
        product.installHBeam(LOW_MIDDLE_BEAM, new Beam(x - 4.5, y + height / 1.5, width * 1.22, height / 10, fill));
        product.installHBeam(BOTTOM_BEAM, new Beam(x - 5, y + height - 1, width * 1.25, height / 10, fill));
    }

    @Override
    public void buildVBeams(double x, double y, double width, double height, Paint fill) {
        product.installVBeam(LEFT_BEAM, new Beam(x, y, width / 10, height, fill));
        product.installVBeam(RIGHT_BEAM, new Beam(x + width, y, width / 10, height, fill));
        product.installVBeam(LEFT_WINDOW_ROUNDING, new Beam(x + width / 5.4 - 2, y + height / 2.5 - 2, width / 4, height * 0.35, fill));
        product.installVBeam(CENTER_WINDOW_ROUNDING, new Beam(x + width / 2.16 - 2, y + height / 2.5 - 2, width / 4, height * 0.35, fill));
        product.installVBeam(RIGHT_WINDOW_ROUNDING, new Beam(x + width / 2 + width / 5 , y + height / 2.5 - 2, width / 4, height * 0.35, fill));
    }

    @Override
    public void buildFlags(double x, double y, double width, double height, Paint fill) {
        System.out.println("There are no flags in canteen");
    }
}
