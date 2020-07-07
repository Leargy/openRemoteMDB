package sample.drawing_utils.directors.rooms;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Pair;
import organization.OrganizationWithUId;
import sample.drawing_utils.mappers.rooms.HallMapper;
import sample.drawing_utils.materials.Room;
import sample.drawing_utils.materials.RoomType;

public class HallDirector extends RoomDirector {
    @Override
    public Room make(OrganizationWithUId organization, Paint colour, Group group) {
        _builder.destroy();
        _builder.plan(organization, RoomType.HALL);
        Pair<Double, Double> anchor = HallMapper.getLeftUpperCorner(organization, group);
        _builder.buildFacade(anchor.getKey(), anchor.getValue(), HallMapper.DEFAULT_WIDTH, HallMapper.DEFAULT_HEIGHT, colour);
        _builder.buildHBeams(anchor.getKey(), anchor.getValue(), HallMapper.DEFAULT_WIDTH, HallMapper.DEFAULT_HEIGHT, HBEAMS_COLOUR);
        _builder.buildVBeams(anchor.getKey(), anchor.getValue(), HallMapper.DEFAULT_WIDTH, HallMapper.DEFAULT_HEIGHT, VBEAMS_COLOUR);
        _builder.buildWindows(anchor.getKey(), anchor.getValue(), HallMapper.DEFAULT_WIDTH, HallMapper.DEFAULT_HEIGHT, WINDOWS_COLOUR_OPENED);
        _builder.buildDoors(anchor.getKey(), anchor.getValue(), HallMapper.DEFAULT_WIDTH, HallMapper.DEFAULT_HEIGHT, DOOR_COLOUR);
        if (HallMapper.isOutOfBounds(organization, group))
            _builder.buildFlags(anchor.getKey(), anchor.getValue(), HallMapper.DEFAULT_WIDTH, HallMapper.DEFAULT_HEIGHT, Color.RED);
        return _builder.getResult();
    }
}
