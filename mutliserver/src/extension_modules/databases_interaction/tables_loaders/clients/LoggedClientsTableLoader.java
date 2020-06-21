package extension_modules.databases_interaction.tables_loaders.clients;

import entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;
import java.util.UUID;

public final class LoggedClientsTableLoader extends ClientsTableLoader {
    private final Logger LOG = LoggerFactory.getLogger(ClientsTableLoader.class);
    @Override
    protected Statement prepareStatementFromConnection(Connection link) {
        LOG.info("Trying prepare new statement from connection");
        Statement result =  super.prepareStatementFromConnection(link);
        if (result == null)
            LOG.error("Unable to create new statement from connection");
        else LOG.info("Successful statement creation");
        return result;
    }

    @Override
    protected ResultSet retrieveClients(Statement statement) {
        LOG.info("Trying get clients from database");
        ResultSet clients = super.retrieveClients(statement);
        if (clients == null)
            LOG.error("Errors happened while getting clients from database");
        else LOG.info("Successful retrieving clients from database");
        return clients;
    }

    @Override
    protected Map<UUID, User> parseQueryResult2Map(ResultSet results) {
        Map<UUID, User> users = super.parseQueryResult2Map(results);
        if (users == null)
            LOG.error("Users not found in results");
        else LOG.info("Successful preparation users for returning");
        return users;
    }

    @Override
    protected User parseUserFromRow(ResultSet results) {
        User user = super.parseUserFromRow(results);
        if (user == null)
            LOG.error("Cannot parse user from row");
        else LOG.info("Successful user parsing from row: [" + user.getID() + " : " + user.getLogin() + "]");
        return user;
    }

    @Override
    protected int insertUser2Clients(PreparedStatement statement, UUID key, User value) {
        int inserted = super.insertUser2Clients(statement, key, value);
        if (inserted != 1)
            LOG.warn("User " + value.getLogin() + "." + key + " wasn't inserted in table");
        else LOG.info("Successful insertion User " + value.getLogin() + "." + key);
        return inserted;
    }

    @Override
    protected void unloadingExceptionLog() {
        LOG.error("Errors happened while unloading clients to database table");
    }
}
