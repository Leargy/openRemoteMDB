package sample.drawing_utils.builders.rooms;

import javafx.scene.paint.Paint;
import organization.OrganizationWithUId;
import sample.drawing_utils.materials.Room;
import sample.drawing_utils.materials.RoomType;

public abstract class RoomBuilder {
    protected Room product;
    public final void destroy() {
        product = null;
    }

    public final Room getResult() {
        return product;
    }

    public final void plan(OrganizationWithUId organization, RoomType type) {
        product = new Room(organization, type);
    }

    public abstract void buildFacade(double x, double y, double width, double height, Paint fill);
    public abstract void buildWindows(double x, double y, double width, double height, Paint fill);
    public abstract void buildDoors(double x, double y, double width, double height, Paint fill);
    public abstract void buildHBeams(double x, double y, double width, double height, Paint fill);
    public abstract void buildVBeams(double x, double y, double width, double height, Paint fill);
    public abstract void buildFlags(double x, double y, double width, double height, Paint fill);
}
