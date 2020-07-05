package sample.drawing_utils.directors.rooms;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import organization.OrganizationWithUId;
import sample.drawing_utils.builders.rooms.RoomBuilder;
import sample.drawing_utils.materials.Room;

public abstract class RoomDirector {
    public static final Color HBEAMS_COLOUR = Color.SANDYBROWN;
    public static final Color VBEAMS_COLOUR = Color.SADDLEBROWN;
    public static final Color DOOR_COLOUR = Color.CYAN;
    public static final Color WINDOWS_COLOUR = Color.BLACK;

    protected RoomBuilder _builder;
    public abstract Room make(OrganizationWithUId organization, Paint colour, Group group);
    public final void changeBuilder(RoomBuilder builder) {
        _builder = builder;
    }
    public final void destroy() { _builder.destroy(); }
}
