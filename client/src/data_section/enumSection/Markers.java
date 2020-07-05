package data_section.enumSection;

/**
 * Класс enum отвечающий за маркеровку обращений к {@link communication.Mediator}
 * @author Leargy aka Anton Sushkevich
 * @author Come_1LL_F00 aka Lenar Khannanov
 */
public enum Markers {
    READ("READ"),
    WRITE("WRITE"),
    STOP("STOP"),
    INTERRUPTED("INTERRUPTED"),
    CONFIRMING("CONFIRMING"),
    BADINPUTCONDITION("BADINPUTCONDITION"),
    GOODINPUTCONDITION("GOODINPUTCONDITION"),
    HASSERVERKEY("HASSERVERKEY"),
    WAIKUP("WAIKUP"),

    UPDATE("UPDATE"),
    INSERT("INSERT"),
    CLEAR("CLEAR"),
    REMOVE("REMOVE"),
    INFO("INFO"),
    USER_CONNECTED("USER_CONNECTED"),
    USER_DISCONNECTED("USER_DISCONNECTED");

    private String name;
    Markers(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
