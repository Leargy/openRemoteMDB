package extension_modules.databases_interaction.tables_loaders;

import java.sql.Connection;
import java.util.Map;

public abstract class TablesLoader<K, V> {
    public abstract Map<K, V> load(Connection link);

    public abstract boolean unload(Connection link, Map<K, V> elements);
}
