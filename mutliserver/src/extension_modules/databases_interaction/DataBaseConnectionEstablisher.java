package extension_modules.databases_interaction;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DataBaseConnectionEstablisher {
    private static volatile DataBaseConnectionEstablisher INSTANCE;
    public static final String DEFAULT_DATABASE_DRIVER_NAME = "org.postgresql.Driver";
    public static final String DEFAULT_PROPERTIES_SOURCE = "database.properties";
    private final String CURRENT_DATABASE_DRIVER_NAME;
    private final String CURRENT_PROPERTIES_SOURCE;
    private volatile Connection CURRENT_CONNECTION;

    private DataBaseConnectionEstablisher(String driverName, String source) {
        CURRENT_DATABASE_DRIVER_NAME = driverName;
        CURRENT_PROPERTIES_SOURCE = source;
    }

    public static DataBaseConnectionEstablisher newDefaultConnector() {
        return INSTANCE = new DataBaseConnectionEstablisher(DEFAULT_DATABASE_DRIVER_NAME, DEFAULT_PROPERTIES_SOURCE);
    }

    public static DataBaseConnectionEstablisher newParametrizedConnector(String driverName, String fileSource) {
        return INSTANCE = new DataBaseConnectionEstablisher(driverName, fileSource);
    }

    public static DataBaseConnectionEstablisher getConnectionEstablisher() {
        if (INSTANCE == null)
            synchronized (DataBaseConnectionEstablisher.class) {
                if (INSTANCE == null)
                    return newDefaultConnector();
                else return INSTANCE;
            }
        else return INSTANCE;
    }

    protected boolean loadDriver(String driverName) {
        try {
            Class.forName(driverName);
            return true;
        } catch (ClassNotFoundException classNotFoundException) {
            return false;
        }
    }

    protected Connection establishConnectionUsingFileProperties(String driverName, String fileName) {
        Path userDir = Paths.get(System.getProperty("user.dir"));
        Path propertiesWay = userDir.resolve(fileName);
        Properties props = new Properties();
        try (InputStream in = Files.newInputStream(propertiesWay)) {
            props.load(in);
        } catch (IOException ioException) {
            return null;
        }
        String drivers = props.getProperty("jdbc.drivers");
        if (drivers != null) System.setProperty("jdbc.drivers", drivers);
        else System.setProperty("jdbc.drivers", driverName);
        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");
        Connection currentConnection = null;
        try {
            currentConnection = DriverManager.getConnection(url, username, password);
        } catch (SQLException sqlException) {
            return null;
        }
        return currentConnection;
    }

    public synchronized boolean prepareFirstConnection() {
        boolean isLoaded = loadDriver(CURRENT_DATABASE_DRIVER_NAME);
        if (!isLoaded) return false;
        CURRENT_CONNECTION = establishConnectionUsingFileProperties(
                CURRENT_DATABASE_DRIVER_NAME, CURRENT_PROPERTIES_SOURCE);
        if (CURRENT_CONNECTION == null)
            return false;
        else return true;
    }

    public Connection getInstalledConnection() {
        return CURRENT_CONNECTION;
    }
}
