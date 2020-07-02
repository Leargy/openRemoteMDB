package parameters.server;

import organization.Parameters;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ServerParameters implements Parameters {
    protected static final String CORRECT_IP_PATTERN = "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
    protected static final int DEFAULT_PORT = 0xdead;
    protected static final int MIN_FREE_PORT = 50000;
    protected static String defaultIPAddress;
    static {
        try {
            defaultIPAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException unknownHostException) {
            // TODO: correct logging exception
            System.err.println(unknownHostException.getMessage());
            System.exit(1);
        }
    }

    private final int CURRENT_SERVER_PORT;
    private final String CURRENT_SERVER_IP;



    public ServerParameters(String address, int port) {
        CURRENT_SERVER_IP = checkIPv4Address(address)? address : defaultIPAddress;
        CURRENT_SERVER_PORT = checkFreePort(port)? port : DEFAULT_PORT;
    }

    public static boolean checkIPv4Address(String address) {
        return (address != null && !address.isEmpty() && address.matches(CORRECT_IP_PATTERN));
    }

    public static boolean checkFreePort(int port) {
        return (port < MIN_FREE_PORT)? false : true;
    }

    public String getIPAddress() { return CURRENT_SERVER_IP; }

    public int getPort() { return CURRENT_SERVER_PORT; }

    public static String getDefaultIPAddress() { return defaultIPAddress; }

    public static int getDefaultPort() { return DEFAULT_PORT; }
}
