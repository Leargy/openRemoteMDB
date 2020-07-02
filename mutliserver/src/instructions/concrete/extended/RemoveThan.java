package instructions.concrete.extended;

import organization.OrganizationWithUId;
import instructions.concrete.base.Committer;
import patterns.command.Receiver;


/**
 * Абстракция команд, удаляющих
 * все элементы по условию
 * @author Come_1LL_F00 aka Lenar Khannanov
 * @author Leargy aka Anton Sushkevich
 */
public abstract class RemoveThan extends Committer {
  /**
   * Конструктор, устанавливающий ссылку на
   * управленца коллекцией, а также объект
   * добавляемого элемента
   * @param sieve текущий управленец коллекцией
   * @param added добавляемый элемент
   * @param menace условие удаления
   */
  public RemoveThan(Receiver sieve, OrganizationWithUId added) {
    super(sieve, added);
  }
}
