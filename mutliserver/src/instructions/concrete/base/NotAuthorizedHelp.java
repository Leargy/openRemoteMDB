package instructions.concrete.base;

import communication.Report;
import instructions.concrete.ConcreteDecree;
import patterns.command.Receiver;

import java.util.ArrayList;
import java.util.List;

public class NotAuthorizedHelp extends ConcreteDecree {
    /**
     * Конструктор, устанавливающий ссылку на
     * управленца коллекцией
     *
     * @param sieve текущий управленец коллекцией
     */
    public NotAuthorizedHelp(Receiver sieve) {
        super(sieve);
    }


    private final List<String[]> CMDS = new ArrayList<>();
    {
        CMDS.add(new String[]{ Help.NAME, Help.BRIEF, Help.SYNTAX });
        CMDS.add(new String[]{ SignUp.NAME, SignUp.BRIEF, SignUp.SYNTAX});
        CMDS.add(new String[]{ SignIn.NAME, SignIn.BRIEF, SignIn.SYNTAX});
    }
    /**
     * Пишем альманах всех комманд,
     * дабы друиды могли вызвать сатану
     * @return отчет о работе команды
     */
    @Override
    public Report execute() {
        StringBuilder manual = new StringBuilder();
        CMDS
                .stream()
                .forEach(
                        (o)->
                        {
                            manual.append(less(o[0], o[1], o[2]));
                        });
        return new Report(0, "Список доступных комманд:\n" + manual.toString());
    }

    /**
     * Вспомогательный метод
     * формирования информации о команде
     * @param NAME название команды
     * @param BRIEF краткое описание команды
     * @param SYNTAX синтаксис команды
     * @return информация по команде
     */
    private final String less(String NAME, String BRIEF, String SYNTAX) {
        StringBuilder page = new StringBuilder();
        page.append("name: " + NAME + " -- brief: " + BRIEF + "\n");
        page.append("synopsys: " + SYNTAX + "\n");
        return page.toString();
    }
    public static final String NAME = "help";
    public static final String BRIEF = "выводит справку по командам, доступным неавторезированному пользователю";
    public static final String SYNTAX = NAME;
    public static final int ARGNUM = 0;

    /**
     * Метод возврата
     * названия команды
     * @return название команды
     */
    @Override
    public String toString() { return NAME; }
}
