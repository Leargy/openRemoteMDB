package extension_modules.dbinteraction;

import communication.Report;
import communication.ReportsFormatter;
import extension_modules.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;

public final class DataBaseConnector {
    private static final String DATABASE_DRIVER_NAME = "org.postgresql.Driver";
    private static final String DEFAULT_PROPERTIES = "database.properties";
    private final Logger LOG = LoggerFactory.getLogger(DataBaseConnector.class);
    private volatile Connection CURRENT_CONNECTION = null;
    private static volatile DataBaseConnector instance;

    public static DataBaseConnector getInstance() {
        if (instance == null)
            synchronized (DataBaseConnector.class) {
                if (instance == null)
                    instance = new DataBaseConnector();
            }
        return instance;
    }

    private DataBaseConnector() { }

    public Report prepareConnection2DB() {
        LOG.info("Trying load db driver");
        try {
            Class.forName(DATABASE_DRIVER_NAME);
        } catch (ClassNotFoundException classNotFoundException) {
            LOG.error("Not found drivers for database usages");
            return ReportsFormatter.makeUpUnsuccessReport(
                    ClassUtils.retrieveExecutedMethod() + " finding DB driver");
        }
        LOG.info("DB driver loaded successfully");
        return ReportsFormatter.makeUpSuccessReport(
                ClassUtils.retrieveExecutedMethod());
    }

    private Connection getConnectionUsingFProperties(String filename) {
        Path userDir = Paths.get(System.getProperty("user.dir"));
        Path propertiesWay = userDir.resolve(filename);
        Properties props = new Properties();
        LOG.info("Trying get properties from file " + filename);
        try (InputStream in = Files.newInputStream(propertiesWay)) {
            props.load(in);
        } catch (IOException ioException) {
            LOG.error("Cannot connect to db because of unable reading properties file");
            return null;
        }
        LOG.info("File properties loaded");
        String drivers = props.getProperty("jdbc.drivers");
        if (drivers != null) System.setProperty("jdbc.drivers", drivers);
        else System.setProperty("jdbc.drivers", DATABASE_DRIVER_NAME);
        String host = props.getProperty("jdbc.host");
        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");
        Connection currentConnection = null;
        LOG.info("Trying connection to database");
        try {
            currentConnection = DriverManager.getConnection(url, username, password);
        } catch (SQLException sqlException) {
            LOG.error("Happened errors while get connection to database");
        }
        LOG.info("Connection installed successfully");
        return currentConnection;
    }

    public Report connect2DBUsingProperties(String filename) {
        Connection connection = getConnectionUsingFProperties(filename);
        if (connection == null)
            return ReportsFormatter.makeUpUnsuccessReport(ClassUtils.retrieveExecutedMethod() + " connection isn't set");
        CURRENT_CONNECTION = connection;
        return ReportsFormatter.makeUpSuccessReport(ClassUtils.retrieveExecutedMethod() + " connection is set");
    }

    public Report dropDatabase() {
        if (CURRENT_CONNECTION == null) {
            LOG.info("Sorry, but there are no DBs to drop");
            return ReportsFormatter.makeUpSuccessReport(ClassUtils.retrieveExecutedMethod() + " closing connection");
        } else try {
            CURRENT_CONNECTION.close();
        } catch (SQLException sqlException) {
            LOG.error("Cannot close current connection");
            return ReportsFormatter.makeUpUnsuccessReport(ClassUtils.retrieveExecutedMethod() + " closing connection");
        }
        return ReportsFormatter.makeUpSuccessReport(ClassUtils.retrieveExecutedMethod() + " closing connection");
    }

    public Connection retrieveCurrentConnection() {
        if (CURRENT_CONNECTION == null)
            return CURRENT_CONNECTION = getConnectionUsingFProperties(DEFAULT_PROPERTIES);
        else return CURRENT_CONNECTION;
    }

}
