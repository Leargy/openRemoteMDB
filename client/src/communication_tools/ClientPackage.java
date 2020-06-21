package communication_tools;

import instructions.rotten.RawDecree;

import java.io.Serializable;

/**
 * Класс, объектами которого возможно будет осуществляться обмен с сервером.
 * @author Leargy aka Anton Sushkevich
 * @author Come_1LL_F00 aka Lenar Khannanov
 */
public class ClientPackage implements Serializable {
    private static final long serialVersionUID = 1L; //serialisation indicator
    private RawDecree command;
    private Report report;
    private String login;
    private String password;

    /**
     * Базовый конструктор,
     * принимающий сырую команду
     * в качестве клиентского запроса
     * @param command сырая команда
     */
    public ClientPackage(RawDecree command) {
        this.command = command;
    }

    /**
     * Допконструктор, принимающий
     * сырую команду в качестве запроса,
     * и отчет о выполнении команды, в
     * качестве результата работы команды
     * @param commandData клиентский запрос
     * @param stringData отчет о работе
     */
    public ClientPackage(RawDecree commandData, Report stringData) {
        this(commandData);
        this.report = stringData;
    }

    public RawDecree getCommand() {
        return command;
    }
    public Report getReport() {return report; }
    public void setReport(Report report) {
        this.report = report;
    }
}
