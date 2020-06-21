package essential_modules.registers;

import communication_tools.Report;
import patterns.mediator.Component;
import patterns.mediator.Controllers;
import communication_tools.uplink_bags.RegistrationSelectorBag;

public interface Registers extends Controllers, Component {
    Report register(RegistrationSelectorBag registrationData);
}
