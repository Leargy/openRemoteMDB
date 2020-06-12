package parameters.entities;

import parameters.Junker;
import parameters.Parameters;

public abstract class EntityParameters implements Parameters {
    protected final Junker UNSORTED_PARAMETERS;

    protected EntityParameters(Junker unsortedParameters) {
        UNSORTED_PARAMETERS = unsortedParameters;
    }
}
