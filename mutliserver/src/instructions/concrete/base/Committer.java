package instructions.concrete.base;

import entities.Organization;
import entities.OrganizationWithUId;
import instructions.concrete.ConcreteDecree;
import patterns.command.Receiver;

/**
 * Абстракция всех комманд, добавляющих что-то в коллекцию
 * @author Come_1LL_F00 aka Lenar Khannanov
 * @author Leargy aka Anton Sushkevich
 * @see ConDecree
 * @see parsing.instructions.Decree
 * @see parsing.instructions.Command
 */
public abstract class Committer extends ConcreteDecree {
  protected final OrganizationWithUId EMBEDDED;
  /**
   * Конструктор, устанавливающий ссылку на
   * управленца коллекцией, а также объект
   * добавляемого элемента
   * @param sieve текущий управленец коллекцией
   * @param added добавляемый элемент
   */
  public Committer(Receiver sieve, OrganizationWithUId organizationWithUId) {
    super(sieve);
    EMBEDDED = organizationWithUId;
  }

  /**
   * Метод, возвращающий
   * название команды
   * @return название команды
   */
  @Override
  public String toString() { return NAME; }
}
