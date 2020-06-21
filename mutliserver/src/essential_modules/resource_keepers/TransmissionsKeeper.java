package essential_modules.resource_keepers;

import org.postgresql.core.SqlCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server_core.tasks.service_tasks.ServiceTask;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class TransmissionsKeeper extends ResourceKeeper {
    private final Logger LOG = LoggerFactory.getLogger(TransmissionsKeeper.class);
    private Connection DATABASES_CONNECTION;
    private ServerSocket SERVER;
    private ServiceTask SERVICING;
    private final Map<InetAddress, Socket> CLIENTS = new ConcurrentHashMap<>();

    public boolean setNewDataBaseConnection(Connection shutdownable) {
        if (shutdownable == null) return false;
        try {
            if (shutdownable.isClosed()) {
                LOG.warn("Set shutdownable cannot be closed");
                return false;
            } else {
                DATABASES_CONNECTION = shutdownable;
                LOG.info("Successful setting new connection");
                return true;
            }
        } catch (SQLException sqlException) {
            LOG.error("Cannot define connection status");
            return false;
        }
    }

    public boolean setNewServer(ServerSocket shutdownable) {
        if (shutdownable == null || shutdownable.isClosed()) {
            LOG.warn("Set server can't be closed or null");
            return false;
        } else {
            LOG.info("Server socket set for shutdown successfully");
            SERVER = shutdownable;
            return true;
        }
    }

    public boolean addNewClient(InetAddress key, Socket value) {
        if (key == null || value == null)
            return false;
        else if (value.isClosed())
            return false;
        else {
            CLIENTS.put(key, value);
            return true;
        }
    }

    public int disconnectAllClients() {
        int[] number = new int[]{0};
        CLIENTS.forEach(
                (key, value)->{
                    try {
                        if (!value.isClosed()) value.close();
                    } catch (IOException ioException) {
                        LOG.error("Unable to close socket with address: " + key.getHostAddress());
                    }
                    number[0]++;
                    CLIENTS.remove(key);
                });
        return number[0];
    }

    public int getCurrentClientsNumber() { return CLIENTS.size(); }

    public boolean disconnectClientByAddress(InetAddress address) {
            Socket client = CLIENTS.remove(address);
            if (client == null) {
                LOG.warn("There are no clients with such address: " + address.getHostAddress());
                return false;
            } else {
                try {
                    client.close();
                    LOG.info("Successful client disconnection; [address = " + address.getHostAddress() + "]");
                    return true;
                } catch (IOException ioException) {
                    LOG.error("Unable to disconnect client with such: " + address.getHostAddress());
                    return false;
                }
            }
    }

    public boolean shutdownServer() {
        if (SERVER == null) {
            return SERVICING.shutdownServicing();
        } else {
            if (SERVER.isClosed()) return true;
            try {
                SERVER.close();
            } catch (IOException ioException) {
                LOG.error("Errors occurred while closing server socket");
            }
            return SERVER.isClosed() && SERVICING.shutdownServicing();
        }
    }

    public boolean disconnectFromDatabase() {
        if (DATABASES_CONNECTION == null)
            return true;
        else {
            try {
                if (DATABASES_CONNECTION.isClosed()) return true;
                DATABASES_CONNECTION.close();
            } catch (SQLException sqlException) {
                LOG.error("Errors occurred while closing connection to database");
            }
            try {
                return DATABASES_CONNECTION.isClosed();
            } catch (SQLException sqlException) {
                LOG.error("Errors occurred while checking database connection status");
                return false;
            }
        }
    }

    public boolean setServiceTask(ServiceTask servicing) {
        SERVICING = servicing;
        return true;
    }
}
