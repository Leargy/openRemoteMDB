package instructions.concrete.extended;

import entities.Organization;
import entities.OrganizationWithUId;
import instructions.Command;
import instructions.concrete.ConDecree;
import patterns.command.Receiver;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.function.Function;

/**
 * Конкретная команда поиска
 * максимального элемента по дате
 * его создания
 * @author Come_1LL_F00 aka Lenar Khannanov
 * @author Leargy aka Anton Sushkebich
 * @see MaxBy
 * @see ConDecree
 * @see Command
 */
public final class MaxByDate extends MaxBy<Integer, OrganizationWithUId, LocalDateTime> {
  /**
   * Конструктор, устанавливающий ссылку на
   * управленца коллекцией. Также предустанавливает
   * геттер, который находит максимальный элемент по
   * его дате создания
   * @param sieve текущий управленец коллекцией
   */
  public MaxByDate(Receiver<Integer, OrganizationWithUId> sieve) {
    super(sieve, (orga)->(orga.getOrganization().getCreationDate()));
  }

  public static final String NAME = "max_by_date";
  public static final String BRIEF = "находит самый старый элемент коллекции";
  public static final String SYNTAX = NAME;
  public static final int ARGNUM = 0;

  @Override
  public String toString() { return NAME; }
}
