package entities;

import com.sun.istack.internal.NotNull;
import parameters.entities.CoordinatesParameters;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "coordinates")
@XmlAccessorType(XmlAccessType.FIELD)
public final class Coordinates {
  @XmlAttribute(name = "x")
  private final int x;

  public int getX() { return x; }

  @XmlAttribute(name = "y")
  @NotNull
  private final Float y;

  public Float getY() { return y; }

  public Coordinates() {
    x = 0;
    y = -538f + Float.MIN_VALUE;
  }

  public Coordinates(int x, Float y) {
    this.x = x;
    this.y = (y == null)? -538 + Float.MIN_VALUE : ((y < -538f + Float.MIN_VALUE)? -538f + Float.MIN_VALUE : y);
  }

  public Coordinates(int x) { this(x, null); }

  public Coordinates(Float y) { this(0, y); }

  public Coordinates(CoordinatesParameters params) { this(params.getX(), params.getY()); }

  @Override
  public String toString() {
    return "Coordinates[x = " + x + "; y = " + y + "]";
  }

  @Override
  public boolean equals(Object other) {
    if (other == null) return false;
    if (this == other) return true;
    if (!this.getClass().getName().equals(other.getClass().getName())) return false;
    Coordinates cord = (Coordinates) other;
    return x == cord.x && y.equals(cord.y);
  }

  @Override
  public int hashCode() {
    int p = 0x6F;
    return (int)((x * p) + y);
  }

}
