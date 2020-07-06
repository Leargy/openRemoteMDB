package sample.drawing_utils.directors.rooms;

import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.util.Pair;
import organization.OrganizationWithUId;
import sample.drawing_utils.builders.rooms.RoomBuilder;
import sample.drawing_utils.mappers.rooms.AtticMapper;
import sample.drawing_utils.materials.Room;
import sample.drawing_utils.materials.RoomType;

public class AtticDirector extends RoomDirector {

    @Override
    public Room make(OrganizationWithUId organization, Paint colour, Group group) {
        _builder.destroy();
        _builder.plan(organization, RoomType.ATTIC);
        Pair<Double, Double> anchor = AtticMapper.getLeftUpperCorner(organization, group);
        _builder.buildFacade(anchor.getKey(), anchor.getValue(), AtticMapper.DEFAULT_WIDTH, AtticMapper.DEFAULT_HEIGHT, colour);
        _builder.buildHBeams(anchor.getKey(), anchor.getValue(), AtticMapper.DEFAULT_WIDTH, AtticMapper.DEFAULT_HEIGHT, HBEAMS_COLOUR);
        _builder.buildVBeams(anchor.getKey(), anchor.getValue(), AtticMapper.DEFAULT_WIDTH, AtticMapper.DEFAULT_HEIGHT, VBEAMS_COLOUR);
        _builder.buildWindows(anchor.getKey(), anchor.getValue(), AtticMapper.DEFAULT_WIDTH, AtticMapper.DEFAULT_HEIGHT, WINDOWS_COLOUR_CLOSED);
        return _builder.getResult();
    }
}
