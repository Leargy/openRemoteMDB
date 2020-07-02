package instructions.concrete.base;

import communication.Report;
import organization.OrganizationWithUId;
import parsing.customer.local.TotalCommander;
import patterns.command.Receiver;

/**
 * Команда обновления элемента
 * коллекции по идентификатору
 * @author Come_1LL_F00 aka Lenar Khannanov
 * @author Leargy aka Anton Sushkevich
 * @see parsing.instructions.concrete.ConDecree
 * @see parsing.instructions.Decree
 * @see parsing.instructions.Command
 */
public class Update extends Committer {
  protected final Integer id;

  /**
   * Конструктор, устанавливающий ссылку на
   * управленца коллекцией, а также идентификатор
   * обновляемого элемента и элемент, которым
   * заменяем
   * @param sieve текущий управленец коллекцией
   * @param id идентификатор обновляемого элемента
   * @param added заменитель
   */
  public Update(Receiver sieve, Integer id, OrganizationWithUId organizationWithUId) {
    super(sieve, organizationWithUId);
    this.id = id;
  }

  /**
   * Постигаем дзен, а точнее кайдзен
   */
  @Override
  public Report execute() {
    //TODO: адаптировать логику обновления объекта коллекции для обертки организации
    if (SIEVE == null)
      return new Report(1, "Ссылка на коллекцию не была обнаружена, пожалуйста, свяжитесь с Вашим системным администратором.");
    if (id == null)
      return new Report(1, "Неправильный формат идентификатора обновляемого элемента.");
    if (EMBEDDED == null)
      return new Report(1, "Обнаружена попытка добавить неопределенный элемент.");
    Receiver<Integer, OrganizationWithUId> realSiever = (Receiver<Integer, OrganizationWithUId>) SIEVE;
    Integer key = null;
    Integer[] keys = new Integer[]{id};
    OrganizationWithUId[] findedOrganization = new OrganizationWithUId[]{null};
    realSiever.search(keys, findedOrganization, (org)->(org.getKey().equals(id)));
    key = keys[0];
//    System.out.println(key);
    if (key != null) {
      OrganizationWithUId litmus = EMBEDDED;
      OrganizationWithUId[] buffers = new OrganizationWithUId[]{litmus};
      if (!((TotalCommander)realSiever).checkIfYours(findedOrganization[0],litmus)) {
        return new Report(3, "Заменяемая организация не пренадлежит взаимодействующему пользователю.");
      }
      realSiever.add(keys, buffers, (org)->(true));
      if (buffers[0] != null)
        return new Report(0, "Элемент успешно обновлен.");
      else return new Report(0xCCCF, "Возникли ошибки при добавлении элемента.");
    } else return new Report(0xCCCF, "Элемент с заданным идентификатором не найден.");
  }
  public static final String NAME = "update";
  public static final String BRIEF = "Обновляет значение элемента, id которого равен заданному.";
  public static final String SYNTAX = NAME + " [id] {element}";
  public static final int ARGNUM = 2;

  @Override
  public String toString() {
    return NAME;
  }
}
