package organization;

import java.io.Serializable;

public class OrganizationTypeParameter implements Serializable {
    private final String TYPE;

    public OrganizationTypeParameter(String type) {
        TYPE = type;
    }

    public OrganizationType getType() {
        return OrganizationType.PUBLIC;
    }
}
