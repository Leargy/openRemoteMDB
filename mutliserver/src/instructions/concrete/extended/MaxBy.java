package instructions.concrete.extended;

import communication.Report;
import organization.Mappable;
import organization.OrganizationWithUId;
import instructions.concrete.ConcreteDecree;
import patterns.command.Receiver;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;

/**
 * Команда, находящая информацию об
 * элементе, имеющего поле с максимальным значением
 * @param <K> ключ коллекции
 * @param <R> поле, по которому сравниваем
 * @author Come_1LL_F00 aka Lenar Khannanov
 * @author Leargy aka Anton Sushkevich
 */
public class MaxBy<K extends Integer, V extends Mappable<K>, R extends Comparable<? super R>> extends ConcreteDecree {
  protected final Function<? super V,? extends R> keySearcher;
  /**
   * Конструктор, устанавливающий ссылку на
   * управленца коллекцией, а также
   * устанавливает геттер, которым мы берем поле
   * @param sieve текущий управленец коллекцией
   */
  public MaxBy(Receiver<K, V> sieve, Function<V, R> field) {
    super(sieve);
    keySearcher = field;
  }

  /**
   * Метод по нахождению максимального
   * элемента по определенному полю
   * @return информацию об элементе с максимальным значением поля
   */
  @Override
  public Report execute() {
    // взять HashMap из коллекции, где ключи - ключи организации, а значения - поле его
    Map<K, R> buffer = SIEVE.getBy(keySearcher);
    // найти в отображении максимальный элемент, вернуть его ключ
    Optional<Map.Entry<K, R>> maxim =
        buffer.entrySet()
        .stream()
        .max((Map.Entry<K, R> enter1, Map.Entry<K, R> enter2)->(enter1.getValue().compareTo(enter2.getValue())));
    K maxim_key;
    try {
      maxim_key = maxim.get().getKey();
    } catch (NoSuchElementException e) { return new Report(404, "Подходящий элемент не найден, проверьте размер коллекции"); }
    // найти в исходной коллекции тот элемент
    OrganizationWithUId recology = null;
    OrganizationWithUId[] recologys = new OrganizationWithUId[]{recology};
    Integer[] maxim_keys = new Integer[]{maxim_key};
    SIEVE.search(maxim_keys, recologys, (element)->true);
    // вернуть информацию об элементе
    return new Report(0, "\tKEY: " + maxim_keys[0] + ";\n\tVALUE: " + recologys[0].getOrganization().toString() + ";\n\tOWNER: " + recologys[0].getUserLogin() + "\n");
  }

  @Override
  public String toString() { return NAME; }
}
