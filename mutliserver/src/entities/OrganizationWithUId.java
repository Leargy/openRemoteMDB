package entities;

//TODO: add annotations for parsing to XML
public class OrganizationWithUId implements Mappable<Integer>{
    private final Organization ORGANIZATION;
    private final String UID;

    public OrganizationWithUId(Organization organization, String Uid) {
        ORGANIZATION = organization;
        UID = Uid;
    }

    public String getUID() {
        return UID;
    }

    public Organization getOrganization() {
        return ORGANIZATION;
    }

    @Override
    public Integer getKey() {
        return ORGANIZATION.getKey();
    }

    @Override
    public String getName() {
        return ORGANIZATION.getName();
    }
}
