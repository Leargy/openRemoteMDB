package parsing.customer.bootstrapper;

import extension_modules.dbinteraction.DataBaseConnector;
import extension_modules.dbinteraction.OrganizationsTableInteractor;
import extension_modules.dbinteraction.UsersTableInteractor;
import organization.*;

import javax.print.attribute.standard.MediaSize;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;


/**
 * Стандартный подгрузчик коллекции,
 * пытаемся следовать SRP, а также
 * меня задолбало, что у менеджера
 * дОфига кода, что читать мешает.
 * Буква Р - Реfuckторинг. Название - пародия на BaredBoxLoader
 * @author Leargy aka Anton Sushkevich
 * @author Come_1LL_F00 aka Lenar Khannanov
 * @see LoaferLoader
 */
public final class NakedCrateLoader implements LoaferLoader<OrganizationWithUId> {
  private static final String TEST_MODE = "DEBUG"; // маркер для определения подгрузки тестовой коллекции
  private static final String CAL_FOLDER = "storage"; // директория, хранящая все коллекции
  private String birthDay = ZonedDateTime.now().toString(); // дата создания загрузчика коллекции или файла с коллекцией
  private String environment = "DBPATH"; // название переменной окружения
  private boolean loaded = false; // признак того, что коллекция уже загружена
  public final DataBaseConnector DATABASE_CONTROLLER;

  public NakedCrateLoader(DataBaseConnector dataBaseConnector) {
    DATABASE_CONTROLLER = dataBaseConnector;
  }
  /**
   * Метод подгрузки коллекции из локального хранилища,
   * так, чтобы не бомбило сидалище.
   * Спойлер: бомбит
   * @return список элементов
   */

  public List<OrganizationWithUId> loadFromXML() {
    // получаем файловый разделитель нашей OS
    // запоминаем детки, что для Windows - это \, а для Линуха - /,
    // а MacOS - это дерьмо для геев и его даже знать не надо
    String sprt = System.getProperty("file.separator");
    // объявляем ящик для наших компаний за массивом
    // следующих try-catch'ей, чтобы его потом достать можно хотя бы было
    Organizations companies = null;
    // формируем путь до серверных коллекций,
    // возможно критическая зона, если запускать сервер
    // из жопы файловой системы
    String pathname = System.getProperty("user.dir") + sprt + CAL_FOLDER + sprt + sayMyFileName();
    // берем файловый дескриптор, по этому пути (без комментариев)
    File fuck = new File(pathname);
    // если размер меньше кибибайта, то это повод задуматься
    // Джонни, там подсунули чухню, откатывай до пустой коллекции
    if (fuck.length() <= 0) return (companies = NothingButVoid()).getCompanies();
    // Ооо, первый try поехал, а вместе с ней и кукуха
    try {
      if (!checkFile(fuck)) {
        // TODO: подергать не кота за причинное место, а логгер, который расскажет в чем ошибка
        // А пока подергаем стандартный поток ошибок
        System.err.println("Обнаружена ошибка синтаксиса в файле!\nКоллекция не была подгружена!");
        return (companies = NothingButVoid()).getCompanies();
      }
    } catch (IOException e) {
      // TODO: также, лучше дергать логгер
      System.err.println(e.getMessage()); // здесь все равно мало, что полезного выведется, но хотя бы что-то
    }
    // берем прокачанный File методом конвертером
    Path urineBrickedPath = fuck.toPath();
    // О-па, второй try, загружающий коллекцию
    // создаем входной поток в файл, а также раба, читающего его
    try (
            InputStream nvidickShield = new FileInputStream(fuck);
            InputStreamReader assWorm = new InputStreamReader(nvidickShield);
    ) {
      // мне, однажды, сказал одноклассник, что стринги - это аксессуар
      // поэтому атрибуты файла так называются; закидываем в трусики сведение о нашем файле
      BasicFileAttributes pantsu = Files.readAttributes(urineBrickedPath, BasicFileAttributes.class);
      // страшный выбор между датой создания загрузчика и датой создания файла
      birthDay = RUInvalid(pantsu.creationTime().toString())? birthDay : pantsu.creationTime().toString();
      JAXBContext cotex = null; // контекст еще какой-то нужно создавать, непонятно
      try {
        // брюки (всмысле null) превращаются
        cotex = JAXBContext.newInstance(Organizations.class); // а во оно че, формируем контекст исходя из коренного элемента
        // превращаются брюки - в библиотечный загрузчик коллекции
        Unmarshaller standartenfuhrer = cotex.createUnmarshaller(); // вот эта скотина будет парсить файл, но скорее чаще будет срать null'ами
        companies = (Organizations) standartenfuhrer.unmarshal(assWorm); // пытаемся распарсить файл силами библиотеки
      } catch (JAXBException e) {
        // хорошее исключение (одно за всех) - и сообщения интересные (нет, чаще это null'ы)
        // TODO: высрать этот null логгеру, пока срем по ГОСТУ
        System.err.println(e.getMessage());
        e.printStackTrace();
        System.err.println("Произошла критическая ошибка при загрузке с помощью этой\n" +
                "тупой библиотеки, которая не умеет предотвращать никакие ошибки");
        // здесь уже медицина бессильна, поэтому остается только уйти незаметно
        System.exit(0);
      }
    } catch (FileNotFoundException e) {
      // не нашли нужный файл, загрузим свой - не жадные
      System.err.println("Файл с заданным именем не найден. Будет загружена тестовая коллекция"); // TODO: также это все срет логгер, и лучше уведомить клиента
      return (companies = NothingButVoid()).getCompanies(); // держим курс на черную дыру
    } catch (IOException e) {
      // чувствительное исключение, выкинулось даже, когда права не выдал
      System.err.println("Произошла критическая ошибка в вашей файловой системе.\n" +
              "Запустите ее диагностику с помощью соотвествующей утилиты вашей ОС.");
      // здесь уже медицина бессильна, поэтому остается только уйти незаметно
      System.exit(0);
    }
    // по сусекам поскребли, по полками помели и, что нашли, то и вернули
    // TODO: желательно составить рапорт (Report) об успешности загрузки (дабы уведомить клиента) и подергать логгер, выше тоже проверить
    loaded = true;
    return companies.getCompanies();
  }

