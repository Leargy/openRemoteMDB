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
        super(receiver);
        super.setUserLogin(user.getLogin());
        ORGANIZATION_TABLE_INTERACTOR = (OrganizationsTableInteractor) organizationTablesInteractor;
        TEMP_USER = user;
    }
    @Override
    public Report execute() {
        try {
            ORGANIZATION_TABLE_INTERACTOR.clearUserOrganization(TEMP_USER);
        }catch (SQLException ex) {
            return new Report(12,"Failed to clear user's organization");
        }
        return super.execute();
    }
}
