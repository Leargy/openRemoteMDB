package parsing.customer.local;

import czerkaloggers.RadioLogger;
import entities.HashMax;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import organization.Mappable;
import parsing.customer.bootstrapper.LoaferLoader;
import patterns.command.Receiver;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Управленец коллекцией только уже с данными
 * @author Leargy aka Anton Sushkevich
 * @author Come_1LL_F00 aka Lenar Khannanov
 * @param <K> тип ключа коллекции
 * @param <V> тип элемента коллекции
 * @see Receiver
 * @see LoaferLoader
 */
public abstract class Commander<K, V extends Mappable<K>> implements Receiver<K, V> {
  protected final Map<K, V> database = new HashMax<>(); // хранимое отображение
  protected String creationDate = LocalDateTime.now().toString(); // дата создания
  protected final LoaferLoader<V> breadLoader; // буханка-загрузчик xml-коллекций
//  protected final RadioLogger whistleblower; // ссылка на логгер
  protected final Logger logger = LoggerFactory.getLogger(this.getClass());
  /**
   * Конструктор, принимающий
   * загрузчик коллекции и элемент,
   * ведущий летопись
   * @param logger логирующий элемент
   */
//  public Commander(LoaferLoader<V> loader, RadioLogger logger) {
//    breadLoader = loader;
//    whistleblower = logger;
//  }
  public Commander(LoaferLoader<V> loader) {
    breadLoader = loader;
  }

  /**
   * Метод для получения отображения из ключей
   * исходной базы и значениями полей элементов данный базы.
   * Заметно должно упрощать реализацию комманд по типу
   * поиска максимального/минимального поля. Суммы по
   * различным полям, и сравнения этих полей.
   * @param keyExtractor передаваемый геттер
   */
  @Override
  public <R> Map<K, R> getBy(Function<V, R> keyExtractor) {
    // буферная коллекция - результат
    Map<K, R> buffer = new HashMap<>();
    // заполнение этой this very буферной коллекции
    database
        .entrySet() // формируем множество из пар ключ-значений
        .stream() // создание стрима из этого
        .forEach((Map.Entry<K, V> enter)->
        {
          // проходимся по каждому вхождению и кладем в коллекцию
          buffer.put(enter.getKey(), keyExtractor.apply(enter.getValue()));
        });
    // вернуть эту коллекцию
    return buffer;
  }
}