  @Override /*Method for loading collection from DB during preparing server*/
  public List<OrganizationWithUId> load(){
    List<OrganizationWithUId> companies = new ArrayList<>();
    try {
    if (countOrganizationsInDB() == 0) {
      return companies;
    }
    String name, fullName, zipCode, userLogin, type;
    int id, employsCount, coordinates_x, collectionKey;
    Long location_x, location_y;
    Double location_z;
    Float coordinates_y, annualTurnover;
    LocalDateTime creationDateTime;

    Connection currentConnection = DATABASE_CONTROLLER.retrieveCurrentConnection();
      PreparedStatement getTableFromDB = currentConnection.prepareStatement("SELECT * FROM " + OrganizationsTableInteractor.DB_TABLE_NAME + " ");
      ResultSet resultSet = getTableFromDB.executeQuery();
      while (resultSet.next()) {
        name = resultSet.getString("name");
        fullName = resultSet.getString("fullname");
        zipCode = resultSet.getString("zipcode").trim();
        userLogin = resultSet.getString("user_login");
        type = resultSet.getString("type");
        id = resultSet.getInt("id");
        employsCount = resultSet.getInt("employeescount");
        coordinates_x = resultSet.getInt("coordinates_x");
        collectionKey = resultSet.getInt("collection_key");
        location_x = resultSet.getLong("location_x");
        location_y = resultSet.getLong("location_y");
        location_z = resultSet.getDouble("location_z");
        coordinates_y = resultSet.getFloat("coordinates_y");
        annualTurnover = resultSet.getFloat("annualturnover");
        creationDateTime = LocalDateTime.parse(resultSet.getString("creationdate").subSequence(0,19), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        companies.add(new OrganizationWithUId(new Organization(name, new Coordinates(coordinates_x,coordinates_y),
                 annualTurnover, fullName, employsCount,makeOutType(type),
                new Address(zipCode,new Location(location_x, location_y, location_z)), id, creationDateTime),
                userLogin, collectionKey));
      }
    }catch (SQLException ex) {
      System.out.println(ex.getMessage()); //TODO:накинуть логгер если нада
      System.exit(-10);
    }

    return companies;
  }

  public int countOrganizationsInDB() throws SQLException {
    int result = 0;
    Connection currentConnection = DATABASE_CONTROLLER.retrieveCurrentConnection();
    PreparedStatement counting = currentConnection
            .prepareStatement("SELECT COUNT(*) AS rowcount FROM " + OrganizationsTableInteractor.DB_TABLE_NAME);
    ResultSet resultSet = counting.executeQuery();
    resultSet.next();
    result = resultSet.getInt("rowcount");
    return result;
  }

  private OrganizationType makeOutType(String typeInSting) {
    switch (typeInSting) {
      case "public": return OrganizationType.PUBLIC;
      case "trust": return  OrganizationType.TRUST;
      case "private-limited-company": return OrganizationType.PRIVATE_LIMITED_COMPANY;
      case "open-joint-stock-company": return OrganizationType.OPEN_JOINT_STOCK_COMPANY;
      default: return OrganizationType.PUBLIC;
    }
  }

  /**
   * Метод выгрузки коллекции в хранилище,
   * так, чтобы не бомбило сидалище.
   * Спойлер: бомбит
   * @param elements коллекция элементов
   */
  @Override
  public void unload(List<OrganizationWithUId> elements) throws NullPointerException {
    // снова для начала берем разделитель нашей OS
    String sptr = System.getProperty("file.separator");
    // также получаем имя файла, куда срать будем, и формируем путь до него
    String fuck = sayMyFileName();
    String pathname = System.getProperty("user.dir") + sptr + CAL_FOLDER + sptr + fuck;
    // начинается попа-боль
    // создаем выходной поток, и виновника, от души которого все блага отпускаем
    try (OutputStream otputty = new FileOutputStream(pathname);
         OutputStreamWriter perpetrator = new OutputStreamWriter(otputty)
    ) {
      // опять с этими контекстами парится, опять этот ТеХ чудится
      JAXBContext cotex = JAXBContext.newInstance(Organizations.class);
      // маршалл рейхсфюрер готов отправлять null'ы в печь
      Marshaller reichsfuhrer = cotex.createMarshaller();
      // естественно, прожарка должна производится по ГОСТУ
      reichsfuhrer.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
      // посрали в файл
      reichsfuhrer.marshal(new Organizations(elements), perpetrator);
    } catch (FileNotFoundException e) {
      // фрайер файлик не нашел, значит фрайер - дурачок;
      // все вопросы к администратору, что не выделил файл под клиента
      // TODO: выпад FNF-исключения дает Вам право на две шкатулки - в одной уведомление клиенту, в другой - одинокий логгер
      System.err.println("Файл, не найден, проверьте: выделил ли ваш хозяин вам место в хранилище сервера");
    } catch (IOException e) {
      // TODO: ошибка на сервере, потому сектор "логгер" на барабане
      System.err.println("Произошла критическая ошибка в вашей ФС: проверьте ее с помощью утилит или посмотрите имеете ли вы полный к ней доступ.");
      // я сказал: приложение вырубай, здесь запуск серверов запрещен
      System.exit(0);
    } catch (JAXBException e) {
      // наше любимое исключение, что готово заменить все имеющиеся
      System.err.println("Произошла критическая ошибка при попытке сохранить коллекцию\n" +
              "с помощью этой дурацкой библиотеки");
      System.exit(0); // выйди отсюда, розбийник
    }
    // TODO: левой рукой клиента тереблю, правой - логгер щекочу
    // уведомить о suckцессе
  }

  /**
   * Проверка на придурка-дурака,
   * который не дал переменной окружения или
   * не присвоил ей какого-либо значения
   * @param line проверяемая строка
   * @return true - дурак, false - молодец
   */
  private boolean RUInvalid(String line) { return (line == null || line.isEmpty())? true : false; }

  /**
   * Простейший сеттер для переменной окружения,
   * идут проверки на то, что пользователь инвалид
   * и не смог ее (переменную) создать и
   * как-то обозначить и тогда загружаем заготовленный файл,
   * дабы не прерывать сервер на такие глупости.
   * @param environment название переменной окружения
   */
  private String CheckEnvironment(String environment) { return (RUInvalid(environment) || RUInvalid(System.getenv(environment)))? TEST_MODE : environment; }

  /**
   * Такой же простой геттер для получения
   * имени файла из переменной: возвращает
   * название файла с тестовой коллекцией, если пользовательская не была найдена
   * @return название файла с коллекцией
   */
  private String sayMyFileName() {
    try {
      if (TEST_MODE.equals(environment)) {
        // TODO: логирование, уведомление что работаем с тестовой коллекцией
        System.err.println("Вниманиме! Так как Ваша коллекция не была найдена, то будет загружена заготовленная тестовая коллекция:");
//      System.err.println("Usage: java -jar [jarfile] [varenv]");
//      System.err.println("[jarfile] - the path or just name for server jar executable file;");
//      System.err.println("[varenv] - the name of environment variable within relative path to XML collection file (like DBPATH or etc.)");
        return "tutor.xml";
      } else return System.getenv(environment);
    }catch (NullPointerException ex) {
      return "";
    }
  }

  /**
   * Возвращает пустую коллекцию и переходит на тестовую коллекцию.
   * Этот мир прогнил и не осталось ничего, кроме страданий
   * @return компании в общем
   */
  private Organizations NothingButVoid() {
    environment = TEST_MODE; // мы не хотим расстраивать пользователя
    return new Organizations(new ArrayList<>()); // поэтому даем ему поиграться с пустотой
  }

  /**
   * Метод выполняющий проверку на правильное заполнение синтаксиса xml-файла или его наличие.
   * Если не будут заполнены обязательные поля, метод заполняет их значениями по умолчанию.
   * @param foil я задолбался опечатываться, так что оставлю с таким названием, так как смешнее
   * @return true/false
   * @throws IOException типичное исключение системы ввода/выводы, которое, как неприступная дева, никогда ничего о себе не расскажет
   */
  @Override
  public boolean checkFile(File foil) throws IOException {
    FileReader reader = new FileReader(foil);
    Scanner scanner = new Scanner(reader);
    String tempString;
    tempString = scanner.nextLine();
    StringBuilder stringBuilder = new StringBuilder();
    String xmlBasement = tempString;
    Pattern base = Pattern.compile("<\\?xml version=\".*\" encoding=\".*\".*\\?>");
    if(base.matcher(tempString).find()) {
      while (scanner.hasNextLine()) {
        tempString = scanner.nextLine();
        stringBuilder.append(tempString);
      }
    }else {
      return false;
    }
    base = Pattern.compile("<organizations>.*</organizations>");
    if (!base.matcher(stringBuilder).find()){
      return false;
    }
    base = Pattern.compile("user-name=\"\"");
//    if (!base.matcher(stringBuilder).find()) {
//      System.err.println("unidentified organization");
//      return false;
//    }
    String[] dictinary = new String[]{"id=\"\"","name=\"\""};
    String[] degradedXML = stringBuilder.toString().split(" ");
    stringBuilder = new StringBuilder();
    for(int i = 0; i<dictinary.length; i++){
      for(int j = 0; j < degradedXML.length; j++ ) {
        base = Pattern.compile(dictinary[i]);
        if (base.matcher(degradedXML[j]).find()) {
          String replacemant;
          if (dictinary[i].substring(0, dictinary[i].length() - 3).equals("id")) {
            replacemant = "id=\"9696" + j * 3+"\"";
          } else {
            replacemant = "name=\"unknown" +j/2+"\"";
          }
          System.out.printf("Replacing empty values: %s , to defolt: %s! ", dictinary[i],replacemant);
          System.out.println();
          degradedXML[j] = replacemant;
        }
      }
    }
    String string ;
    stringBuilder.append("\n");
    for (int i = 0; i< degradedXML.length; i++) {
      if(degradedXML[i].equals("")) {
        string = " ";
      }else if(!degradedXML[i].contains(">")) {
        string = String.format("%s%s", degradedXML[i], " ");
      }else string = String.format("%s%n%s",degradedXML[i], " ");
      stringBuilder.append(string);
    }

    FileWriter fileWriter = new FileWriter(foil.getAbsolutePath());
    fileWriter.append(xmlBasement.concat(stringBuilder.toString()));
    fileWriter.close();
    return true;
  }

  /**
   * Свойство записи названия
   * переменной окружения в поле
   * загрузчика. Также проводим
   * при записи проверку, чтобы при ЧС
   * могли что-то хорошее загрузить
   * @param varName название переменной окружения
   */
  @Override
  public void Environment(String varName) {
    environment = CheckEnvironment(varName);
  }

  /**
   * Свойство получения,
   * признака того, что коллекция
   * уже подгружена
   * @return признак загрузки коллекции
   */
  @Override
  public boolean Loaded() { return loaded; }

  /**
   * Свойство, дающие заглянуть
   * лишь в одно из свойств файла, с которым
   * нам пришлось работать
   * @return строковое представение даты
   */
  @Override
  public String Birth() {
    return birthDay;
  }
}
