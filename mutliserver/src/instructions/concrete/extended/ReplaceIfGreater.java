package instructions.concrete.extended;

import communication.Report;
import entities.Organization;
import entities.OrganizationWithUId;
import entities.comparator.OrganizationTitleComparator;
import parsing.customer.distro.LimboKeeper;
import parsing.customer.local.TotalCommander;
import patterns.command.Receiver;

import java.util.Map;

public class ReplaceIfGreater extends ReplaceIf {
  public ReplaceIfGreater(LimboKeeper r, Integer p, OrganizationWithUId o) {
    super(r, p, o);
  }

  /**
   * Общий метод исполнения для всех исполняемых комманд
   */
  @Override
  public Report execute() {
    //TODO: подправить логику исполнения
    Receiver<Integer, OrganizationWithUId> REAL = (Receiver<Integer, OrganizationWithUId>)SIEVE;
    Map<Integer, Integer> keys = REAL.getBy(OrganizationWithUId::getKey);
    if (keys.containsKey(KEY)) {
      OrganizationWithUId replaced = null;
      OrganizationWithUId[] replaceds = new OrganizationWithUId[]{replaced};
      Integer[] KEYS = new Integer[]{KEY};
      REAL.search(KEYS, replaceds, (org)->(true));
      if (!((TotalCommander)SIEVE).checkIfYours(replaceds[0],EMBEDDED)) return new Report(3, "Заменяемая организация не пренадлежит взаимодействующему пользователю.");
      boolean isReplaced = new OrganizationTitleComparator().compare(EMBEDDED.getOrganization(), replaceds[0].getOrganization()) > 0;
      REAL.add(KEYS, new OrganizationWithUId[]{EMBEDDED}, (org)->(isReplaced));
      REAL.search(new Integer[]{null}, replaceds, (org)->(true));
      if (!(replaceds[0] == EMBEDDED) && (isReplaced))
        return new Report(0, "Проведена успешная замена по ключу: " + KEYS[0]);
      else return new Report(0, "Заменить элемент не удалось, т.к. "
          + (isReplaced? "произошла ошибка при замене": "элемент оказался меньше"));
    } else return new Report(2, "Ключ " + KEY + " не найден в коллекции");
  }

  public static final String NAME = "replace_if_greater";
  public static final String BRIEF = "заменяет на новое значение по ключу [key], если оно больше старого";
  public static final String SYNTAX = NAME + " [key] {element}";
  public static final int ARGNUM = 2;

  @Override
  public String toString() { return NAME; }
}
