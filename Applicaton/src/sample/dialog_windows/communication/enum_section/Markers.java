package sample.dialog_windows.communication.enum_section;

public enum  Markers {
    NEXTSTAGE("NEXTSTAGE"),
    PRIVIOUSSTAGE("PRIVIOUSSTAGE"),
    SENDALLERT("SENDALLERT"),
    SENDCOMMAND("SENDCOMMAND"),
    SETCONNECTION("SETCONNECTION"),
    RESETCONNECCTION("RESETCONNECCTION"),
    STOP("STOP"),

    UPDATE("UPDATE"),
    INSERT("INSERT"),
    CLEAR("CLEAR"),
    REMOVE("REMOVE");
//    BADINPUTENCE("BADINPUTENCE");


    private String name;
    Markers(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
