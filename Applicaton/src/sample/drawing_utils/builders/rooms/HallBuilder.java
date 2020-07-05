package sample.drawing_utils.builders.rooms;

import javafx.scene.paint.Paint;
import sample.drawing_utils.materials.Beam;
import sample.drawing_utils.materials.Door;
import sample.drawing_utils.materials.Facade;
import sample.drawing_utils.materials.Window;

public class HallBuilder extends RoomBuilder {
    public static final int SITE = 0;
    public static final int LEFT_WINDOW = 0;
    public static final int RIGHT_WINDOW = 1;
    public static final int BOTTOM_BEAM = 0;
    public static final int LEFT_BEAM = 0;
    public static final int RIGHT_BEAM = 1;
    public static final int CENTER_DOOR = 0;
    @Override
    public void buildFacade(double x, double y, double width, double height, Paint fill) {
        product.installFacade(new Facade(x, y, width, height, fill));
    }

    @Override
    public void buildWindows(double x, double y, double width, double height, Paint fill) {
        product.installWindow(LEFT_WINDOW, new Window(x + width * 0.15,  y + height * 0.3, width / 5, height * 0.3, fill));
        product.installWindow(RIGHT_WINDOW, new Window(x + 0.65 * width, y + height * 0.3, width / 5, height * 0.3, fill));
    }

    @Override
    public void buildDoors(double x, double y, double width, double height, Paint fill) {
        product.installDoor(CENTER_DOOR, new Door(x + width * 0.3, y + height / 1.25, width / 2.5, height / 5, fill));
    }

    @Override
    public void buildHBeams(double x, double y, double width, double height, Paint fill) {
        product.installHBeam(BOTTOM_BEAM, new Beam(x - 2, y + height - 1, width * 1.2, height / 10, fill));
    }

    @Override
    public void buildVBeams(double x, double y, double width, double height, Paint fill) {
        product.installVBeam(LEFT_BEAM, new Beam(x, y, width / 10, height, fill));
        product.installVBeam(RIGHT_BEAM, new Beam(x + width, y, width / 10, height, fill));
    }

    @Override
    public void buildFlags(double x, double y, double width, double height, Paint fill) {
        System.out.println("There are no flags in hall");
    }
}
