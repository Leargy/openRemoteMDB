package parameters.server;

import parameters.Parameters;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Base parameters that contains
 * only server address and its port.
 * Can validate its parameters with static
 * methods called validate. To change server
 * parameters you must create new object or
 * invoke change methods.
 * @author come_ill_foo
 * @author Leargy
 */
public class BaseServerParameters implements Parameters {
    protected static final String CORRECT_IP_PATTERN = "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
    public static final int DEFAULT_PORT = 0xdead;
    protected static final int MIN_FREE_PORT = 48654;
    public static final String DEFAULT_IP_ADDRESS;
    static {
        String rawAddress = "";
        try {
            rawAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException unknownHostException) {
            System.out.println("Cannot get default IP address as localhost");
            rawAddress = "128.0.0.1";
        }
        DEFAULT_IP_ADDRESS = rawAddress;
    }

    public final String ADDRESS;
    public final int PORT;
    
    public BaseServerParameters(String address, int port) {
        ADDRESS = validateIPv4Address(address)? address : DEFAULT_IP_ADDRESS;
        PORT = validateFreePort(port)? port : DEFAULT_PORT;
    }

    public final static boolean validateIPv4Address(String address) { return (address == null || address.isEmpty() || !address.matches(CORRECT_IP_PATTERN))? false : true; }
    public final static boolean validateFreePort(int port) { return !(port < MIN_FREE_PORT); }

    public BaseServerParameters changeServerAddress(String address) {
        return changeServerParameters(address, PORT);
    }

    public BaseServerParameters changeServerPort(int port) {
        return changeServerParameters(ADDRESS, port);
    }

    public final BaseServerParameters changeServerParameters(String address, int port) {
        return new BaseServerParameters(address, port);
    }
}
