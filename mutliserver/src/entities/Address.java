package entities;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import communication.treasures.parameters.entities.AddressParameters;
import communication.treasures.parameters.entities.LocationParameters;

import javax.xml.bind.annotation.*;


@XmlRootElement(name = "address")
@XmlAccessorType(XmlAccessType.FIELD)
public final class Address {

  @XmlAttribute(name = "zipcode")
  @NotNull
  private final String zipCode;

  public String getZipCode() { return zipCode; }

  @XmlElement(name = "town")
  @Nullable
  private final Location town;

  public Location getTown() { return town; }

  public Address() {
    zipCode = "";
    town = null;
  }

  public Address(String zipCode, Location town) {
    this.zipCode = zipCode == null? "" : zipCode;
    this.town = town;
  }

  public Address(AddressParameters params) { this(params.getZipCode(), new Location(params.getLocationParameters())); }

  @Override
  public String toString() {
    return "Address[zipCode: " + zipCode + "; town: " + town + "]";
  }

  @Override
  public boolean equals(Object other) {
    if (other == null) return false;
    if (this == other) return true;
    if (!this.getClass().getName().equals(other.getClass().getName())) return false;
    Address address = (Address) other;
    return zipCode.equals(address.zipCode) && town.equals(address.town);
  }

  @Override
  public int hashCode() {
    int p = 0x1F;
    return zipCode.hashCode() * p + ((town == null)? 0 : town.hashCode());
  }
}
