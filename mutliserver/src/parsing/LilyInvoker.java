package parsing;

import communication.Report;
import communication.wrappers.AlertBag;
import instructions.concrete.ConDecree;
import instructions.concrete.ConcreteDecree;
import instructions.concrete.base.Save;
import instructions.concrete.extended.ExecuteScript;
import patterns.mediator.Controllers;
import uplink_bags.ExecuteBag;
import uplink_bags.NotifyBag;

/**
 * Эмулятор клиента, что вызывает приходящие
 * от него команды и вызывает их. Пародия на LilyTerm
 * @author Come_1LL_F00 aka Lenar Khannanov
 * @author Leargy aka Anton Sushkevich
 */
public class LilyInvoker extends FondleEmulator {
  private Report collectorReports = new Report(0,"");

  /**
   * Конструктор, устанавливающий
   * контроллер с которого приходят команды
   * @param controller ссылка на SSPC
   */
  public LilyInvoker(Controllers controller) {
    super(controller);
  }

  /**
   * Метод записи комманды в
   * список обслуживаемых комманд
   * @param command название команды
   */
  @Override
  public void signup(ConcreteDecree command) { availableCommands.put(command.toString(), command); }

  /**
   * Метод вызова команды
   */
  @Override
  public void invoke(ExecuteBag executeBag) {
    ConcreteDecree concreteCommand = executeBag.getConcreteDecree();
    System.out.println(Thread.currentThread().getName() + " my command is " + concreteCommand.toString());
    Report result = concreteCommand.execute();
//    if (concmd instanceof ExecuteScript) {
//      result = shell.read(cmd);
//    } else {
//      result = concmd.execute();
//    }
    Report respond = new Report(0, "Команда " + concreteCommand.toString() + " выполнена с результатом:\n\t" + result.Message());
    if (result.getIsConfirmed()){
      respond.setIsConfirmed(true);
    }
//    respond.setIsConfirmed(result.getIsConfirmed());
//    if (concmd instanceof ExecuteScript) {
//      respond = new Report(0,respond.Message() + collectorReports.Message());
//      collectorReports = new Report(0,"");
    MAGIV.notify(this, new NotifyBag(executeBag.Channel(), respond));
//      return;
//    }
//    collectorReports = new Report(0,collectorReports.Message() + respond.Message()+"\n");
//    if (!(concreteCommand instanceof Save) && executeBag.Channel() != null) {
//      collectorReports = new Report(0,"");
//      MAGIV.notify(this, new AlertBag(executeBag.Channel(), respond));
//    }
  }
}
