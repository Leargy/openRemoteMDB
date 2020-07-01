package locale.connection;

public class ConnectionResources extends java.util.ListResourceBundle {
    private static final Object[][] contents = {
            // BEGINNING OF INTERNATIONALIZATION
            {"Language", "Language"},
            {"IP", "IP"},
            {"Port", "Port"},
            {"Connect", "Connect"}
            // ENDING OF INTERNATIONALIZATION
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
