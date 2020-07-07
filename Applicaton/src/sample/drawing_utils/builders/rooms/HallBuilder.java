package sample.drawing_utils.builders.rooms;

import javafx.scene.paint.Paint;
import sample.drawing_utils.materials.*;

public class HallBuilder extends RoomBuilder {
    public static final int SITE = 0;
    public static final int LEFT_WINDOW = 0;
    public static final int RIGHT_WINDOW = 1;
    public static final int BOTTOM_BEAM = 0;
    public static final int TOP_DOOR_WAY_BEAM = 1;
    public static final int TOP_BEAM = 2;
    public static final int LEFT_BEAM = 0;
    public static final int LEFT_WINDOW_ROUNDING = 1;
    public static final int RIGHT_BEAM = 2;
    public static final int RIGHT_WINDOW_ROUNDING = 3;
    public static final int LEFT_DOOR_WAY_BEAM = 4;
    public static final int RIGHT_DOOR_WAY_BEAM = 5;
    public static final int CENTER_DOOR = 0;
    public static final int OUT_OF_BOUNDS_FLAG = 0;
    @Override
    public void buildFacade(double x, double y, double width, double height, Paint fill) {
        product.installFacade(new Facade(x, y, width, height, fill));
    }

    @Override
    public void buildWindows(double x, double y, double width, double height, Paint fill) {
        product.installWindow(LEFT_WINDOW, new Window(x + width * 0.25,  y + height * 0.3, width / 5, height * 0.3, fill));
        product.installWindow(RIGHT_WINDOW, new Window(x + width * 0.65, y + height * 0.3, width / 5, height * 0.3, fill));
    }

    @Override
    public void buildDoors(double x, double y, double width, double height, Paint fill) {
        product.installDoor(CENTER_DOOR, new Door(x + width * 0.36, y + height /1.27, width / 2.62, height / 5, fill));
    }

    @Override
    public void buildHBeams(double x, double y, double width, double height, Paint fill) {
        product.installHBeam(TOP_BEAM , new Beam(x - width / 10, y, width * 1.27, height / 10, fill));
        product.installHBeam(TOP_DOOR_WAY_BEAM, new Beam(x + width * 0.31, y + height / 1.38, width / 2.13, height / 15, fill));
        product.installHBeam(BOTTOM_BEAM, new Beam(x - 5, y + height - 1, width * 1.25, height / 10, fill));
    }

    @Override
    public void buildVBeams(double x, double y, double width, double height, Paint fill) {
        product.installVBeam(LEFT_BEAM, new Beam(x, y, width / 10, height, fill));
        product.installVBeam(RIGHT_BEAM, new Beam(x + width, y, width / 10, height, fill));
        product.installVBeam(LEFT_DOOR_WAY_BEAM, new Beam(x + width * 0.33, y + height / 1.35, width / 20, height / 3, fill));
        product.installVBeam(RIGHT_DOOR_WAY_BEAM, new Beam(x + width * 0.715, y + height / 1.35, width / 17.5, height / 3, fill));
        product.installVBeam(LEFT_WINDOW_ROUNDING, new Beam(x + width * 0.25 - 2,  y + height * 0.3 - 2, width / 4, height * 0.35, fill));
        product.installVBeam(RIGHT_WINDOW_ROUNDING, new Beam(x + width * 0.65 - 2, y + height * 0.3 - 2, width / 4, height * 0.35, fill));

    }

    @Override
    public void buildFlags(double x, double y, double width, double height, Paint fill) {
        product.installFlag(OUT_OF_BOUNDS_FLAG, new Flag(x + width, y + height / 2, width / 4 , height / 4, fill));
    }
}
