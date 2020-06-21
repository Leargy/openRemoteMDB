package entities;

//TODO: add annotations for parsing to XML

import com.sun.istack.internal.NotNull;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name = "reserved-organization")
public class OrganizationWithUId implements Mappable<Integer>{
    @XmlElement(name = "organization")
    @NotNull
    private final Organization ORGANIZATION;
    @XmlAttribute(name = "user-name")
    @NotNull
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
