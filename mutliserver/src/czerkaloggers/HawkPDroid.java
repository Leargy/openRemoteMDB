package czerkaloggers;


import patterns.mediator.Component;
import patterns.mediator.Controllers;

/**
 * Логгер, умеющий работать с контроллером
 * исполнения запросов.
 * @author Come_1LL_F00 aka Lenar Khannanov
 * @author Leargy aka Anton Sushkevich
 */
public abstract class HawkPDroid<T extends Controllers> implements RadioLogger, Component {
  protected final T MAGIV; // ссылка на SSPC
  // SubSystem Process Controller

  /**
   * Конструктор, устанавливающий
   * отправителя сообщений для логирования
   * @param controller отправитель сообщений
   */
  protected HawkPDroid(T controller) { MAGIV = controller; }

  /**
   * Фабричный метод сборки
   * экземпляра логгера
   * @param controller отправитель сообщений
   * @param plant хранитель протокола сборки
   * @return готовый логгер
   */
  public static HawkPDroid<? extends Controllers> assemble(Controllers controller, DroidFactory plant) {
    return plant.create(controller);
  }
}
