package sample.dialog_windows.communication.enum_section;

public enum  Markers {
    NEXTSTAGE("NEXTSTAGE"),
    PRIVIOUSSTAGE("PRIVIOUSSTAGE"),
    SENDALLERT("SENDALLERT"),
    SENDCOMMAND("SENDCOMMAND"),
    SETCONNECTION("SETCONNECTION"),
    RESETCONNECCTION("RESETCONNECCTION");
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
