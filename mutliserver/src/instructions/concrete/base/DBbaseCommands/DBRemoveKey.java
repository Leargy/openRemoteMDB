package instructions.concrete.base.DBbaseCommands;

import communication.Report;
import entities.User;
import extension_modules.dbinteraction.OrganizationsTableInteractor;
import instructions.concrete.base.RemoveKey;
import patterns.command.Receiver;

import java.sql.SQLException;

public class DBRemoveKey extends RemoveKey {
    public final OrganizationsTableInteractor ORGANIZATION_TABLE_INTERACTOR;
    public final User TEMP_USER;
    /**
     * Конструктор, устанавливающий ссылку на
     * управленца коллекцией и ключ элемента,
     * который подлежит удалению
     *
     * @param sieve         текущий управленец коллекцией
     * @param removable_key ключ удаляемого элемента
     */
    public DBRemoveKey(Receiver sieve, Integer removable_key, OrganizationsTableInteractor organizationsTableInteractor, User tempUser) {
        super(sieve, removable_key);
        this.ORGANIZATION_TABLE_INTERACTOR = organizationsTableInteractor;
        this.TEMP_USER = tempUser;
    }

    @Override
    public Report execute() {
        Report dbReport = null;
        try {
            dbReport = ORGANIZATION_TABLE_INTERACTOR.removeUserOrganizationByKey(TEMP_USER, super.key);
        } catch (SQLException ex) {
            return new Report(12, "Failed to remover organization!\n" + dbReport);
        }
        return super.execute();
    }
    @Override
    public String toString() { return NAME; }
}
