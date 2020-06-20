package parsing.pickers;

import entities.Organization;
import entities.OrganizationWithUId;
import entities.User;
import extension_modules.dbinteraction.OrganizationsTableInteractor;
import instructions.concrete.ConcreteDecree;
import instructions.concrete.base.DBbaseCommands.DBInsert;
import instructions.concrete.base.Help;
import instructions.concrete.base.Insert;
import instructions.concrete.base.Update;
import instructions.concrete.extended.RemoveLower;
import instructions.concrete.extended.ReplaceIfGreater;
import instructions.concrete.extended.ReplaceIfLower;
import instructions.rotten.IClued;
import instructions.rotten.RawCommitter;
import instructions.rotten.base.RawInsert;
import instructions.rotten.base.RawUpdate;
import instructions.rotten.extended.RawRemoveLower;
import instructions.rotten.extended.RawReplaceIfGreater;
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
    OrganizationWithUId organizationWithUId = new OrganizationWithUId(c.getOrganization(),user.getLogin());
    if (c instanceof IClued) {
      Integer p = ((IClued) c).Key();
      if (c instanceof RawInsert)
          return new DBInsert(r,p,organizationWithUId,organizationsTableInteractor,user);
//        return new Insert(r, p,);
      else if (c instanceof RawUpdate)
          return new Update(r, p, organizationWithUId);
//        return new Update(r, p,organization);
      else if (c instanceof RawReplaceIfGreater)
          return new ReplaceIfGreater();
//          return new ReplaceIfGreater(r, p, organization );
      else return new ReplaceIfLower(r, p, organization);
    } else if (c instanceof RawRemoveLower) return new RemoveLower(r, organization);
    else return new Help(r);
  }
}
