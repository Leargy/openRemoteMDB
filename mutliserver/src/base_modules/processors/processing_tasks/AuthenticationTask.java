package base_modules.processors.processing_tasks;

import base_modules.processors.processing_tasks.handlers.AuthorizedHandler;
import base_modules.processors.processing_tasks.handlers.Handling;
import base_modules.processors.processing_tasks.handlers.NotAuthorizedHandler;
import communication.ClientPackage;
import entities.User;
import exceptions.NotAuthorizedException;
import patterns.mediator.Component;
import patterns.mediator.Controllers;
import uplink_bags.ClientPackBag;
import uplink_bags.RawDecreeBag;
import uplink_bags.TransportableBag;

import java.nio.channels.SocketChannel;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AuthenticationTask implements Component {
    private final Controllers SUB_PROCESS_CONTROLLER;
    private final Executor cachedTP = Executors.newCachedThreadPool();
    private final ConcurrentHashMap<SocketChannel, User> LOGGED_USERS; //the collection for authorized users
    private final Handling ALLOWED_NA_COMMAND_HANDLER;
    private final Handling ALLOWED_A_COMMAND_HANDLER;

    public AuthenticationTask(Controllers controller) {
        SUB_PROCESS_CONTROLLER = controller;
        LOGGED_USERS = new ConcurrentHashMap<>();
        ALLOWED_NA_COMMAND_HANDLER = new NotAuthorizedHandler();
        ALLOWED_A_COMMAND_HANDLER = new AuthorizedHandler();
    }

    public void identify(TransportableBag clientPackBag) {
//        Future<ConcreteDecree> recognizedCommand = cachedTP.execute(); // Use this thread to recognize the command
        ClientPackage tempClientPackage = ((ClientPackBag)clientPackBag).getClientPacket();
        SocketChannel tempClientSocket = ((ClientPackBag)clientPackBag).getChannel();
        if (LOGGED_USERS.containsKey(tempClientSocket)) {
            if (checkIdentificationData(LOGGED_USERS.get(tempClientSocket),tempClientPackage)) {
                //do something if user was identified
                try {
                    SUB_PROCESS_CONTROLLER.notify(this, new RawDecreeBag(tempClientSocket, ALLOWED_A_COMMAND_HANDLER.handle(tempClientPackage).getCommand(),LOGGED_USERS.get(tempClientSocket))); // make the command of the user
                }catch (NotAuthorizedException ex) {
                    //Catch the exception if user tried to use commands according to authorization
                    //TODO: send the report back to user
                    System.err.println(ex.getMessage());
                }
            }else {
                //do something if user wasn't identified
            }
        }else { //if user wasn't authorized
            try {
                SUB_PROCESS_CONTROLLER.notify(this,new RawDecreeBag(tempClientSocket, ALLOWED_NA_COMMAND_HANDLER.handle(tempClientPackage).getCommand(),null));
            }catch (NotAuthorizedException ex) {
                //Catch the exception if user tried to execute commands, forbidden for not authorized users
                //TODO: send the report back to user
                System.err.println(ex.getMessage());
            }
        }
    }

    //Returns "true" if authorized-user data is equals to temp-user data
    protected boolean checkIdentificationData(User userData, ClientPackage tempClientPackage) {
        if (userData.getLogin().equals(tempClientPackage.getLogin()) && userData.getPassword().equals(tempClientPackage.getPassWord())) {
            return true;
        }
        return false;
    }

    // Synchronized method to make "Happens-before"
    public synchronized void addAuthorizedUser(SocketChannel socketChannel, User user) {
        LOGGED_USERS.putIfAbsent(socketChannel, user);
    }
    public synchronized void removeAuthorizedUser(SocketChannel socketChannel) {
        LOGGED_USERS.remove(socketChannel);
    }
}
