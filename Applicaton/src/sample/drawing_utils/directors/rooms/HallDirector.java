package sample.drawing_utils.directors.rooms;

import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.util.Pair;
import organization.OrganizationWithUId;
import sample.drawing_utils.mappers.rooms.BossMapper;
import sample.drawing_utils.mappers.rooms.HallMapper;
import sample.drawing_utils.materials.Room;
import sample.drawing_utils.materials.RoomType;

public class HallDirector extends RoomDirector {
    @Override
    public Room make(OrganizationWithUId organization, Paint colour, Group group) {
        _builder.destroy();
        _builder.plan(organization, RoomType.BOSS_ROOM);
        Pair<Double, Double> anchor = HallMapper.getLeftUpperCorner(organization, group);
        _builder.buildFacade(anchor.getKey(), anchor.getValue(), HallMapper.DEFAULT_WIDTH, HallMapper.DEFAULT_HEIGHT, colour);
        _builder.buildHBeams(anchor.getKey(), anchor.getValue(), HallMapper.DEFAULT_WIDTH, HallMapper.DEFAULT_HEIGHT, HBEAMS_COLOUR);
        _builder.buildVBeams(anchor.getKey(), anchor.getValue(), HallMapper.DEFAULT_WIDTH, HallMapper.DEFAULT_HEIGHT, VBEAMS_COLOUR);
        _builder.buildWindows(anchor.getKey(), anchor.getValue(), HallMapper.DEFAULT_WIDTH, HallMapper.DEFAULT_HEIGHT, WINDOWS_COLOUR);
        _builder.buildDoors(anchor.getKey(), anchor.getValue(), HallMapper.DEFAULT_WIDTH, HallMapper.DEFAULT_HEIGHT, DOOR_COLOUR);
        return _builder.getResult();
    }
}
