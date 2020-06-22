package parsing;

import base_modules.processors.processing_tasks.AuthenticationTask;
import entities.OrganizationWithUId;
import extension_modules.dbinteraction.OrganizationsTableInteractor;
import instructions.rotten.IJunked;
import instructions.rotten.RawCommitter;
import instructions.rotten.RawDecree;
import parsing.customer.distro.LimboKeeper;
import parsing.pickers.CommittersBuilder;
import parsing.pickers.JustCommandBuilder;
import patterns.mediator.Component;
import patterns.mediator.Controllers;
import uplink_bags.ExecuteBag;
import uplink_bags.RawDecreeBag;
import uplink_bags.TransportableBag;

/**
 * Псевдо-фабрика сборки из абстрактных комманд
 * команд конкретных с ссылками на обработчик коллекции
 * и уже конкретный экземпляр элемента этой коллекции
 * @author Come_1LL_F00 aka Lenar Khannanov
 * @author Leargy aka Anton Sushkevich
 * @see Component
 * @see Factory
 */
public final class InstructionBuilder implements Component {
  public final Controllers SUB_PROCESS_CONTROLLER; // ссылка на SSPC
//  protected final Factory<Organization> MARDUK; // ссылка на производителя элементов
  public final CommittersBuilder COMMITERS_BUILDER = new CommittersBuilder();
  public final JustCommandBuilder COMMON_COMMAND_BUILDER = new JustCommandBuilder();
  public final OrganizationsTableInteractor organizationsTableInteractor = new OrganizationsTableInteractor();
  /**
   * Конструктор устанавливающий
   * ссылку на контроллер и фабрику,
   * формирующую элементы коллекции
   * @param controller контроллер
   */
  public InstructionBuilder(Controllers controller, AuthenticationTask authenticationTask) {
    SUB_PROCESS_CONTROLLER = controller;
    COMMON_COMMAND_BUILDER.setLinkToAuthenticationTask(authenticationTask);
//    MARDUK = facility;
  }

  // метод формирования из отправленной команды
  // исполняемой команды
  /**
   * Преобразуем абстрактную команду,
   * присланную от пользователя в
   * конкретную команду с ссылкой на
   * текущий обработчик.
   * В ходе преобразования устанавливается
   * ссылка на обработчик, из параметров
   * добавляемого или сравниваемого объекта
   * формируется конкретный объект
   * @param flesh абстрактная команда
   * @param receiver обработчик коллекции
   */
  public void make(TransportableBag rawDecreeBag, LimboKeeper receiver) {
    RawDecree command = ((RawDecreeBag)rawDecreeBag).UNINVOKABLE_COMMAND;
    if (command instanceof IJunked)
    SUB_PROCESS_CONTROLLER.notify(this, new ExecuteBag(((RawDecreeBag) rawDecreeBag).getChannel(), COMMITERS_BUILDER.make((RawCommitter) command, receiver,organizationsTableInteractor,((RawDecreeBag)rawDecreeBag).USER)));
    else SUB_PROCESS_CONTROLLER.notify(this, new ExecuteBag(((RawDecreeBag) rawDecreeBag).getChannel(), COMMON_COMMAND_BUILDER.make(command, receiver,((RawDecreeBag)rawDecreeBag).getChannel(),organizationsTableInteractor,((RawDecreeBag)rawDecreeBag).USER)));
  }
}
