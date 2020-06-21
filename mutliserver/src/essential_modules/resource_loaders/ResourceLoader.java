package essential_modules.resource_loaders;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public abstract class ResourceLoader<T> {
    protected abstract List<T> load(InputStream source);
    protected abstract boolean unload(OutputStream destination, List<T> elements);
}
