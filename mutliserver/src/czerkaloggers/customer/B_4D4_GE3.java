package czerkaloggers.customer;

import czerkaloggers.HawkPDroid;
import czerkaloggers.RadioLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import patterns.mediator.Component;
import patterns.mediator.Controllers;

/**
 * Вот, самое сложное похоже это придумать название всему,
 * что есть в программе. Поэтому не нужно ругаться на название
 * этого класса. Знающие люди сразу обнаружать здесь забавную отсылку.
 * И тогда все встанет на свои места.
 * @author Come_1LL_F00 aka Lenar Khannanov
 * @author Leargy aka Anton Sushkevich
 * @see HawkPDroid
 * @see RadioLogger
 */
public final class B_4D4_GE3 extends HawkPDroid<Controllers> implements Component {

  private static final Logger log = LoggerFactory.getLogger(B_4D4_GE3.class);

  // builders
  /**
   * Конструктор, задающий
   * отправителя логов
   * @param controller отправитель логов
   */
  public B_4D4_GE3(Controllers controller) { super(controller); }

  /**
   * Помимо логгирования, еще и
   * составляет протокол действий.
   * @param errorCode код ошибки
   * @param message передаваемое сообщение
   */
  @Override
  public void notify(Integer errorCode, String message) {
    logboard(errorCode, message);
//    SocketChannel client = MAGIV.ClientChannel();
//    AlertBag alert = new AlertBag(client, new Report(errorCode, message));
//    MAGIV.notify(this, alert);
  }

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
