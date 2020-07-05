package sample.drawing_utils.materials;

import organization.OrganizationWithUId;

public interface Linkable {
    void setLink(OrganizationWithUId link);
    OrganizationWithUId getLink();
}
