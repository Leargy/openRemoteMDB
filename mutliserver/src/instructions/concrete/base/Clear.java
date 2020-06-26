package instructions.concrete.base;

import communication.Report;
import instructions.concrete.ConcreteDecree;
import patterns.command.Receiver;

/**
 * Команда очистки коллекции
 * @author Come_1LL_F00 aka Lenar Khannanov
 * @author Leargy aka Anton Sushkevich
 * @see instructions.concrete.ConDecree
 * @see parsing.instructions.Decree
 * @see Command
 */
public class Clear extends ConcreteDecree {
  private String userLogin;
  /**
   * Конструктор, устанавливающий ссылку на
   * управленца коллекцией, для команды
   * по очистке коллекции
   * @param sieve текущий управленец коллекцией
   */
  public Clear(Receiver sieve,String userLogin) {
    super(sieve);
    this.userLogin = userLogin;
  }

  /**
   * Магия, очищающая коллекцию
   * @return отчет об успешности выполнения команды
   */
  @Override
  public Report execute() {
    int numOfDeletedArguments = 0;
    if (SIEVE == null)
      return new Report(1, "Ссылка на коллекцию не была обнаружена, пожалуйста, свяжитесь с Вашим системным администратором");
    try {
      numOfDeletedArguments = SIEVE.clear(userLogin); //clear concrete user's organizations
    } catch (UnsupportedOperationException e) {
      return new Report(0xBAD, "Извините, но данный тип коллекции не поддерживает операцию очистки");
    }
    if (numOfDeletedArguments == 0) return new Report(0,"Ваших элементов в коллекции не было найдено.\n\t Удалено элементов: " + numOfDeletedArguments);
    return new Report(0, "Очистка коллекции успешна.\n\t Удалено элементов: " + numOfDeletedArguments);
  }

//  public void setUserLogin(String userLogin) {
//    this.userLogin = userLogin;
//  }

  public static final String NAME = "clear";
  public static final String BRIEF = "очищает коллекцию";
  public static final String SYNTAX = NAME;
  public static final int ARGNUM = 0;

  /**
   * Стандартный метод из
   * object'а, возвращающий строку
   * как результат
   * @return название команды
   */
  @Override
  public String toString() { return NAME; }
}
