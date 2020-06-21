package czerkaloggers.perusal;

import communication_tools.Component;
import communication_tools.Mediator;
import communication_tools.Report;
import communication_tools.wrappers.AlertBag;
import czerkaloggers.HawkPDroid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import perusal.QueryReader;

import java.nio.channels.SocketChannel;

/**
 * Экземпляр логгера, как не странно,
 * создан логировать сообщения от
 * модуля чтения
 * @author Come_1LL_F00 aka Lenar Khannanov
 * @author Leargy aka Anton Sushkevich
 */
public final class C9_T9_GE3 extends HawkPDroid<QueryReader> implements Component {

  private static final Logger log = LoggerFactory.getLogger(C9_T9_GE3.class);

  /**
   * Конструктор, принимающий
   * отправителя сообщений для логирования
   * @param controller отправитель логов
   */
  public C9_T9_GE3(Mediator controller) { super((QueryReader) controller); }

  /**
   * Помимо логгирования, еще и составляет протокол действий.
   * @param errorCode код ошибки
   * @param message   отправляемое сообщение
   */
  @Override
  public void notify(Integer errorCode, String message) {
    logboard(errorCode, message);
    SocketChannel client = MAGIV.ClientChannel();
    AlertBag alert = new AlertBag(client, new Report(errorCode, message));
    MAGIV.notify(this, alert);
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
