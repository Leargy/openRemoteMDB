package entities.organizationFactory;


import communication.Component;
import exceptions.PartNotFoundException;
import organization.*;
import sample.dialog_windows.handlers.exceptions.ParsingException;

import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Фабрика основных элементво коллекции
 * @author Come_1LL_F00 aka Lenar Khannanov
 * @author Leargy aka Anton Sushkevich
 */
public final class OrganizationBuilder implements Factory<Organization>, Component {
//  private final Mediator mediator; // ссылка на медиатор
  private final Factory<Address> addressBuilder = new AddressBuilder();
  private final Factory<Coordinates> cordBuilder = new CoordinatesBuilder();
  private final TypeBuilder typeBuilder = new TypeBuilder();
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

  @Override
  public Organization makeWithParsing(String rawData) throws PartNotFoundException {
    StringBuilder exceptionResult = new StringBuilder();
    int coor_x = 0;
    Float coord_y = null;
    long loc_x = 0;
    long loc_y = 0;
    float loc_z = 0;
    OrganizationType organizationType = null;
    float annualTurnover = 0f; int employeesCount = 0;
    String name = "";
    String fullName = null;
    String zip_cod = null;
    LocalDateTime creation_date = null;

    for (int i = 1; i <= 12; i++) {
      try {
        switch (i) {
          case 1: name = getName(rawData);
            break;
          case 2: fullName = getFullName(rawData);
            break;
          case 3: coor_x = getCoordX(rawData);
            break;
          case 4: coord_y = getCoordY(rawData);
            break;
          case 5: employeesCount = getEmploysCount(rawData);
            break;
          case 6: annualTurnover = getAnnualTurnover(rawData);
            break;
          case 7: organizationType = getType(rawData);
            break;
          case 8: zip_cod = getzipCod(rawData);
            break;
          case 9: loc_x = getLocationX(rawData);
            break;
          case 10: loc_y = getLocationY(rawData);
            break;
          case 11: loc_z = (float) getLocationZ(rawData);
            break;
          case 12: creation_date = getCreationData(rawData);
            break;
        }
      }catch (PartNotFoundException ex) {
        exceptionResult.append(ex.getMessage() + "\n");
      }
    }
    if (exceptionResult.toString().isEmpty() == false) throw new PartNotFoundException(exceptionResult.toString());
    return new Organization(name,new Coordinates(coor_x,coord_y),annualTurnover,fullName,employeesCount,organizationType,new Address(zip_cod,new Location(loc_x,loc_y,loc_z)),0, creation_date);
  }

