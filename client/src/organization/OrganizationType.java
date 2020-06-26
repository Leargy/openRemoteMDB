package organization;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlType(name = "organization-type")
@XmlEnum(value = String.class)
public enum OrganizationType implements Serializable {
  @XmlEnumValue("public") PUBLIC,
  @XmlEnumValue("trust") TRUST,
  @XmlEnumValue("private-limited-company") PRIVATE_LIMITED_COMPANY,
  @XmlEnumValue("open-joint-stock-company") OPEN_JOINT_STOCK_COMPANY;

  public static String getValues() {
    StringBuilder value = new StringBuilder();
    value.append("\n──────Organization types───────\n");
    value.append("----------- " + PUBLIC + " ------------\n");
    value.append("----------- " + TRUST + " -------------\n");
    value.append("--- " + PRIVATE_LIMITED_COMPANY + " ---\n");
    value.append("--- " + OPEN_JOINT_STOCK_COMPANY + " --\n");
    value.append("───────────────────────────────\n:");
    return value.toString();
  }
}
