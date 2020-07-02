package extension_modules.dbinteraction;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
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
    private volatile int LOCAL_PORT = 5000;
    //TODO:Организовать хранение коллекции в реляционной СУБД (PostgresQL). Убрать хранение коллекции в файле. АГА СОСУНОК, ГДЕ ГОВОРИШЬ ХРОНИШЬ КОЛЛЕКЦИЮ

    public static DataBaseConnector getInstance() {
        if (instance == null)
            synchronized (DataBaseConnector.class) {
                if (instance == null)
                    instance = new DataBaseConnector();
            }
        return instance;
    }

    public int setLocalPort(int port) {
        return LOCAL_PORT = port;
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
//        props.put("StrictHostKeyChecking", "no");
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
        LOG.info("Trying loading jdbc driver");
        try {
            Class.forName(drivers);
        } catch (ClassNotFoundException classNotFoundException) {
            LOG.error("JDBC driver not found");
        }
        LOG.info("JDBC driver successfully found");
        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");
        LOG.info("Trying installing ssh connection");
        JSch jsch = new JSch();
        Session currentSession = null;
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        try {
            currentSession = jsch.getSession("s284733", "se.ifmo.ru", 2222);
            currentSession.setPassword("nsh845");
            currentSession.setConfig(config);
            currentSession.connect();
            currentSession.setPortForwardingL(5000, "pg", 5432);
        } catch (JSchException ex) {
            System.out.println(ex.getCause().getMessage());
            LOG.error("Unable to create secure session");
            return null;
        }
        LOG.info("Successful secure session opening");
        Connection currentConnection = null;
        LOG.info("Trying connection to database");
        try {
            currentConnection = DriverManager.getConnection("jdbc:postgresql://localhost" + ":" + 5000 + "/studs", "s284733", "nsh845");
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getCause().getMessage());
            LOG.error("Happened errors while get connection to database");
            return currentConnection;
        }
        LOG.info("Connection installed successfully");
        return currentConnection;
    }

    public Report connect2DBUsingProperties(String filename) {
        if (CURRENT_CONNECTION != null)
            return ReportsFormatter.makeUpSuccessReport(ClassUtils.retrieveExecutedMethod());
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
