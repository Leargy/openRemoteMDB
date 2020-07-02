package patterns.factory;
import organization.Parameters;

public interface Factory<T extends Creatable> {
    T makeInstance(Parameters params);

}
