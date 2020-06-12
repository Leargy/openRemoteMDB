package parameters.entities;

import parameters.Junker;

public final class AddressParameters extends EntityParameters {
    private final LocationParameters LOCATION_PARAMETERS;

    public AddressParameters(Junker unsortedParameters) {
        super(unsortedParameters);
        LOCATION_PARAMETERS = new LocationParameters(unsortedParameters.Guts()[0]);
    }

    public String getZipCode() { return UNSORTED_PARAMETERS.Lines()[0]; }

    public LocationParameters getLocationParameters() {
        return LOCATION_PARAMETERS;
    }
}
