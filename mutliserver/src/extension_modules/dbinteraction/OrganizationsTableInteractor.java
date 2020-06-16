package extension_modules.dbinteraction;

import communication.Report;

public class OrganizationsTableInteractor implements TablesInteractor {
    private static final String DB_TABLE_NAME = "organizations";

    @Override
    public String getTableName() { return DB_TABLE_NAME; }
}
