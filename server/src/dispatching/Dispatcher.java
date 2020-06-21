package dispatching;


import communication_tools.Component;
import communication_tools.Mediator;
import communication_tools.wrappers.AlertBag;
import systemcore.ServerController;

/**
 * Шаблон модуля отправки результатов клиенту
 * @author Come_1LL_F00 aka Lenar Khannanov
 * @author Leargy aka Anton Sushkevich
 * @see Mediator
 * @see Component
 */
public abstract class Dispatcher implements Component, Mediator {
  protected final ServerController KAPELLMEISTER; // ссылка на SSPC

  /**
   * Сигнатура метода отправки
   * посылок клиенту
   * @param postcard данные о предстоящих отправлениях
   */
  public abstract void send(AlertBag postcard);

  /**
   * Просто конструктор, инициализирующий
   * модуль его контроллером
   * @param m
   */
  public Dispatcher(ServerController m) {
    KAPELLMEISTER = m;
  }
}
