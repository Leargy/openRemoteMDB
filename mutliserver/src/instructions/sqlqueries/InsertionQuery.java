package instructions.sqlqueries;

import communication_tools.Report;
import communication_tools.ReportsFormatter;
import entities.Organization;
import patterns.command.Command;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class InsertionQuery implements Command {
    private final Connection CURRENT_CONNECTION;
    private final UUID USER_ID;
    private final Integer ID;
    private final Organization EMBEDDED;
    protected final String ADVANCED_QUERY;

    public InsertionQuery(Connection connection, UUID user_id, Integer id, Organization embedded, String tableName) {
        CURRENT_CONNECTION = connection;
        USER_ID = user_id;
        ID = id;
        EMBEDDED = embedded;
        ADVANCED_QUERY = "INSERT INTO " + tableName + " VALUES "
                + prepareArguments(id, embedded, user_id) + ";";
    }


    @Override
    public Report execute() {
        Statement INSERTION = null;
        try {
            INSERTION = CURRENT_CONNECTION.createStatement();
            int lines = INSERTION.executeUpdate(ADVANCED_QUERY);
            if (lines == 1)
                return ReportsFormatter.makeUpSuccessReport("Inserting new organization");
            else return ReportsFormatter.makeUpUnsuccessReport("Inserting new organization");
        } catch (SQLException sqlException) { return ReportsFormatter.makeUpUnsuccessReport(); }
    }

    protected String prepareArguments(Integer id, Organization embedded, UUID user_id) {
        return "( "
                + String.join(", ",
                id.toString(),
                prepareLineArgument(embedded.getName()),
                prepareLineArgument(embedded.getFullname()),
                prepareLineArgument(embedded.getType().toString()),
                Integer.toString(embedded.getEmployeesCount()),
                Float.toString(embedded.getAnnualTurnOver()),
                prepareLineArgument(embedded.getCreationDate().toString()),
                Integer.toString(embedded.getCoordinates().getX()),
                embedded.getCoordinates().getY().toString(),
                prepareLineArgument(embedded.getAddress().getZipCode()),
                Long.toString(embedded.getAddress().getTown().getX()),
                embedded.getAddress().getTown().getY().toString(),
                Double.toString(embedded.getAddress().getTown().getZ()),
                user_id.toString()
        )
                + " )";
    }

    protected String prepareLineArgument(String argument) {
        return "\'" + argument + "\'";
    }
}
