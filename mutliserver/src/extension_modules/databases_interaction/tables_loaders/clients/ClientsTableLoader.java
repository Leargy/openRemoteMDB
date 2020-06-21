package extension_modules.databases_interaction.tables_loaders.clients;

import entities.User;
import extension_modules.databases_interaction.tables_loaders.TablesLoader;

import java.sql.*;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ClientsTableLoader extends TablesLoader<UUID, User> {
    private static final String DEFAULT_TABLE_NAME = "clients";
    private static final String SELECTION = "SELECT * FROM " + DEFAULT_TABLE_NAME + ";";
    private static final String INSERTION = "INSERT INTO " + DEFAULT_TABLE_NAME + " VALUES (?, ?, ?, ?);";

    @Override
    public final Map<UUID, User> load(Connection link) {
        Statement statement = prepareStatementFromConnection(link);
        if (statement == null) return null;
        ResultSet results = retrieveClients(statement);
        if (results == null) return null;
        Map<UUID, User> clients = parseQueryResult2Map(results);
        return clients;
    }

    protected Statement prepareStatementFromConnection(Connection link) {
        if (link == null) return null;
        try {
            return link.createStatement();
        } catch (SQLException sqlException) {
            return null;
        }
    }

    protected ResultSet retrieveClients(Statement statement) {
        try {
            return statement.executeQuery(SELECTION);
        } catch (SQLException sqlException) {
            return null;
        }
    }

    protected Map<UUID, User> parseQueryResult2Map(ResultSet results) {
        try {
            ConcurrentMap<UUID, User> map = new ConcurrentHashMap<>();
            results.first();
            do {
                User row = parseUserFromRow(results);
                if (row == null) return null;
                else map.put(row.getID(), row);
            } while (results.next());
            return map;
        } catch (SQLException sqlException) {
            return null;
        }
    }

    protected User parseUserFromRow(ResultSet results) {
        try {
            UUID user_id = results.getObject("id", UUID.class);
            String login = results.getString("login");
            String password = results.getString("password");
            String environment_name = results.getString("environment");
            return new User(user_id, login, password, environment_name);
        } catch (SQLException sqlException) {
            return null;
        }
    }

    @Override
    public final boolean unload(Connection link, Map<UUID, User> elements) {
        int[] insertedRows = new int[]{0};
        try {
            PreparedStatement statement = link.prepareStatement(INSERTION);
            elements.forEach(
                    (key, value)->{
                        insertedRows[0] += insertUser2Clients(statement, key, value);
                    }
            );
            return insertedRows[0] == elements.size();
        } catch (SQLException sqlException) {
            unloadingExceptionLog();
            return false;
        }
    }

    protected int insertUser2Clients(PreparedStatement statement, UUID key, User value) {
        try {
            statement.setObject(1, key);
            statement.setString(2, value.getLogin());
            statement.setString(3, value.getPassword());
            statement.setString(4, value.getPassword());
            return statement.executeUpdate();
        } catch (SQLException sqlException) {
            return 0;
        }
    }

    protected void unloadingExceptionLog() { return; }
}
