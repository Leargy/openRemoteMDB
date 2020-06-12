package entities;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import parameters.entities.OrganizationParameters;

import javax.xml.bind.annotation.*;
import java.time.LocalDateTime;

@XmlRootElement(name = "organization")
@XmlAccessorType(XmlAccessType.FIELD)
public final class Organization implements Mappable<Integer> {
  @XmlTransient
  private static int count = 1;
  @XmlAttribute(name = "id")
  private final int id;
  @XmlAttribute(name = "name")
  @NotNull
  private final String name;

  @XmlElement(name = "coordinates")
  @NotNull
  private final Coordinates coordinates;

  public Coordinates getCoordinates() { return coordinates; }
  @XmlTransient
  @NotNull
  private final LocalDateTime creationDate = LocalDateTime.now();

  public LocalDateTime getCreationDate() { return creationDate; }

  @XmlAttribute(name = "annual-turnover")
  private final float annualTurnover;

  public float getAnnualTurnOver() { return annualTurnover; }

  @XmlElement(name = "fullname")
  @NotNull
  private final String fullname;

  public String getFullname() { return fullname; }

  @XmlAttribute(name = "employees-count")
  private final int employeesCount;

  public int getEmployeesCount() { return employeesCount; }

  @XmlElement(name = "organization-type", required = true)
  @Nullable
  private final OrganizationType type;

  public OrganizationType getType() { return type; }

  @XmlElement(name = "official-address")
  @Nullable
  private final Address officialAddress;

  public Address getAddress() { return officialAddress; }

  public Organization() {
    name  = "Sample Organization";
    coordinates = new Coordinates();
    annualTurnover = 0;
    fullname = "sample organization pattern";
    employeesCount = 0;
    type = OrganizationType.PUBLIC;
    officialAddress = new Address();
    id = Math.abs(hashCode()) + count++;
  }

  public Organization(String name, Coordinates coordinates,
                      float annualTurnover, String fullname,
                      int employeesCount, OrganizationType type, Address officialAddress) {
    this.name = (name.isEmpty() || (name == null))? "SampleOrganization" : name;
    this.fullname = (fullname == null)? "" : fullname;
    this.coordinates = (coordinates == null)? new Coordinates() : coordinates;
    this.annualTurnover = annualTurnover > 0? annualTurnover : Float.MIN_VALUE;
    this.employeesCount = employeesCount > 0? employeesCount : 1;
    this.type = type;
    this.officialAddress = officialAddress;
    int buffer = hashCode() + count++;
    id = (buffer == Integer.MIN_VALUE)? --buffer : Math.abs(buffer);
  }

  public Organization(OrganizationParameters params) {
    this(params.getName(), new Coordinates(params.getCoordinates()),
            params.getAnnualTurnOver(), params.getFullName(),
            params.getEmployeesCount(), params.getType(), new Address(params.getAddress()));
  }

  public int getID() {return id;}

  @Override
  public String toString() {
    return "Organization[id: " + id + "; name: " + name + "; coordinates: " + coordinates
            + "; creationDate: " + creationDate + "; annualTurnover: " + annualTurnover
            + "; fullname: " + fullname + "; employeesCount: " + employeesCount
            + "; type: " + type + "; officialAddress: " + officialAddress + "]";
  }

  @Override
  public boolean equals(Object other) {
    if (other == null) return false;
    if (this == other) return true;
    if (!this.getClass().getName().equals(other.getClass().getName())) return false;
    Organization another = (Organization) other;
    return (id == another.id)
            && (employeesCount == another.employeesCount)
            && (annualTurnover == another.annualTurnover)
            && (type == another.type)
            && (name.equals(another.name))
            && (fullname.equals(another.fullname))
            && (coordinates.equals(another.coordinates))
            && ((officialAddress == null) ? false : officialAddress.equals(another.officialAddress))
            && (creationDate.isEqual(another.creationDate));
  }

  @Override
  public int hashCode() {
    return (int) (
            name.hashCode() + fullname.hashCode()
                    + (employeesCount + annualTurnover) % 2
                    + (coordinates.hashCode()
                    + ((officialAddress != null)? officialAddress.hashCode() : 0)) % 3
                    + creationDate.hashCode()
                    + ((type == null)? 0 : type.hashCode())
    );
  }

  @Override
  public Integer getKey() { return id; }

  @Override
  public String getName() { return name; }
}