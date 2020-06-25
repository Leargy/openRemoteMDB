package parsing.pickers;

import base_modules.processors.processing_tasks.AuthenticationTask;
import entities.User;
import extension_modules.dbinteraction.OrganizationsTableInteractor;
import extension_modules.dbinteraction.TablesInteractor;
import extension_modules.dbinteraction.UsersTableInteractor;
import instructions.concrete.ConcreteDecree;
import instructions.concrete.base.*;
import instructions.concrete.base.DBbaseCommands.*;
import instructions.concrete.extended.FilterContainsName;
import instructions.concrete.extended.MaxByDate;
import instructions.concrete.extended.SumOfAnnualTurnover;
import instructions.rotten.IClued;
import instructions.rotten.ITitled;
import instructions.rotten.RawDecree;
import instructions.rotten.base.*;
import instructions.rotten.extended.RawMaxByDate;
import instructions.rotten.extended.RawSumOfAnnualTurnover;
import parsing.customer.distro.LimboKeeper;

import java.nio.channels.SocketChannel;

/**
 * Фабрика по созданию "конкретных" комманд без аргументов.
 *  @author Come_1LL_F00 aka Lenar Khannanov
 *  @author Leargy aka Anton Sushkevich
 */
public final class JustCommandBuilder {
  private AuthenticationTask AUTHENTICATION_TASK = null;
  public final UsersTableInteractor USER_TABLE_INTERACTOR = new UsersTableInteractor();
  /**
   * Метод по формированию "конкретных" команд без аргументов.
   * @param c RawDecree
   * @param receiver LimboKeeper
   * @return ConDecree
   */
  public ConcreteDecree make(RawDecree c, LimboKeeper receiver, SocketChannel tempSocketChannel, TablesInteractor organizationTablesInteractor, User user) {
    if (c instanceof ITitled) {
      String name = ((ITitled) c).Name();
      return new FilterContainsName(receiver, name);
    } else if (c instanceof IClued) {
      Integer key = ((IClued) c).Key();
      return new DBRemoveKey(receiver, key, (OrganizationsTableInteractor) organizationTablesInteractor, user);
    } else {
        if (c instanceof RawSignUp) {
        SignUp signUp = new SignUp(receiver,USER_TABLE_INTERACTOR,AUTHENTICATION_TASK);
        signUp.setTempUserParametrs(((RawSignUp)c).getLogin(), ((RawSignUp)c).getPassword(),tempSocketChannel);
        return signUp;
      }
      else if (c instanceof RawSignIn) {
        SignIn signIn = new SignIn(receiver,USER_TABLE_INTERACTOR,AUTHENTICATION_TASK);
        signIn.setTempUserParametrs(((RawSignIn)c).getLogin(), ((RawSignIn)c).getPassword(),tempSocketChannel);
        return signIn;
      }
      else if (c instanceof RawSignOut) {
        SignOut signOut = new SignOut(receiver,AUTHENTICATION_TASK);
        signOut.setTempUserParametrs(tempSocketChannel);
        return signOut;
      }
      else if (c instanceof RawNotAuthorizedHelp) return new NotAuthorizedHelp(receiver);
      else if (c instanceof RawClear) return new DBClear(receiver,organizationTablesInteractor,user);
      else if (c instanceof RawInfo) return new Info(receiver);
//      else if (c instanceof RawSave) return new Save(receiver);
      else if (c instanceof RawShow) return new Show(receiver);
      else if (c instanceof RawMaxByDate) return new MaxByDate(receiver);
      else if (c instanceof RawSumOfAnnualTurnover) return new SumOfAnnualTurnover(receiver); //TODO: поработать над командами
      else if (c instanceof RawExit) return new Exit(receiver);
      else return new Help(receiver);
    }
  }
  public void setLinkToAuthenticationTask(AuthenticationTask authenticationTask) {
    if (AUTHENTICATION_TASK == null) {
      AUTHENTICATION_TASK = authenticationTask;
    }
  }
}
