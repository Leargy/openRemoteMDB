package instructions.concrete.base.DBbaseCommands;

import communication.Report;
import entities.OrganizationWithUId;
import entities.User;
import extension_modules.dbinteraction.OrganizationsTableInteractor;
import instructions.concrete.base.Update;
import patterns.command.Receiver;

import java.sql.SQLException;

public class DBUpdate extends Update {
    public final OrganizationsTableInteractor ORGANIZATION_TABLE_INTERACTOR;
    public final User TEMP_USER;

    /**
     * Конструктор, устанавливающий ссылку на
     * управленца коллекцией, а также идентификатор
     * обновляемого элемента и элемент, которым
     * заменяем
     *
     * @param sieve               текущий управленец коллекцией
     * @param id                  идентификатор обновляемого элемента
     * @param organizationWithUId
     */
    public DBUpdate(Receiver sieve, Integer id, OrganizationWithUId organizationWithUId, OrganizationsTableInteractor organizationsTableInteractor, User tempUser) {
        super(sieve, id, organizationWithUId);
        ORGANIZATION_TABLE_INTERACTOR = organizationsTableInteractor;
        TEMP_USER = tempUser;
    }

    @Override
    public Report execute() {
        Report dbReport = null;
        try {
            dbReport = ORGANIZATION_TABLE_INTERACTOR.updateUserOrganization(TEMP_USER, super.id, super.EMBEDDED);
        } catch (SQLException ex) {
            return new Report(12, "Failed to remove lower organization!\n");
        }
        return super.execute();
    }
    @Override
    public String toString() { return NAME; }
}
