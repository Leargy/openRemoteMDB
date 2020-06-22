package entities;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@XmlRootElement(name = "organizations")
@XmlAccessorType(XmlAccessType.FIELD)
public final class Organizations {
  @XmlElement(name = "reserved-organization")
  private List<OrganizationWithUId> companies;

  public Organizations() { companies = new ArrayList<>(); }

  public Organizations(List<OrganizationWithUId> companies) { this.companies = companies; }

  public Organizations(Map<Integer, OrganizationWithUId> companies) { this.companies = new ArrayList<>(companies.values()); }

  public final List<OrganizationWithUId> getCompanies() { return companies; }
}
