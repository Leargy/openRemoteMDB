package czerkaloggers.perusal;

import communication.Report;
import czerkaloggers.HawkPDroid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import patterns.mediator.Component;
import patterns.mediator.Controllers;

import java.nio.channels.SocketChannel;

/**
 * Экземпляр логгера, как не странно,
 * создан логировать сообщения от
 * модуля чтения
 * @author Come_1LL_F00 aka Lenar Khannanov
 * @author Leargy aka Anton Sushkevich
 */
public final class C9_T9_GE3 extends HawkPDroid<Controllers> implements Component {

  private static final Logger log = LoggerFactory.getLogger(C9_T9_GE3.class);

  /**
   * Конструктор, принимающий
   * отправителя сообщений для логирования
   * @param controller отправитель логов
   */
  public C9_T9_GE3(Controllers controller) { super(controller); }

  /**
   * Помимо логгирования, еще и составляет протокол действий.
   * @param errorCode код ошибки
   * @param message   отправляемое сообщение
   */
  @Override
  public void notify(Integer errorCode, String message) {//TODO: написать реализацию
    logboard(errorCode, message);
//    SocketChannel client = MAGIV.ClientChannel();
//    AlertBag alert = new AlertBag(client, new Report(errorCode, message));
//    MAGIV.notify(this, alert);
  }
  /**
   * Выполняет логгирование всех действий
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
