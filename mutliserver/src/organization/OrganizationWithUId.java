package organization;

//TODO: add annotations for parsing to XML

import com.sun.istack.internal.NotNull;
import organization.*;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;

@XmlRootElement (name = "reserved-organization")
public class OrganizationWithUId implements Mappable<Integer> {
    @XmlElement(name = "organization")
    @NotNull
    private final Organization ORGANIZATION;
    @XmlAttribute(name = "user-name")
    @NotNull
    public final String USER_LOGIN;
    @XmlAttribute(name = "collection-key")
    @NotNull
    public final int  COLLECTION_KEY;

    public OrganizationWithUId() {
        ORGANIZATION = new Organization();
        USER_LOGIN = "debug";
        COLLECTION_KEY = 0xDEAD;
    }

    public OrganizationWithUId(Organization organization, String user_login, int collectionKey) {
        ORGANIZATION = organization;
        USER_LOGIN = user_login;
        COLLECTION_KEY = collectionKey;
    }

    public String getUserLogin() {
        return USER_LOGIN;
    }

    public Organization getOrganization() {
        return ORGANIZATION;
    }

    @Override
    public Integer getKey() {
        return COLLECTION_KEY;
    }

    @Override
    public String getName() {
        return ORGANIZATION.getName();
    }

    public int getEmployeesCount() {
        return ORGANIZATION.getEmployeesCount();
    }

    public float getAnnualTurnover() {
        return ORGANIZATION.getAnnualTurnOver();
    }

    public Coordinates getCoordinates() {
        return ORGANIZATION.getCoordinates();
    }

    public Address getAddress() {
        return ORGANIZATION.getAddress();
    }

    public LocalDateTime getCreationDate() {
        return ORGANIZATION.getCreationDate();
    }

    public String getFullname() {
        return ORGANIZATION.getFullname();
    }

    public OrganizationType getType() {
        return ORGANIZATION.getType();
    }

    @Override
    public String toString() {
        return ORGANIZATION.toString();
    }
}
