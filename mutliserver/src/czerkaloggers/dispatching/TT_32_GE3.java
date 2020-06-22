package czerkaloggers.dispatching;

import base_modules.dispatchers.DispatchController;
import czerkaloggers.HawkPDroid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import patterns.mediator.Component;
import patterns.mediator.Controllers;

/**
 * Сущность, логирующая сообщения,
 * присылаемые от контроллера
 * @author Come_1LL_F00 aka Lenar Khannanov
 * @author Leargy aka Anton Sushkevich
 */
public final class TT_32_GE3 extends HawkPDroid<DispatchController> implements Component {

  private static final Logger log = LoggerFactory.getLogger(TT_32_GE3.class);

  /**
   * Конструктор, установки
   * отправителя сообщения для
   * логов
   * @param controller отправитель логов
   */
  public TT_32_GE3(Controllers controller) { super((DispatchController) controller); }


  /**
   * Помимо логгирования, еще и
   * составляет протокол действий.
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
