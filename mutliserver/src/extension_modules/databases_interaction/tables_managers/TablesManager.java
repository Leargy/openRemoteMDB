package extension_modules.databases_interaction.tables_managers;

import extension_modules.databases_interaction.tables_keepers.TablesKeeper;
import extension_modules.databases_interaction.tables_loaders.TablesLoader;
import parameters.Parameters;

public abstract class TablesManager<P extends Parameters, K, V> {
    private final TablesLoader<K, V> LOADER;
    private final TablesKeeper KEEPER;

    public TablesManager(TablesLoader<K, V> loader, TablesKeeper keeper) {
        LOADER = loader;
        KEEPER = keeper;
    }

    public abstract String getTableName();
    public abstract V findRecording(P parameters);
    public abstract boolean insertRecording(V record);
    public abstract boolean deleteRecording(V record);
    public abstract boolean replaceRecording(V oldValue, V newValue);
}
