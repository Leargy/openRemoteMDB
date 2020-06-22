package instructions.concrete.base.DBbaseCommands;

import communication.Report;
import organization.OrganizationWithUId;
import entities.User;
import extension_modules.dbinteraction.OrganizationsTableInteractor;
import instructions.concrete.extended.ReplaceIfLower;
import parsing.customer.distro.LimboKeeper;

import java.sql.SQLException;

public class DBReplaceIfLower extends ReplaceIfLower {
    public final OrganizationsTableInteractor ORGANIZATION_TABLE_INTERACTOR;
    public final User TEMP_USER;


    public DBReplaceIfLower(LimboKeeper r, Integer p, OrganizationWithUId o, OrganizationsTableInteractor organizationsTableInteractor, User tempUser) {
        super(r, p, o);
        ORGANIZATION_TABLE_INTERACTOR = organizationsTableInteractor;
        TEMP_USER = tempUser;
    }

    @Override
    public Report execute() {
        Report dbReport = null;
        try {
            dbReport = ORGANIZATION_TABLE_INTERACTOR.replaceUserOrganizationIfLower(TEMP_USER, super.KEY, super.EMBEDDED);
        } catch (SQLException ex) {
            return new Report(12, "Failed to replace organization!\n");
        }
        return super.execute();
    }
    @Override
    public String toString() { return NAME; }
}
