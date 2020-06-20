package instructions.concrete.base.DBbaseCommands;

import communication.Report;
import entities.Organization;
import entities.OrganizationWithUId;
import entities.User;
import extension_modules.dbinteraction.OrganizationsTableInteractor;
import instructions.concrete.base.Insert;
import patterns.command.Receiver;

public class DBInsert extends Insert {
    public final OrganizationsTableInteractor ORGANIZATION_TABLE_INTERACTOR;
    public final User TEMP_USER;
    /**
     * Конструктор, устанавливающий ссылку на
     * управленца коллекцией, добавляемый элемент
     * и ключ, по которому нужно добавить элемент
     *
     * @param sieve текущий управленец коллекцией
     * @param key   ключ добавляемого элемента
     * @param added добавляемый элемент
     */
    public DBInsert(Receiver sieve, Integer key, OrganizationWithUId organizationWithUId, OrganizationsTableInteractor organizationsTableInteractor, User tempUser) {
        super(sieve, key, organizationWithUId);
        ORGANIZATION_TABLE_INTERACTOR = organizationsTableInteractor;
        TEMP_USER = tempUser;
    }

    @Override
    public Report execute() {
        return super.execute();
    }
}
