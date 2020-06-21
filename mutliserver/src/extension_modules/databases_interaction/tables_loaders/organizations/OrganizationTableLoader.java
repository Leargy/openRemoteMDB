package extension_modules.databases_interaction.tables_loaders.organizations;

import entities.Organization;
import extension_modules.databases_interaction.tables_loaders.TablesLoader;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Map;

public class OrganizationTableLoader extends TablesLoader<Integer, Organization> {
    @Override
    public final Map<Integer, Organization> load(Connection link) {
        return null;
    }
    @Override
    public final boolean unload(Connection link, Map<Integer, Organization> elements) {
        return false;
    }
}
