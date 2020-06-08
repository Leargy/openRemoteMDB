package base_modules.registers;

import communication.Report;
import communication.uplinkbags.PassBag;
import patterns.mediator.Component;
import patterns.mediator.Controllers;

public interface Registers extends Controllers, Component {
    Report register(PassBag clientData);
}
