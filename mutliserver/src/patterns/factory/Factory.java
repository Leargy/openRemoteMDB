package patterns.factory;
import parameters.Parameters;

public interface Factory<T extends Creatable> {
    T makeInstance(Parameters params);

}
