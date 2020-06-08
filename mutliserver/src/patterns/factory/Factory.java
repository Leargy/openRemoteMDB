package patterns.factory;

import communication.treasures.Wealths;
import communication.treasures.parameters.Parameters;

public interface Factory<T extends Wealths> {
    T make(Parameters params);

}
