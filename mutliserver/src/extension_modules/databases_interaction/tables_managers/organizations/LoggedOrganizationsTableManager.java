package extension_modules.databases_interaction.tables_managers.organizations;

import essential_modules.resource_keepers.organizations.OrganizationsKeeper;
import essential_modules.resource_loaders.organizations.OrganizationsLoader;
import extension_modules.databases_interaction.tables_keepers.organizations.OrganizationTableKeeper;
import extension_modules.databases_interaction.tables_loaders.organizations.OrganizationTableLoader;

public final class LoggedOrganizationsTableManager extends OrganizationsTableManager {
    public LoggedOrganizationsTableManager(OrganizationTableLoader loader, OrganizationTableKeeper keeper) {
        super(loader, keeper);
    }
}
