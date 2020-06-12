package parameters.entities;

import parameters.Junker;

public final class CoordinatesParameters extends EntityParameters {
    public CoordinatesParameters(Junker unsortedParameters) {
        super(unsortedParameters);
    }

    public int getX() { return (int) UNSORTED_PARAMETERS.Digits()[0]; }

    public Float getY() {
        return (float) UNSORTED_PARAMETERS.Cogits()[0];
    }
}
