package communication.treasures.parameters.entities;

import entities.OrganizationType;

public class OrganizationTypeParameter {
    private final String TYPE;

    public OrganizationTypeParameter(String type) {
        TYPE = type;
    }

    public OrganizationType getType() {
        return OrganizationType.PUBLIC;
    }
}
