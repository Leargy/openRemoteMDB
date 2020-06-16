package servercore.servertasks.installtasks;

import parameters.server.ConfiguredServerParameters;
import parameters.server.ServerParameters;
import servercore.Server;
import servercore.installers.ServerInstaller;

import java.util.Scanner;
import java.util.concurrent.Callable;

public class InstallServerTask implements Callable<Server> {
    protected static final int MAX_ATTEMPTS = 3;
    protected final ServerInstaller MAIN_INSTALLER;


    public InstallServerTask(ServerInstaller installer) {
        MAIN_INSTALLER = installer;
    }

    @Override
    public Server call() throws Exception {
        ServerParameters rawParameters = promptServerParameters();
        ConfiguredServerParameters configuredParameters = MAIN_INSTALLER.configureServer(rawParameters);
        return Server.getInstance(configuredParameters);
    }

    protected final ServerParameters promptServerParameters() {
        Scanner paramsScanner = new Scanner(System.in);
        String address = promptServerIP(MAX_ATTEMPTS, paramsScanner);
        int port = promptServerPort(MAX_ATTEMPTS, paramsScanner);
        return new ServerParameters(address, port);
    }

    protected final String promptServerIP(int attemptNumber, Scanner scanner) {
        String resultAddress = ServerParameters.getDefaultIPAddress();
        do {
            System.out.print("Input the correct server IPv4 address: ");
            String newIP = scanner.nextLine();
            if (!ServerParameters.checkIPv4Address(newIP))
                System.out.println("Incorrect IPv4 address, try again: remaining input attempts " + attemptNumber);
            else {
                resultAddress = newIP;
                break;
            }
        } while (attemptNumber-- <= 0);
        return resultAddress;
    }

    protected final int promptServerPort(int attemptNumber, Scanner scanner) {
        int resultServerPort = ServerParameters.getDefaultPort();
        do {
            System.out.print("Input the correct server Port: ");
            try {
                int newPort = Integer.valueOf(scanner.nextLine());
                if (!ServerParameters.checkFreePort(newPort)) throw new NumberFormatException();
                else {
                    resultServerPort = newPort;
                    break;
                }
            } catch (NumberFormatException numberFormatException) {
                System.out.println("Incorrect port number, try again: remaining input attempts " + attemptNumber);
            }
        } while (attemptNumber-- <= 0);
        return resultServerPort;
    }
}
