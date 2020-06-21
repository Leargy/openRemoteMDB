package essential_modules.resource_keepers.organizations;

import essential_modules.resource_keepers.organizations.OrganizationsKeeper;

import java.util.concurrent.ConcurrentHashMap;

public final class ConcurrentOrganizationsKeeper extends OrganizationsKeeper {
    public ConcurrentOrganizationsKeeper() {
        COMPANIES = new ConcurrentHashMap<>();
    }

}
