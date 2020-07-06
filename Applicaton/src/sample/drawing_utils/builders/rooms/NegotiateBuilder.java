package sample.drawing_utils.builders.rooms;

import javafx.scene.paint.Paint;
import sample.drawing_utils.materials.Beam;
import sample.drawing_utils.materials.Door;
import sample.drawing_utils.materials.Facade;
import sample.drawing_utils.materials.Window;

public class NegotiateBuilder extends RoomBuilder {
    public static final int SITE = 4;
    public static final int LEFT_WINDOW = 0;
    public static final int CENTER_WINDOW = 1;
    public static final int RIGHT_WINDOW = 2;
    public static final int LEFT_BEAM = 0;
    public static final int LEFT_WINDOW_ROUNDING = 1;
    public static final int CENTER_WINDOW_ROUNDING = 2;
    public static final int RIGHT_WINDOW_ROUNDING = 3;
    public static final int RIGHT_BEAM = 4;
    public static final int TOP_BEAM = 0;

    @Override
    public void buildFacade(double x, double y, double width, double height, Paint fill) {
        product.installFacade(new Facade(x, y, width, height, fill));
    }

    @Override
    public void buildWindows(double x, double y, double width, double height, Paint fill) {
        product.installWindow(LEFT_WINDOW, new Window(x + width * 0.2,  y + height * 0.35, width / 5, height * 0.3, fill));
        product.installWindow(CENTER_WINDOW, new Window(x + width * 0.45, y + height * 0.35, width / 5, height * 0.3, fill));
        product.installWindow(RIGHT_WINDOW, new Window(x + 0.7 * width, y + height * 0.35, width / 5, height * 0.3, fill));
    }

    @Override
    public void buildDoors(double x, double y, double width, double height, Paint fill) {
        System.out.println("There are no doors in negotiate room");
    }

    @Override
    public void buildHBeams(double x, double y, double width, double height, Paint fill) {
        product.installHBeam(TOP_BEAM, new Beam(x - width / 10, y, width * 1.3, height / 10, fill));
    }

    @Override
    public void buildVBeams(double x, double y, double width, double height, Paint fill) {
        product.installVBeam(LEFT_BEAM, new Beam(x, y, width / 10, height, fill));
        product.installVBeam(RIGHT_BEAM, new Beam(x + width, y, width / 10, height, fill));
        product.installVBeam(LEFT_WINDOW_ROUNDING, new Beam(x + width * 0.2 - 2,  y + height * 0.35 - 2, width / 4, height * 0.35, fill));
        product.installVBeam(CENTER_WINDOW_ROUNDING, new Beam(x + width * 0.45 - 2, y + height * 0.35 - 2, width / 4, height * 0.35, fill));
        product.installVBeam(RIGHT_WINDOW_ROUNDING, new Beam(x + width * 0.7 - 2, y + height * 0.35 - 2, width / 4, height * 0.35, fill));
    }

    @Override
    public void buildFlags(double x, double y, double width, double height, Paint fill) {
        System.out.println("There are no flags in negotiate");
    }
}
