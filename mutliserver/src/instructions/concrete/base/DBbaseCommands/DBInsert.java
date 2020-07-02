package instructions.concrete.base.DBbaseCommands;

import communication.Report;
import organization.OrganizationWithUId;
import extension_modules.dbinteraction.OrganizationsTableInteractor;
import instructions.concrete.base.Insert;
import patterns.command.Receiver;

import java.sql.SQLException;

public class DBInsert extends Insert {
    public final OrganizationsTableInteractor ORGANIZATION_TABLE_INTERACTOR;
    /**
     * Конструктор, устанавливающий ссылку на
     * управленца коллекцией, добавляемый элемент
     * и ключ, по которому нужно добавить элемент
     *
     * @param sieve текущий управленец коллекцией
     * @param key   ключ добавляемого элемента
     * @param added добавляемый элемент
     */
    public DBInsert(Receiver sieve, Integer key, OrganizationWithUId organizationWithUId, OrganizationsTableInteractor organizationsTableInteractor) {
        super(sieve, key, organizationWithUId);
        ORGANIZATION_TABLE_INTERACTOR = organizationsTableInteractor;
    }

    @Override
    public Report execute() {
        Report dbReport = null;
        try {
            dbReport = ORGANIZATION_TABLE_INTERACTOR.insertUserOrganization(super.key,super.EMBEDDED);
        }catch (SQLException ex) {
//            return new Report(12,"Failed to insert user's organization!\n");
            return new Report(12,"Не удалось добавить организацию пользователя!\n");
        }
        EMBEDDED.getOrganization().id = ORGANIZATION_TABLE_INTERACTOR.getDBOrganizationId(EMBEDDED);
        return super.execute();
    }
    @Override
    public String toString() { return NAME; }
}
