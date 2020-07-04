package communication;

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
    private String passWord;

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
     * Конструктор для индифицирующей передачей
     * @param command
     * @param login
     * @param passWord
     */
    public ClientPackage(RawDecree command, String login, String passWord ) {
        this(command);
        this.login = login;
        this.passWord = passWord;
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
    public String getPassWord() { return passWord; }
    public Report getReport() { return report; }
    public String getLogin() { return login; }

    public void setReport(Report report) { this.report = report; }
}