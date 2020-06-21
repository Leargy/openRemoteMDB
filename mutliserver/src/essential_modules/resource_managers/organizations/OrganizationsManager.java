package essential_modules.resource_managers.organizations;

import essential_modules.resource_keepers.organizations.OrganizationsKeeper;
import essential_modules.resource_loaders.organizations.OrganizationsLoader;
import essential_modules.resource_managers.ResourceManager;

public class OrganizationsManager extends ResourceManager {
    private final OrganizationsKeeper STORAGE;
    private final OrganizationsLoader LOADER;

    public OrganizationsManager(OrganizationsLoader loader, OrganizationsKeeper keeper) {
        LOADER = loader;
        STORAGE = keeper;
    }

    public String getStorageInfo() {
        return null;
    }
}
