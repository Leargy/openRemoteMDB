package entities;

//TODO: add annotations for parsing to XML

import com.sun.istack.internal.NotNull;

import javax.xml.bind.annotation.*;

@XmlRootElement (name = "reserved-organization")
@XmlAccessorType (XmlAccessType.FIELD)
public class OrganizationWithUId implements Mappable<Integer>{
    @XmlElement(name = "organization")
    private Organization ORGANIZATION;
    @XmlAttribute(name = "user-name")
    private String UID;
    private Integer collectionKey;

    public OrganizationWithUId() {
//        ORGANIZATION = new Organization();
//        UID = "";
    }
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
    public void help() {
        System.out.println("ay hochu piccy");
    }

    @Override
    public String getName() {
        return ORGANIZATION.getName();
    }
}
