package essential_modules.resource_loaders.organizations;

import entities.Organization;
import entities.Organizations;
import essential_modules.resource_loaders.ResourceLoader;

import java.io.OutputStream;
import java.util.List;

public abstract class OrganizationsLoader extends ResourceLoader<Organization> {
    public final boolean unload(OutputStream destination, Organizations elements) {
        return this.unload(destination, elements.getCompanies());
    }
}
