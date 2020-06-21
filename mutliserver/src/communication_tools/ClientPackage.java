package communication_tools;

import instructions.raw.RawDecree;

import java.io.Serializable;

public class ClientPackage implements Serializable {
    private static final long serialVersionUID = 1L;
    private RawDecree command;
    private Report report;

    public ClientPackage(RawDecree command) {
        this.command = command;
    }

    public ClientPackage(RawDecree commandData, Report stringData) {
        this(commandData);
        this.report = stringData;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public RawDecree getCommand() {
        return command;
    }

    public Report getReport() {return report; }
}