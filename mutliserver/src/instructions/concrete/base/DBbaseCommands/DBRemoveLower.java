package instructions.concrete.base.DBbaseCommands;

import communication.Report;
import organization.OrganizationWithUId;
import entities.User;
import extension_modules.dbinteraction.OrganizationsTableInteractor;
import instructions.concrete.extended.RemoveLower;
import patterns.command.Receiver;

import java.sql.SQLException;

public class DBRemoveLower extends RemoveLower {
    public final OrganizationsTableInteractor ORGANIZATION_TABLE_INTERACTOR;
    public final User TEMP_USER;
    /**
     * Конструктор, устанавливающий ссылку на
     * управленца коллекцией, а также объект
     * добавляемого элемента
     *
     * @param sieve текущий управленец коллекцией
     * @param added добавляемый элемент
     */
    public DBRemoveLower(Receiver sieve, OrganizationWithUId added, OrganizationsTableInteractor organizationsTableInteractor, User tempUser) {
        super(sieve, added);
        ORGANIZATION_TABLE_INTERACTOR = organizationsTableInteractor;
        TEMP_USER = tempUser;
    }

    @Override
    public Report execute() {
        Report dbReport = null;
        try {
            dbReport = ORGANIZATION_TABLE_INTERACTOR.removeLowerUserOrganization(TEMP_USER, super.EMBEDDED);
        } catch (SQLException ex) {
            return new Report(12, "Failed to remove lower organization!\n");
        }
        return super.execute();
    }
    @Override
    public String toString() { return NAME; }
}
