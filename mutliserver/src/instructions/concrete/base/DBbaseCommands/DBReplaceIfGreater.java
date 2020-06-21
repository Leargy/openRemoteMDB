package instructions.concrete.base.DBbaseCommands;

import communication.Report;
import entities.OrganizationWithUId;
import entities.User;
import extension_modules.dbinteraction.OrganizationsTableInteractor;
import instructions.concrete.extended.ReplaceIfGreater;
import parsing.customer.distro.LimboKeeper;

import java.sql.SQLException;

public class DBReplaceIfGreater extends ReplaceIfGreater {
    public final OrganizationsTableInteractor ORGANIZATION_TABLE_INTERACTOR;
    public final User TEMP_USER;

    public DBReplaceIfGreater(LimboKeeper r, Integer p, OrganizationWithUId o, OrganizationsTableInteractor organizationsTableInteractor, User tempUser) {
        super(r, p, o);
        ORGANIZATION_TABLE_INTERACTOR = organizationsTableInteractor;
        TEMP_USER = tempUser;
    }

    @Override
    public Report execute() {
        Report dbReport = null;
        try {
            dbReport = ORGANIZATION_TABLE_INTERACTOR.replaceUserOrganizationIfGreater(TEMP_USER, super.KEY, super.EMBEDDED);
        } catch (SQLException ex) {
            return new Report(12, "Failed to remove greater organization!\n" + dbReport);
        }
        return super.execute();
    }

}
