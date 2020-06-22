package entities;

//TODO: add annotations for parsing to XML

import com.sun.istack.internal.NotNull;

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
    private final String userLogin;

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

    @Override
    public Integer getKey() {
        return ORGANIZATION.getKey();
    }
    public void help() {
        System.out.println("ay hochu piccy");
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
