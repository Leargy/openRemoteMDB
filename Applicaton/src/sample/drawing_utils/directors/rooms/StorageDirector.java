package sample.drawing_utils.directors.rooms;

import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.util.Pair;
import organization.OrganizationWithUId;
import sample.drawing_utils.mappers.rooms.RoofMapper;
import sample.drawing_utils.mappers.rooms.StorageMapper;
import sample.drawing_utils.materials.Room;
import sample.drawing_utils.materials.RoomType;

public class StorageDirector extends RoomDirector {
    @Override
    public Room make(OrganizationWithUId organization, Paint colour, Group group) {
        _builder.destroy();
        _builder.plan(organization, RoomType.STORAGE);
        Pair<Double, Double> anchor = StorageMapper.getLeftUpperCorner(organization, group);
        _builder.buildFacade(anchor.getKey(), anchor.getValue(), StorageMapper.DEFAULT_WIDTH, StorageMapper.DEFAULT_HEIGHT, colour);
        _builder.buildHBeams(anchor.getKey(), anchor.getValue(), StorageMapper.DEFAULT_WIDTH, StorageMapper.DEFAULT_HEIGHT, HBEAMS_COLOUR);
        _builder.buildVBeams(anchor.getKey(), anchor.getValue(), StorageMapper.DEFAULT_WIDTH, StorageMapper.DEFAULT_HEIGHT, VBEAMS_COLOUR);
        return _builder.getResult();
    }
}
