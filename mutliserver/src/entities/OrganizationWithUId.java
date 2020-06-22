package entities;

//TODO: add annotations for parsing to XML

import com.sun.istack.internal.NotNull;

import javax.xml.bind.annotation.*;

@XmlRootElement (name = "reserved-organization")
@XmlAccessorType (XmlAccessType.FIELD)
public class OrganizationWithUId implements Mappable<Integer>{
    @XmlElement(name = "organization")
    private final Organization ORGANIZATION;
    @XmlAttribute(name = "user-name")
    private final String userLogin;
    private Integer collectionKey;

    public OrganizationWithUId() {
        ORGANIZATION = new Organization();
        userLogin = "";
    }
    public OrganizationWithUId(Organization organization, String Uid) {
        ORGANIZATION = organization;
        userLogin = Uid;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public Organization getOrganization() {
        return ORGANIZATION;
    }

    public float getAnnualTurnover() {
        return ORGANIZATION.getAnnualTurnOver();
    }

    public void setCollectionKey(Integer collectionKey) {
        this.collectionKey = collectionKey;
    }

    @Override
    public Integer getKey() {
        if (collectionKey == null){
            return ORGANIZATION.getKey();
        }
        return collectionKey;
    }

    @Override
    public String getName() {
        return ORGANIZATION.getName();
    }
}
