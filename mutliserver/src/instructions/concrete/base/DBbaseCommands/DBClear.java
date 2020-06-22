package instructions.concrete.base.DBbaseCommands;

import communication.Report;
import entities.User;
import extension_modules.dbinteraction.OrganizationsTableInteractor;
import extension_modules.dbinteraction.TablesInteractor;
import instructions.concrete.base.Clear;
import patterns.command.Receiver;

import java.sql.SQLException;

public class DBClear extends Clear {
    public final OrganizationsTableInteractor ORGANIZATION_TABLE_INTERACTOR;
    public final User TEMP_USER;

    public DBClear(Receiver receiver, TablesInteractor organizationTablesInteractor, User user) {
        super(receiver,user.getLogin());
        ORGANIZATION_TABLE_INTERACTOR = (OrganizationsTableInteractor) organizationTablesInteractor;
        TEMP_USER = user;
    }
    @Override
    public Report execute() {
        Report dbReport = null;
        try {
            dbReport = ORGANIZATION_TABLE_INTERACTOR.clearUserOrganization(TEMP_USER);
        }catch (SQLException ex) {
            return new Report(12,"Failed to clear user's organization!\n");
        }
        return super.execute();
    }

    @Override
    public String toString() { return NAME; }
}
