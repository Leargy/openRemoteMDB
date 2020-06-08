package communication.treasures.parameters.entities;

import communication.treasures.parameters.Junker;
import communication.treasures.parameters.Parameters;

public abstract class EntityParameters implements Parameters {
    protected final Junker UNSORTED_PARAMETERS;

    protected EntityParameters(Junker unsortedParameters) {
        UNSORTED_PARAMETERS = unsortedParameters;
    }
}
