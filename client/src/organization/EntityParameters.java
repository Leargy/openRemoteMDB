package organization;

public abstract class EntityParameters implements Parameters {
    protected final Junker UNSORTED_PARAMETERS;

    protected EntityParameters(Junker unsortedParameters) {
        UNSORTED_PARAMETERS = unsortedParameters;
    }
}
