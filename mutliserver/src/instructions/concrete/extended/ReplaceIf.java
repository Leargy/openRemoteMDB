package instructions.concrete.extended;

import entities.Organization;
import entities.OrganizationWithUId;
import instructions.concrete.base.Committer;
import patterns.command.Receiver;

public abstract class ReplaceIf extends Committer {
  protected final Integer KEY;
  /**
   * Конструктор, устанавливающий ссылку на
   * управленца коллекцией, а также объект
   * добавляемого элемента
   *
   * @param sieve текущий управленец коллекцией
   * @param added добавляемый элемент
   */
  public ReplaceIf(Receiver sieve, Integer key, OrganizationWithUId added) {
    super(sieve, added);
    KEY = key;
  }
}
