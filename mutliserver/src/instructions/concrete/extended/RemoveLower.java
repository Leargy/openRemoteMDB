package instructions.concrete.extended;

import communication.Report;
import entities.Organization;
import entities.OrganizationWithUId;
import entities.comparators.OrganizationTitleComparator;
import parsing.customer.Indicator;
import patterns.command.Receiver;

import java.util.Map;

public class RemoveLower extends RemoveThan {
  protected final Indicator MENACE;
  /**
   * Конструктор, устанавливающий ссылку на
   * управленца коллекцией, а также объект
   * добавляемого элемента
   *
   * @param sieve текущий управленец коллекцией
   * @param added добавляемый элемент
   */
  public RemoveLower(Receiver sieve, OrganizationWithUId added) {
    super(sieve, added);
    OrganizationTitleComparator cmp = new OrganizationTitleComparator();
    MENACE = (org)->(cmp.compare(((OrganizationWithUId)org).getOrganization(), EMBEDDED.getOrganization()) < 0);
  }

  /**
   * Метод исполнения, для этих команд общее
   * @return отчет выполнения
   */
  @Override
  public Report execute() {
        int[] deletedNumber = new int[]{0};
        Receiver<Integer, OrganizationWithUId> REAL = (Receiver<Integer, OrganizationWithUId>) SIEVE;
        StringBuilder result = new StringBuilder();
        Map<Integer, Integer> keys = REAL.getBy(OrganizationWithUId::getKey); //TODO: тут было Organization::getKey
        keys
            .entrySet()
            .stream()
            .forEach((Map.Entry<Integer, Integer> e)-> {
              OrganizationWithUId taken = null;
              OrganizationWithUId[] takens = new OrganizationWithUId[]{taken}; //там где OrganizationWithUId было Organization
              REAL.search(new Integer[]{e.getKey()}, takens, (org)->(true));
              OrganizationTitleComparator cmp = new OrganizationTitleComparator();
              REAL.remove(new Integer[]{e.getKey()}, new OrganizationWithUId[]{takens[0]}, MENACE);
                  if (MENACE.verify(takens[0])) {
                      result.append("Элемент " + takens[0] + " по ключу " + e.getKey() + " удален\n");
                      deletedNumber[0]++;
                  }
            });
        result.append("Элементов удалено: " + deletedNumber[0]);
        return new Report(0, result.toString());
  }

  public static final String NAME = "remove_lower";
  public static final String BRIEF = "удаляет из коллекции элементы, меньшие чем заданный";
  public static final String SYNTAX = NAME + " {element}";
  public static final int ARGNUM = 1;

    @Override
    public String toString() { return NAME; }
}
