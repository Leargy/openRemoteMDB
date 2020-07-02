package czerkaloggers;


import patterns.mediator.Controllers;

/**
 * Фабрика дроидов? Нет, ****
 * фабрика логгеров
 * @author Come_1LL_F00 aka Lenar Khannanov
 * @author Leargy aka Anton Sushkevich
 */
public interface DroidFactory {
  /**
   * Фабричный метод создания логгеров
   * @param controller отправитель логгеров
   * @return экземпляр логгера
   */
  HawkPDroid<? extends Controllers> create(Controllers controller);
}
