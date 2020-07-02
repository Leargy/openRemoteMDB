package czerkaloggers.receiver;

import czerkaloggers.HawkPDroid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import patterns.mediator.Component;
import patterns.mediator.Controllers;

/**
 * Угадайте: что за класс.
 * А это логгер для модуля
 * записи клиентов
 * @author Come_1LL_F00 aka Lenar Khannanov
 * @author Leargy aka Anton Sushkevich
 */
public final class S_0D3_GE3 extends HawkPDroid<Controllers> implements Component {

  private static final Logger log = LoggerFactory.getLogger(S_0D3_GE3.class);
  // builders

  /**
   * Конструктор для присваивания
   * отправителя логов
   * @param controller отправитель логов
   */
  public S_0D3_GE3(Controllers controller) { super(controller); }
  /**
   * Помимо логгирования, еще и составляет протокол действий.
   * @param errorCode код ошибки
   * @param message   отправляемое сообщение
   */
  @Override
  public void notify(Integer errorCode, String message) { logboard(errorCode, message); }

  /**
   * Выполняет логгирование всех действий.
   * @param errorCode код ошибки
   * @param message   передаваемое сообщение
   */
  @Override
  public void logboard(Integer errorCode, String message) {
    if (errorCode == 1)
      log.error("[{}] : {}", errorCode, message);
    else if (errorCode == 2)
      log.warn("[{}] : {}", errorCode, message);
    else log.info("[{}] : {}", errorCode, message);
  }
}
