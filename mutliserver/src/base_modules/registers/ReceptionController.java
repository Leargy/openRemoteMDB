package base_modules.registers;

import extension_modules.dbinteraction.DBInterConnector;
import base_modules.readers.BasePerusal;
import base_modules.readers.Perusals;
import communication.Report;
import communication.treasures.parameters.entities.UsersParameters;
import communication.uplinkbags.PassBag;
import communication.uplinkbags.ValuableBags;
import patterns.mediator.Component;
import patterns.mediator.Controllers;

import java.io.IOException;
import java.nio.channels.*;

public class ReceptionController implements Registers {
    private final UsersDataKeeper USERS_DATA_KEEPER = new UsersDataKeeper(this, new DBInterConnector());
    private final Controllers CONTROLLER;
    private final Perusals PERUSAL;

    public ReceptionController(Controllers controller) {
        CONTROLLER = controller;
        PERUSAL = new BasePerusal(this);
    }


    @Override
    public Report register(PassBag clientData) {
        ServerSocketChannel serverChannel = clientData.getChannel();
        Selector selector = clientData.getSelector();
        // 1 stage: connecting
        System.out.println("Trying connect new client");
        SocketChannel connectedClientChannel = null;
        try {
            connectedClientChannel = connectClientChannel(serverChannel);
        } catch (IOException ioException) {
            System.err.println("Failed client connection");
            return new Report("Failed client connection");
        }
        System.out.println("Client channel connection is successful");

        // 2 stage: channel configuration
        System.out.println("Trying configure the client channel");
        SocketChannel configuredClientChannel = null;
        try {
            configuredClientChannel = configureClientChannel(connectedClientChannel);
        } catch (IOException ioException) {
            System.err.println("Failed client channel configuration");
            return new Report("Failed client channel configuration");
        }
        System.out.println("Client channel configuration is successful");

        // 3 stage: registering client to selector
        System.out.println("Trying to register client channel to selector");
        try {
            registerClientChannel2Selector(configuredClientChannel, selector);
        } catch (ClosedChannelException closedChannelException) {
            System.err.println("Sorry, but client channel was closed");
            return new Report("Sorry, but client channel was closed");
        }
        System.out.println("Client channel registration is successful");

        // 4 stage: reading user data

        return new Report("Client was successfuly connected");
    }

    @Override
    public Controllers getController() {
        return CONTROLLER;
    }

    protected final SocketChannel connectClientChannel(ServerSocketChannel serverChannel) throws IOException { return serverChannel.accept(); }

    protected final SocketChannel configureClientChannel(SocketChannel connectedChannel) throws IOException {
        return (SocketChannel) connectedChannel.configureBlocking(false);
    }

    protected final void registerClientChannel2Selector(SocketChannel configuredChannel, Selector selector) throws ClosedChannelException {
        configuredChannel.register(selector, SelectionKey.OP_READ);
    }

    protected final UsersParameters retrieveUserParameters(SocketChannel readableClientChannel) {
        return PERUSAL.retrieveUserParameters(readableClientChannel);
    }

    @Override
    public Report notify(Component sender, ValuableBags parcel) {
        return new Report("Reception doesn't have any components yet");
    }
}
