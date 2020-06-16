package extension_modules.dbinteraction;

import communication.Report;
import communication.ReportsFormatter;
import extension_modules.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parameters.entities.UsersParameters;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class UsersTableInteractor implements TablesInteractor {
    private final Logger LOG = LoggerFactory.getLogger(UsersTableInteractor.class);
    private static final String DB_TABLE_NAME = "clients";
    
    public Report addNewUser(UsersParameters params) {
        LOG.info("Adding new user to table");
        if (selectUserFromParameters(params) == null) {
            LOG.info("There are no users with such parameters");
            return ReportsFormatter.makeUpUnsuccessReport(ClassUtils.retrieveExecutedMethod());
        }
        final String INSERTION_USER_QUERY = "INSERT INTO "
                + DB_TABLE_NAME + " VALUES "
                + prepareInsertionParameters(params)
                + ";";
        LOG.info("Query prepared");
        LOG.info("Connecting to database...");
        Report connectionResults = DataBaseConnector
                .getInstance()
                .connect2DBUsingProperties("database.properties");
        if (connectionResults.isSuccessful()) {
            LOG.info("Connection successful");
            Statement insert = null;
            LOG.info("Trying execute adding query");
            try {
                insert = DataBaseConnector.getInstance()
                        .retrieveCurrentConnection().createStatement();
                insert.executeUpdate(INSERTION_USER_QUERY);
                LOG.info("Add execution success");
            } catch (SQLException sqlException) {
                LOG.error("Errors happened while executing query");
                return ReportsFormatter.makeUpUnsuccessReport(ClassUtils.retrieveExecutedMethod());
            }
            return ReportsFormatter.makeUpSuccessReport(ClassUtils.retrieveExecutedMethod());
        } else {
            LOG.info("Unsuccessful connection");
            return connectionResults;
        }
    }
    
    public Report removeExistingUser(UsersParameters params) {
        LOG.info("Removing user from clients");
        if (selectUserFromParameters(params) == null) {
            LOG.info("Not exists any user with that parameters");
            return ReportsFormatter.makeUpUnsuccessReport(ClassUtils.retrieveExecutedMethod());
        }
        final String REMOVING_USER_QUERY = "DELETE FROM "
                + DB_TABLE_NAME + " WHERE login = "
                + prepareStringParameter(params.getLogin()) + " AND "
                + "password = " + prepareStringParameter(params.getPassword()) + " AND "
                + "environment = " + prepareStringParameter(params.getEnvironmentVariableName()) + ";";
        LOG.info("Connecting to database");
        Report connectionResults = DataBaseConnector
                .getInstance()
                .connect2DBUsingProperties("database.properties");
        if (connectionResults.isSuccessful()) {
            LOG.info("Connection success");
            Statement remove = null;
            LOG.info("Trying execute removing query");
            try {
                remove = DataBaseConnector.getInstance()
                        .retrieveCurrentConnection().createStatement();
                int lines = remove.executeUpdate(REMOVING_USER_QUERY);
                if (lines == 0)
                    LOG.info("There are no users with such parameters");
                LOG.info("Remove execution success");
            } catch (SQLException sqlException) {
                LOG.error("Errors happened while executing query");
                return ReportsFormatter.makeUpSuccessReport(ClassUtils.retrieveExecutedMethod());
            }
            return ReportsFormatter.makeUpSuccessReport(ClassUtils.retrieveExecutedMethod());
        } else {
            LOG.info("Unsuccessful connection");
            return ReportsFormatter.makeUpSuccessReport(ClassUtils.retrieveExecutedMethod());
        }
    }

    public UsersParameters selectUserFromParameters(UsersParameters params) {
        LOG.info("Searching user in table");
        final String SELECT_USER_QUERY = "SELECT * FROM "
                + DB_TABLE_NAME + " WHERE login = "
                + prepareStringParameter(params.getLogin()) + " AND "
                + " password = " + prepareStringParameter(params.getPassword()) + ";";
        LOG.info("Prepared query");
        Report connectionResult = DataBaseConnector.getInstance()
                .connect2DBUsingProperties("database.properties");
        if (connectionResult.isSuccessful()) {
            LOG.info("Connection success");
            Statement select = null;
            LOG.info("Trying execute searching query");
            try {
                select = DataBaseConnector.getInstance()
                        .retrieveCurrentConnection().createStatement();
                ResultSet results = select.executeQuery(SELECT_USER_QUERY);
                boolean isOutOfBounds = results.first();
                if (isOutOfBounds) {
                    String login = results.getString("login");
                    String password = results.getString("password");
                    String environment = results.getString("envi↑ronment");
                    UUID id = UUID.fromString(results.getString("id"));
                    return new UsersParameters(login, password, environment, id);
                } else {
                    LOG.info("Not found any users with such parameters");
                    return null;
                }
            } catch (SQLException sqlException) {
                LOG.info("Errors happened while executing query");
                return null;
            }
        } else {
            LOG.info("Unsuccessful connection");
            return null;
        }
    }
    
    @Override
    public String getTableName() { return DB_TABLE_NAME; }

    protected String prepareInsertionParameters(UsersParameters params) {
        return "("
                + String.join(
                        ", ",
                prepareStringParameter(params.getLogin()),
                prepareStringParameter(params.getPassword()),
                prepareStringParameter(params.getEnvironmentVariableName())
                )
                + ")";
    }

    protected String prepareStringParameter(String parameter) {
        return "\'" + parameter + "\'";
    }
}
