package organization;


public final class OrganizationParameters extends EntityParameters {
    private final AddressParameters ADDRESS_PARAMETERS;
    private final CoordinatesParameters COORDINATES_PARAMETERS;
    private final OrganizationTypeParameter TYPE_PARAMETER;

    public OrganizationParameters(Junker unsortedParameters) {
        super(unsortedParameters);
        COORDINATES_PARAMETERS = new CoordinatesParameters(unsortedParameters.Guts()[0]);
        ADDRESS_PARAMETERS = new AddressParameters(unsortedParameters.Guts()[1]);
        TYPE_PARAMETER = new OrganizationTypeParameter(unsortedParameters.Lines()[2]);

    }

    public String getName() { return UNSORTED_PARAMETERS.Lines()[0]; }

    public String getFullName() { return UNSORTED_PARAMETERS.Lines()[1]; }

    public OrganizationType getType() { return TYPE_PARAMETER.getType(); }

    public int getEmployeesCount() { return (int) UNSORTED_PARAMETERS.Digits()[0]; }

    public float getAnnualTurnOver() { return (float) UNSORTED_PARAMETERS.Cogits()[0]; }

    public CoordinatesParameters getCoordinates() {
        return COORDINATES_PARAMETERS;
    }

    public AddressParameters getAddress() {
        return ADDRESS_PARAMETERS;
    }
}
