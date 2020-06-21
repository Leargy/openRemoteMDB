package server_core.tasks.install_tasks;

import parameters.server.BaseServerParameters;
import server_core.configurators.ServerConfigurators;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public final class StdOutInstallServerTask extends InstallationTask {
    public StdOutInstallServerTask(InputStream dataSource, OutputStream channel, ServerConfigurators config) {
        super(dataSource, channel, config);
    }

    @Override
    public BaseServerParameters promptBaseServerParameters(InputStream in, OutputStream out) {
        System.out.println("Getting base server parameters like IP and port...");
        System.out.println("Prompting server address from source...");
        String serverAddress = promptServerAddress(in, out);
        System.out.println("Prompting server port from source...");
        int serverPort = promptServerPort(in, out);
        System.out.println("Finishing getting base parameters...");
        return new BaseServerParameters(serverAddress, serverPort);
    }

    @Override
    public String promptServerAddress(InputStream in, OutputStream out) {
        try (
                Scanner scan = new Scanner(in);
                Writer output = new PrintWriter(new OutputStreamWriter(out))
        ) {
            String rawAddress = "";
            int currentAttempts = MAX_INPUT_ATTEMPTS;
            do {
                // input cycle
                output.write("Enter correct IPv4 server address: ");
                output.flush();
                rawAddress = scan.nextLine();
                // validating data
                if (BaseServerParameters.validateIPv4Address(rawAddress))
                    return rawAddress;
                else {
                    output.write("Incorrect server address: remains attempts " + currentAttempts + "\n");
                    output.flush();
                }
            } while (currentAttempts-- >= 0);
            // setting default
            output.write("All attempts used, default IP address will be set\n");
            output.flush();
            return BaseServerParameters.DEFAULT_IP_ADDRESS;
        } catch (IOException ioException) {
            System.out.println("Cannot get server address from current streams\n");
            return BaseServerParameters.DEFAULT_IP_ADDRESS;
        }
    }

    @Override
    public int promptServerPort(InputStream in, OutputStream out) {
        try (
                Scanner scan = new Scanner(in);
                Writer output = new BufferedWriter(new OutputStreamWriter(out))
        ) {
            int rawPort = 0, currentAttempts = MAX_INPUT_ATTEMPTS;
            do {
                // input cycle
                output.write("Enter the correct server port: ");
                output.flush();
                try {
                    rawPort = Integer.valueOf(scan.nextLine());
                } catch (InputMismatchException inputMismatchException) {
                    output.write("Incorrect port pattern. There must be a number\n");
                    output.flush();
                }
                // validating data
                if (BaseServerParameters.validateFreePort(rawPort))
                    return rawPort;
                else {
                    output.write("Incorrect server port: remains attempts " + currentAttempts + "\n");
                    output.flush();
                }
            } while (currentAttempts-- >= 0);
            // setting default
            output.write("All attempts used, default port will be set\n");
            output.flush();
            return BaseServerParameters.DEFAULT_PORT;
        } catch (IOException ioException) {
            System.out.println("Cannot get server port from current streams\n");
            return BaseServerParameters.DEFAULT_PORT;
        }
    }
}
