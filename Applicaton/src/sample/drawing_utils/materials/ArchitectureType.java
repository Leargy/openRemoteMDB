package sample.drawing_utils.materials;

public enum ArchitectureType {
    HALLED(1, 1),
    STORAGED(8, 2),
    CANTEENED(1024, 3),
    OFFICED(65536, 4),
    NEGOTIATED(2097152, 5),
    BOSSED(33554432, 6),
    ATTICED(268435456, 7),
    ROOFED(1073741824, 8);
    public final int REQUIRED_EMPLOYEES;
    public final int ROOMS_NUMBER;

    ArchitectureType(int requiredEmployees, int roomsNumber) {
        REQUIRED_EMPLOYEES = requiredEmployees;
        ROOMS_NUMBER = roomsNumber;
    }

    public static ArchitectureType defineType(int currentEmployees) {
        if (currentEmployees >= ROOFED.REQUIRED_EMPLOYEES)
            return ROOFED;
        else if (currentEmployees >= ATTICED.REQUIRED_EMPLOYEES)
            return ATTICED;
        else if (currentEmployees >= BOSSED.REQUIRED_EMPLOYEES)
            return BOSSED;
        else if (currentEmployees >= NEGOTIATED.REQUIRED_EMPLOYEES)
            return NEGOTIATED;
        else if (currentEmployees >= OFFICED.REQUIRED_EMPLOYEES)
            return OFFICED;
        else if (currentEmployees >= CANTEENED.REQUIRED_EMPLOYEES)
            return CANTEENED;
        else if (currentEmployees >= STORAGED.REQUIRED_EMPLOYEES)
            return STORAGED;
        else return HALLED;
    }
}
