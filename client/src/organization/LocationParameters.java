package organization;

public final class LocationParameters extends EntityParameters {
    public LocationParameters(Junker unsortedParameters) { super(unsortedParameters); }

    public long getX() {
        return UNSORTED_PARAMETERS.Digits()[0];
    }

    public Long getY() {
        return UNSORTED_PARAMETERS.Digits()[1];
    }

    public double getZ() { return UNSORTED_PARAMETERS.Cogits()[0]; }
}
