package extension_modules.databases_interaction.tables_managers.organizations;

import entities.Organization;
import essential_modules.resource_keepers.organizations.OrganizationsKeeper;
import essential_modules.resource_loaders.organizations.OrganizationsLoader;
import essential_modules.resource_managers.organizations.OrganizationsManager;
import extension_modules.databases_interaction.tables_keepers.organizations.OrganizationTableKeeper;
import extension_modules.databases_interaction.tables_loaders.organizations.OrganizationTableLoader;
import extension_modules.databases_interaction.tables_managers.TablesManager;
import parameters.entities.OrganizationParameters;

public class OrganizationsTableManager extends TablesManager<OrganizationParameters, Integer, Organization> {

    public OrganizationsTableManager(OrganizationTableLoader loader, OrganizationTableKeeper keeper) {
        super(loader, keeper);
    }

    @Override
    public String getTableName() {
        return null;
    }

    @Override
    public Organization findRecording(OrganizationParameters parameters) {
        return null;
    }

    @Override
    public boolean insertRecording(Organization record) {
        return false;
    }

    @Override
    public boolean deleteRecording(Organization record) {
        return false;
    }

    @Override
    public boolean replaceRecording(Organization oldValue, Organization newValue) {
        return false;
    }
}
