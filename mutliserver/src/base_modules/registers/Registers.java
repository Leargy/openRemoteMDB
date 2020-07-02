package base_modules.registers;

import communication.Report;
import patterns.mediator.Component;
import patterns.mediator.Controllers;
import uplink_bags.RegistrationBag;

public interface Registers extends Controllers, Component {
    Report register(RegistrationBag registrationData);
}
