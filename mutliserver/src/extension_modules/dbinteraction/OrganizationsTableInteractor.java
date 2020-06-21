package extension_modules.dbinteraction;

import communication.Report;
import entities.Organization;
import entities.OrganizationWithUId;
import entities.User;

import java.sql.SQLException;

public class OrganizationsTableInteractor implements TablesInteractor {
    public static final String DB_TABLE_NAME = "organizations";


    public Report clearUserOrganization(User user) throws SQLException {
        //TODO: write the realization of targeted "clear"
        return null;
    }
    public Report insertUserOrganization(Integer key, OrganizationWithUId organizationWithUId) throws SQLException {
        return null;
    }

    public Report removeUserOrganizationByKey(User user, Integer key) throws SQLException {
        return null;
    }

    public Report replaceUserOrganizationIfLower(User user, Integer key, OrganizationWithUId organizationWithUId) throws SQLException{
        return null;
    }

    public Report replaceUserOrganizationIfGreater(User user, Integer key, OrganizationWithUId organizationWithUId) throws SQLException{
        return null;
    }

    public Report removeLoweUserOrganization(User user,OrganizationWithUId organizationWithUId) throws SQLException {
        return null;
    }

    public Report updateUserOrganization(User user, Integer key, OrganizationWithUId organizationWithUId) throws SQLException{
        return null;
    }
}
