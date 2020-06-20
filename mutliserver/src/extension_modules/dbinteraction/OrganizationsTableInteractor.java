package extension_modules.dbinteraction;

import communication.Report;
import entities.User;

import java.sql.SQLException;

public class OrganizationsTableInteractor implements TablesInteractor {
    public static final String DB_TABLE_NAME = "organizations";


    public Report clearUserOrganization(User user) throws SQLException {
        //TODO: write the realization of targeted "clear"
        return null;
    }
}
