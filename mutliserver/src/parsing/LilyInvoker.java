package parsing;

import communication.Report;
import instructions.concrete.ConcreteDecree;
import instructions.concrete.base.Save;
import instructions.concrete.extended.ExecuteScript;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import patterns.mediator.Controllers;
import uplink_bags.ExecuteBag;
import uplink_bags.NotifyBag;

import javax.print.attribute.standard.NumberUp;

/**
 * Эмулятор клиента, что вызывает приходящие
 * от него команды и вызывает их. Пародия на LilyTerm
 * @author Come_1LL_F00 aka Lenar Khannanov
 * @author Leargy aka Anton Sushkevich
 */
public class LilyInvoker extends FondleEmulator {
  private Report collectorReports = new Report(0,"");
  protected final Logger logger = LoggerFactory.getLogger(this.getClass());

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
    logger.info("Executing clients command " + concreteCommand);
    Report result = concreteCommand.execute();
//    if (concmd instanceof ExecuteScript) {
//      result = shell.read(cmd);
//    } else {
//      result = concmd.execute();
//    }
    Report respond = new Report(0, "Команда " + concreteCommand.toString() + " выполнена с результатом:\n\t" + result.Message());
    if (result.getIsConfirmed() != null){
      if (result.getIsConfirmed()) respond.setIsConfirmed(true);
      else respond.setIsConfirmed(false);
    }
    logger.info("Reply is ready to be sent to client");
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
