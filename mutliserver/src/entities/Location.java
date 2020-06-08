package entities;

import com.sun.istack.internal.NotNull;
import communication.treasures.parameters.entities.LocationParameters;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "location")
@XmlAccessorType(XmlAccessType.FIELD)
public final class Location {

  @XmlAttribute(name = "x")
  private final long x;

  public long getX() { return x; }

  @XmlAttribute(name = "y")
  @NotNull
  private final Long y;

  public Long getY() { return y; }

  @XmlAttribute(name = "z")
  private final double z;

  public double getZ() { return z; }


  public Location() { x = 0L; y = Long.MIN_VALUE; z = 0.0; }

  public Location(long x, Long y, double z) {
    this.x = x;
    this.y = (y == null)? Long.MIN_VALUE : y;
    this.z = z;
  }

  public Location(LocationParameters params) {
    this(params.getX(), params.getY(), params.getZ());
  }

  @Override
  public String toString() {
    return "Location[x = " + x + "; y = " + y + "; z = " + z + "]";
  }

  @Override
  public boolean equals(Object other) {
    if (other == null) return false;
    if (this == other) return true;
    if (!this.getClass().getName().equals(other.getClass().getName())) return false;
    Location locate = (Location) other;
    return x == locate.x && y.equals(locate.y) && z == locate.z;
  }

  @Override
  public int hashCode() {
    int p = 0x2B;
    return (int) (((x * p) + y) * p + z);
  }
}
