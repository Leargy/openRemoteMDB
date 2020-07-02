package parsing.pickers;

import organization.OrganizationWithUId;
import entities.User;
import extension_modules.dbinteraction.OrganizationsTableInteractor;
import instructions.concrete.ConcreteDecree;
import instructions.concrete.base.DBbaseCommands.*;
import instructions.concrete.base.Help;
import instructions.rotten.IClued;
import instructions.rotten.RawCommitter;
import instructions.rotten.base.RawInsert;
import instructions.rotten.base.RawUpdate;
import instructions.rotten.extended.RawRemoveLower;
import instructions.rotten.extended.RawReplaceIfGreater;
import instructions.rotten.extended.RawReplaceIfLower;
import parsing.customer.distro.LimboKeeper;

/**
 * Фабрика по созданию "конкретных" комманд, манипулирующих объектом организации.
 *  @author Come_1LL_F00 aka Lenar Khannanov
 *  @author Leargy aka Anton Sushkevich
 */
public final class CommittersBuilder {
  /**
   * Метод формирования "конкретной" команды.
   * @param c RawCommitter
   * @param r LimboKeeper
   * @param f Factory of Organization
   * @return ConDecree
   */
  public final ConcreteDecree make(RawCommitter c, LimboKeeper r, OrganizationsTableInteractor organizationsTableInteractor, User user) {
      OrganizationWithUId organizationWithUId;
      if (c instanceof IClued) {
          Integer p = ((IClued) c).Key();
          organizationWithUId = new OrganizationWithUId(c.getOrganization(), user.getLogin(), p);
          if (c instanceof RawInsert)
              return new DBInsert(r, p, organizationWithUId, organizationsTableInteractor);
//        return new Insert(r, p, organizationWithUId);
          else if (c instanceof RawUpdate)
              return new DBUpdate(r, p, organizationWithUId, organizationsTableInteractor, user);
//          return new Update(r, p, organizationWithUId);
          else if (c instanceof RawReplaceIfGreater)
              return new DBReplaceIfGreater(r, p, organizationWithUId, organizationsTableInteractor, user);
//          return new ReplaceIfGreater(r, p, organizationWithUId);
          else if (c instanceof RawReplaceIfLower)
              return new DBReplaceIfLower(r, p, organizationWithUId, organizationsTableInteractor, user);
          else if (c instanceof RawRemoveLower)
              return new DBRemoveLower(r, organizationWithUId, organizationsTableInteractor, user);
//          return new RemoveLower(r, organizationWithUId);
      }
      else if (c instanceof RawRemoveLower) {
          organizationWithUId = new OrganizationWithUId(c.getOrganization(), user.getLogin(), 999999999);
          return new DBRemoveLower(r,organizationWithUId,organizationsTableInteractor,user);
      }
      return new Help(r);
  }
}
