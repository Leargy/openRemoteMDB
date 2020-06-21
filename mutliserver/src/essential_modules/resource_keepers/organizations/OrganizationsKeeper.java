package essential_modules.resource_keepers.organizations;

import entities.Organization;
import essential_modules.resource_keepers.ResourceKeeper;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class OrganizationsKeeper extends ResourceKeeper {
    protected volatile Map<Integer, Organization> COMPANIES;

    public OrganizationsKeeper() {
        COMPANIES = new HashMap<>();
    }

    public Organization insert(Integer key, Organization element) {
        return (key == null || element == null)? null : COMPANIES.put(key, element);
    }

    public boolean remove(Integer key, Organization element) {
        return (key == null || element == null)? false : COMPANIES.remove(key, element);
    }

    public Organization remove(Integer key) {
        return (key == null)? null : COMPANIES.remove(key);
    }

    public Organization filterByKey(Integer key) {
        return COMPANIES.getOrDefault(key, null);
    }

    public Organization replace(Integer key, Organization element) {
        return (key == null || element == null)? null : COMPANIES.replace(key, element);
    }

    public boolean replace(Integer key, Organization oldElement, Organization newElement) {
        return (key == null || newElement == null)? false : COMPANIES.replace(key, oldElement, newElement);
    }

    public boolean clear() {
        try {
            COMPANIES.clear();
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    public Collection<Organization> show() { return COMPANIES.values(); }
}
