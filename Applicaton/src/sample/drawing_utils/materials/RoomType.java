package sample.drawing_utils.materials;

public enum RoomType {
    HALL(2, 1, 2, 1, 0),
    OFFICES(4, 0, 3, 2, 0),
    CANTEEN(3, 0, 2, 4, 0),
    STORAGE(0, 0, 4, 4, 0),
    NEGOTIATE(3, 0, 2, 1, 0),
    BOSS_ROOM(1, 0, 2, 2, 0),
    ATTIC(1, 0, 3, 3, 0),
    ROOF(0, 0, 2, 2, 1);
    public final int WINDOW_COUNT;
    public final int DOOR_COUNT;
    public final int VERTICAL_BEAM_COUNT;
    public final int HORIZONTAL_BEAM_COUNT;
    public final int FLAG_COUNT;

    RoomType(int windows, int doors, int vertical, int horizontal, int flags) {
        WINDOW_COUNT = windows;
        DOOR_COUNT = doors;
        VERTICAL_BEAM_COUNT = vertical;
        HORIZONTAL_BEAM_COUNT = horizontal;
        FLAG_COUNT = flags;
    }

}
