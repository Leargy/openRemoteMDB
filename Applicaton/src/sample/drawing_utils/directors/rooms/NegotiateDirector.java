package sample.drawing_utils.directors.rooms;

import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.util.Pair;
import organization.OrganizationWithUId;
import sample.drawing_utils.mappers.rooms.HallMapper;
import sample.drawing_utils.mappers.rooms.NegotiateMapper;
import sample.drawing_utils.materials.Room;
import sample.drawing_utils.materials.RoomType;

public class NegotiateDirector extends RoomDirector {
    @Override
    public Room make(OrganizationWithUId organization, Paint colour, Group group) {
        _builder.destroy();
        _builder.plan(organization, RoomType.NEGOTIATE);
        Pair<Double, Double> anchor = NegotiateMapper.getLeftUpperCorner(organization, group);
        _builder.buildFacade(anchor.getKey(), anchor.getValue(), NegotiateMapper.DEFAULT_WIDTH, NegotiateMapper.DEFAULT_HEIGHT, colour);
        _builder.buildHBeams(anchor.getKey(), anchor.getValue(), NegotiateMapper.DEFAULT_WIDTH, NegotiateMapper.DEFAULT_HEIGHT, HBEAMS_COLOUR);
        _builder.buildVBeams(anchor.getKey(), anchor.getValue(), NegotiateMapper.DEFAULT_WIDTH, NegotiateMapper.DEFAULT_HEIGHT, VBEAMS_COLOUR);
        _builder.buildWindows(anchor.getKey(), anchor.getValue(), NegotiateMapper.DEFAULT_WIDTH, NegotiateMapper.DEFAULT_HEIGHT, WINDOWS_COLOUR_CLOSED);
        return _builder.getResult();
    }
}
