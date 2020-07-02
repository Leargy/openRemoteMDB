package instructions.concrete.extended;

import organization.OrganizationWithUId;
import patterns.command.Receiver;

public final class FilterContainsName extends FilterContains<Integer, OrganizationWithUId, String> {
  /**
   * Обычный конструктор, связывающий
   * команду с исполнителем и геттером,
   * которым достаем ЗНАЧЕНИЕ поля.
   *
   * @param sieve  ссылка на ресивер
   * @param filter поле, которое ищем
   */
  public FilterContainsName(Receiver sieve, String filter) {
    super(sieve, filter, OrganizationWithUId::getName);
  }

  public static final String NAME = "filter_contains_name";
  public static final String BRIEF = "Находит элементы коллекции по заданному имени";
  public static final String SYNTAX = NAME + " [name]";
  public static final int ARGNUM = 1;

  @Override
  public String toString() { return NAME; }
}
