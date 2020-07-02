package entities.organizationFactory;


import communication.Component;
import organization.*;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Фабрика основных элементво коллекции
 * @author Come_1LL_F00 aka Lenar Khannanov
 * @author Leargy aka Anton Sushkevich
 */
public final class OrganizationBuilder implements Factory<Organization>, Component {
//  private final Mediator mediator; // ссылка на медиатор
  private final Factory<Address> addressBuilder = new AddressBuilder();
  private final Factory<Coordinates> cordBuilder = new CoordinatesBuilder();
  private final TypeBuilder typeBuilder = this.new TypeBuilder();
  private final ExecutorService organizationService = Executors.newFixedThreadPool(2);

  /**
   * Стандартный конструктор, принимающий
   * заказчика организаций
   * @param controller получатель продукции
   */
//  public OrganizationBuilder(Mediator mediator) {
//    this.mediator = mediator;
//  }

  /**
   * Метод, создающий экземпляр типа Organization
   * Возвращает null, если недостаточно параметров
   * для сборки
   * @param parts объект сериализации, хранящий лишь неупорядоченные данные
   * @return собранный продукт или null
   */
  @Override
  public synchronized Organization make(Junker parts) {
//    Future<Address> addressFuture = null; для многопоточности
//    Future<Coordinates> coordinatesFuture = null;
    // проверка не передали ли null
    if (parts == null) return null;
    // достаем параметры для адреса и координат
    Junker[] guts = parts.Guts();
    // если их не оказалось, то собрать не можем
    if ((guts == null) || guts.length == 0) return null;
    // буфер для координат
    Coordinates cord = null;
    // буфер для адреса
    Address address = null;
    // если нет параметров для адреса
    // то вернем null, так как поле может быть null'ом
    if (guts.length < 2)
      address = null;
    else
      // иначе собираем из параметров
//      addressFuture = organizationService.submit(() -> addressBuilder.make(guts[1])); для многопоточности
      address = addressBuilder.make(guts[1]);
    // собираем координаты
//    coordinatesFuture = organizationService.submit(() -> cordBuilder.make(guts[0]));  для многпоточности
    cord = cordBuilder.make(guts[0]);
    // достаем строковые параметры
    String[] lines = parts.Lines();
    if ((lines == null) || (lines.length == 0)) return null;
    // буфер названия и полного названия
    String name, fullname;
    // буфер для типа организации
    OrganizationType type;
    // разбираем строковые параметры
    // если указан лишь один параметр
    if (lines.length == 1) {
      // проверка указан ли хотя бы один параметр
      if (lines[0] == null || lines[0].isEmpty()) return null;
      else {
        // если указан, то значения по умолчанию
        name = lines[0];
        fullname = "";
        type = null;
      }
      // если зарезервировано два и более параметра
    } else {
      // проверка можно ли заменить первый пустой - вторым заполненным
      if ((lines[0] == null) || (lines[0].isEmpty())) {
        if (lines[1] == null)
          return null;
        else {
          // подмена непустой строкой
          name = lines[1];
          fullname = "";
        }
      } else {
        // валидное присвоение
        name = lines[0];
        fullname = (lines[1] == null) ? "" : lines[1];
      }
      // формируем тип из строки
      type = (lines.length == 2)? null : typeBuilder.make(lines[2]);
    }
    // формируем целочисленные параметры
    long[] digits = parts.Digits();
    // проверка установленность параметров
    if ((digits == null) || (digits.length == 0)) return null;
    int employeesCount = 1;
    // установка правильных значений
    if (digits[0] > 0) employeesCount = (int) digits[0];
    // формируем действительные числовые параметры
    double[] cogits = parts.Cogits();
    if ((cogits == null) || (cogits.length == 0)) return null;
    float annualTurnover = Float.MIN_VALUE;
    // проверка на меньше нуля с машинной погрешностью, относительно типа одинарной точности
    if (!(cogits[0] + Float.MIN_VALUE < 0.0)) annualTurnover = (float) cogits[0];
    //Достаем из потоков результаты работы
//    try {
//      address = addressFuture.get();
//      cord = coordinatesFuture.get();
//    }catch (ExecutionException | InterruptedException ex) {
//      System.out.println(ex.getMessage());
//      /*NOPE*/
//    }
    return new Organization(name, cord, annualTurnover, fullname, employeesCount, type,  address, -1, LocalDateTime.now());
  }

  /**
   * Сущность, что формирует тип
   * компании по входной строке
   * Так как данное поле не обязательно,
   * то его можно оставить null
   */
  public class TypeBuilder {
    /**
     * Магия древних формирует
     * валидную организацию
     * @param acronim строковое представление типа
     * @return тип организации
     */
    public OrganizationType make(String acronim) {
      if ((acronim == null) || (acronim.isEmpty()))
        return null;
      boolean isContains = false;
      for (OrganizationType t : OrganizationType.values())
        if (t.toString().toLowerCase().startsWith(acronim.toLowerCase())) {
          acronim = t.toString();
          isContains = true;
          break;
        }
      return (!isContains)? null : OrganizationType.valueOf(acronim);
    }
  }
}
