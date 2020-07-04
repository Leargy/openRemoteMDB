package instructions.concrete.base.DBbaseCommands;

import communication.Report;
import entities.User;
import extension_modules.dbinteraction.OrganizationsTableInteractor;
import extension_modules.dbinteraction.TablesInteractor;
import instructions.concrete.base.Clear;
import parsing.customer.local.TotalCommander;
import patterns.command.Receiver;

import java.sql.SQLException;
import java.util.ArrayList;

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
//            return new Report(12,"Failed to clear user's organization!\n");
            return new Report(12,"Не удалось удалить ваши организации!\n ");
        }
        Report report = super.execute();
        report.setOrganizations(((TotalCommander)SIEVE).getOrganizationList());
        return report;
    }

    @Override
    public String toString() { return NAME; }
}
