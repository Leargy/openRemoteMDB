package instructions.concrete.extended;

import entities.Organization;
import entities.OrganizationWithUId;
import patterns.command.Receiver;

/**
 * Команда суммирования по
 * полю ежегодной прибыли корпораций
 * @author Come_1LL_F00 aka Lenar Khannanov
 * @author Leargy aka Anton Sushkevich
 */
public final class SumOfAnnualTurnover extends SumOfAnnual<Integer, OrganizationWithUId, Float> {
  public static final String NAME = "sum_of_annual_turnover";
  public static final String BRIEF = "выводит сумму поля \"turnover\" всех элементов коллекции";
  public static final String SYNTAX = NAME;
  public static final int ARGNUM = 0;
  /**
   * Конструктор, устанавливающий ссылку на
   * управленца коллекцией и геттер,
   * что достает суммируемые поля
   * @param sieve текущий управленец коллекцией
   */
  public SumOfAnnualTurnover(Receiver sieve) {
    super(sieve, OrganizationWithUId::getAnnualTurnover);
  }

  @Override
  public String toString() { return NAME; }
}