  private String getName(String rawData) throws PartNotFoundException {
    Pattern nameReg = Pattern.compile("name=[^;]*;");
    Matcher matcher = nameReg.matcher(rawData);
    String name = "";
    while (matcher.find()) {
      name = rawData.substring(matcher.start() + 5, matcher.end() - 1);
    }
    if (name.isEmpty()) throw new PartNotFoundException("Name shouldn't be empty");
    name.replace("_"," ");
    return name;
  }
  private String getFullName(String rawData) {
    Pattern nameReg = Pattern.compile("fullName=[^;]*;");
    Matcher matcher = nameReg.matcher(rawData);
    while (matcher.find()) {
      return rawData.substring(matcher.start() + 9, matcher.end() - 1).replace("_"," ");
    }
    return "";
  }
  private OrganizationType getType(String rawData) throws PartNotFoundException {
    String rawType = "";
    Pattern nameReg = Pattern.compile("type=[^;]*;");
    Matcher matcher = nameReg.matcher(rawData);
    while (matcher.find()) {
      rawType = rawData.substring(matcher.start() + 5, matcher.end() - 1);
    }
    switch (rawType.toLowerCase()) {
      case "public":
        return OrganizationType.PUBLIC;
      case "trust":
        return OrganizationType.TRUST;
      case "open_join_stock_company":
        return OrganizationType.OPEN_JOINT_STOCK_COMPANY;
      case "private_limited_company":
        return OrganizationType.PRIVATE_LIMITED_COMPANY;
    }
    throw new PartNotFoundException("Type shouldn't be empty");
  }
  private int getEmploysCount(String rawData)  throws PartNotFoundException{
    Pattern nameReg = Pattern.compile("employs=[^;]*;");
    Matcher matcher = nameReg.matcher(rawData);
    int employees = 0;
    try {
      while (matcher.find()) {
        if (rawData.substring(matcher.start() + 8, matcher.end() - 1).isEmpty()) break;
        employees = Integer.valueOf(rawData.substring(matcher.start() + 8, matcher.end() - 1));
        break;
      }
    }catch (NumberFormatException ex) {
      throw new PartNotFoundException("Wrong format for Employees");
    }
    if (employees == 0) throw new PartNotFoundException("Employs number shouldn't be empty");
    if (employees < 0) throw new PartNotFoundException("Employs number should be positive");
    return employees;
  }
  private float getAnnualTurnover(String rawData)  throws PartNotFoundException{
    Pattern nameReg = Pattern.compile("annual=[^;]*[\\d.,]*;");
    Matcher matcher = nameReg.matcher(rawData);
    float annual = 0;
    try {
      while (matcher.find()) {
        if (rawData.substring(matcher.start() + 7, matcher.end() - 1).isEmpty()) break;
        annual = Float.valueOf(rawData.substring(matcher.start() + 7, matcher.end() - 1));
        break;
      }
    }catch (NumberFormatException ex) {
      throw new PartNotFoundException("Wrong format for Annual turnover - Use \".\" instead \",\"");
    }
    if (annual == 0) throw new PartNotFoundException("Annual turnover shouldn't be empty or 0");
    if (annual < 0) throw new PartNotFoundException("Annual turnover should be positive");
    return annual;
  }
  private LocalDateTime getCreationData(String rawData)  throws PartNotFoundException{
    Pattern nameReg = Pattern.compile("date=[^;]*[\\d,.'\'/]*;");
    Matcher matcher = nameReg.matcher(rawData);
    LocalDateTime localDateTime = null;
    String rawDate = "";
    while (matcher.find()) {
      if (rawData.substring(matcher.start() + 5, matcher.end() - 1).isEmpty()) break;
        rawDate = rawData.substring(matcher.start() + 5, matcher.end() - 1).subSequence(0,19).toString();
    }
    rawDate = rawDate.replace("T"," ");
    try {
      localDateTime = LocalDateTime.parse(rawDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
    if (localDateTime == null) throw new PartNotFoundException("Wrong data format");
    return localDateTime;
  }
  private int getCoordX(String rawData)  throws PartNotFoundException{
    Pattern nameReg = Pattern.compile("cordx=[^;]*[\\d,.]*;");
    Matcher matcher = nameReg.matcher(rawData);
    int x = 0;
    try {
      while (matcher.find()) {
        if (rawData.substring(matcher.start() + 6, matcher.end() - 1).isEmpty()) break;
        x = Integer.valueOf(rawData.substring(matcher.start() + 6, matcher.end() - 1));
        break;
      }
    }catch (NumberFormatException ex) {
      throw new PartNotFoundException("Wrong format for coordinate X - It should be integer.");
    }
    if (x == 0) throw new PartNotFoundException("Coordinate X shouldn't be empty");
    return x;
  }
  private Float getCoordY(String rawData)  throws PartNotFoundException{
    Pattern nameReg = Pattern.compile("cordy=[^;]*[\\d,.]*;");
    Matcher matcher = nameReg.matcher(rawData);
    Float y = null;
    try {
      while (matcher.find()) {
        if (rawData.substring(matcher.start() + 6, matcher.end() - 1).isEmpty()) break;
        y = Float.valueOf(rawData.substring(matcher.start() + 6, matcher.end() - 1));
        break;
      }
    }catch (NumberFormatException ex) {
      throw new PartNotFoundException("Wrong format for coordinate Y - Use \".\" instead \",\"");
    }
    if (y == null) throw new PartNotFoundException("Coordinate Y shouldn't be empty");
    if (y < -538) throw new PartNotFoundException("Coordinate Y shouldn be > -538");
    return y;
  }
  private String getzipCod(String rawData) {
    Pattern nameReg = Pattern.compile("zip=[^;]*;");
    Matcher matcher = nameReg.matcher(rawData);
    while (matcher.find()) {
      return rawData.substring(matcher.start() + 4, matcher.end() - 1).replace("_"," ");
    }
    return "";
  }
  private long getLocationX(String rawData)  throws PartNotFoundException{
    Pattern nameReg = Pattern.compile("locx=[^;]*[\\d]*;");
    Matcher matcher = nameReg.matcher(rawData);
    long x = 0;
    try {
      while (matcher.find()) {
        if (rawData.substring(matcher.start() + 5, matcher.end() - 1).isEmpty()) break;
        x = Long.valueOf(rawData.substring(matcher.start() + 5, matcher.end() - 1));
        break;
      }
    }catch (NumberFormatException ex) {
      throw new PartNotFoundException("Wrong format for location coordinate X - It should be integer.");
    }
    if (x == 0) throw new PartNotFoundException("Location coordinate X shouldn't be empty");
    return x;
  }
  private long getLocationY(String rawData)  throws PartNotFoundException{
    Pattern nameReg = Pattern.compile("locy=[^;]*[\\d]*;");
    Matcher matcher = nameReg.matcher(rawData);
    long y = 0;
    try {
      while (matcher.find()) {
        if (rawData.substring(matcher.start() + 5, matcher.end() - 1).isEmpty()) break;
        y = Long.valueOf(rawData.substring(matcher.start() + 5, matcher.end() - 1));
        break;
      }
    }catch (NumberFormatException ex) {
      throw new PartNotFoundException("Wrong format for location coordinate Y - It should be integer.");
    }
    if (y == 0) throw new PartNotFoundException("Location coordinate Y shouldn't be empty");
    return y;
  }
  private double getLocationZ(String rawData) throws PartNotFoundException{
    Pattern nameReg = Pattern.compile("locz=[^;]*[\\d,.]*;");
    Matcher matcher = nameReg.matcher(rawData);
    double z = 0;
    try {
      while (matcher.find()) {
        if (rawData.substring(matcher.start() + 5, matcher.end() - 1).isEmpty()) break;
        z = Double.valueOf(rawData.substring(matcher.start() + 5, matcher.end() - 1));
        break;
      }
    }catch (NumberFormatException ex) {
      throw new PartNotFoundException("Wrong format for location coordinate Z - Use \".\" instead \",\"");
    }
    if (z == 0) throw new PartNotFoundException("Location coordinate Z shouldn't be empty");
    return z;
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
