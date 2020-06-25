package base_modules.processors.processing_tasks;

import base_modules.processors.processing_tasks.handlers.AuthorizedHandler;
import base_modules.processors.processing_tasks.handlers.Handling;
import base_modules.processors.processing_tasks.handlers.NotAuthorizedHandler;
import base_modules.readers.readertasks.ClientPackageRetrievingTask;
import communication.ClientPackage;
import communication.Report;
import entities.User;
import exceptions.NotAuthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import patterns.mediator.Component;
import patterns.mediator.Controllers;
import uplink_bags.ClientPackBag;
import uplink_bags.NotifyBag;
import uplink_bags.RawDecreeBag;
import uplink_bags.TransportableBag;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AuthenticationTask implements Component {
    private final Controllers SUB_PROCESS_CONTROLLER;
    private final Executor cachedTP = Executors.newCachedThreadPool();
    private final ConcurrentHashMap<SocketChannel, User> LOGGED_USERS; //the collection for authorized users
    private final Handling ALLOWED_NA_COMMAND_HANDLER;
    private final Handling ALLOWED_A_COMMAND_HANDLER;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    public final ExecutorService UsersAlarmThread = Executors.newCachedThreadPool();

    public AuthenticationTask(Controllers controller) {
        SUB_PROCESS_CONTROLLER = controller;
        LOGGED_USERS = new ConcurrentHashMap<>();
        ALLOWED_NA_COMMAND_HANDLER = new NotAuthorizedHandler();
        ALLOWED_A_COMMAND_HANDLER = new AuthorizedHandler();
    }

    public void identify(TransportableBag clientPackBag) {
        logger.info("User entered the authentication block");
//        Future<ConcreteDecree> recognizedCommand = cachedTP.execute(); // Use this thread to recognize the command
        ClientPackage tempClientPackage = ((ClientPackBag)clientPackBag).getClientPacket();
        SocketChannel tempClientSocket = ((ClientPackBag)clientPackBag).getChannel();
        if (LOGGED_USERS.containsKey(tempClientSocket)) {
            if (checkIdentificationData(LOGGED_USERS.get(tempClientSocket),tempClientPackage)) {
                //do something if user was identified
                try {
                    SUB_PROCESS_CONTROLLER.notify(this, new RawDecreeBag(tempClientSocket, ALLOWED_A_COMMAND_HANDLER.handle(tempClientPackage).getCommand(),LOGGED_USERS.get(tempClientSocket))); // make the command of the user
                }catch (NotAuthorizedException ex) {
                    logger.error(ex.getMessage());
                    //Catch the exception if user tried to use commands according to authorization
//                    System.err.println(ex.getMessage());
                    SUB_PROCESS_CONTROLLER.notify(this,new NotifyBag(tempClientSocket, new Report(66, ex.toString())));
                }
            }else {
                //do something if user wasn't identified
            }
        }else{ //if user wasn't authorized
            try {
            for (Map.Entry<SocketChannel, User> tempEntry : LOGGED_USERS.entrySet()) {
                if (tempEntry.getValue().getLogin().equals(tempClientPackage.getLogin())) throw new NotAuthorizedException("Этот аккаунт уже используется!");
            }
                SUB_PROCESS_CONTROLLER.notify(this,new RawDecreeBag(tempClientSocket, ALLOWED_NA_COMMAND_HANDLER.handle(tempClientPackage).getCommand(),null));
            }catch (NotAuthorizedException ex) {
                logger.error(ex.getMessage());
                //Catch the exception if user tried to execute commands, forbidden for not authorized users
//                System.err.println(ex.getMessage());
                SUB_PROCESS_CONTROLLER.notify(this,new NotifyBag(tempClientSocket, new Report(66, ex.toString())));
            }
        }
    }

    //Returns "true" if authorized-user data is equals to temp-user data
    protected boolean checkIdentificationData(User userData, ClientPackage tempClientPackage) {
        if (userData.getLogin().equals(tempClientPackage.getLogin()) && userData.getPassword().equals(tempClientPackage.getPassWord())) {
            logger.info("Provided data from client package was confirmed");
            return true;
        }
        logger.error("Provided data from client package wasn't confirmed");
        return false;
    }

    // Synchronized method to make "Happens-before"
    public synchronized void addAuthorizedUser(SocketChannel socketChannel, User user) {
        for (Map.Entry<SocketChannel, User> tempEntry : LOGGED_USERS.entrySet()) {
            UsersAlarmThread.submit(() -> {
//                SUB_PROCESS_CONTROLLER.notify(this, new NotifyBag(tempEntry.getKey(),new Report(0,tempEntry.getValue().getLogin() + " connected to the server!")));
                SUB_PROCESS_CONTROLLER.notify(this, new NotifyBag(tempEntry.getKey(),new Report(0,tempEntry.getValue().getLogin() + " зашел на сервер!")));
            });
        }
        LOGGED_USERS.putIfAbsent(socketChannel, user);
        logger.info("User " + user.getLogin() + " was added to \"authorized\" list ");
    }
    public synchronized void removeAuthorizedUser(SocketChannel socketChannel) {
        try {
            String disconnectedUserLogin = LOGGED_USERS.get(socketChannel).getLogin();
            logger.info("User " + disconnectedUserLogin + " was deleted from \"authorized\" list");
            LOGGED_USERS.remove(socketChannel);
            for (Map.Entry<SocketChannel, User> tempEntry : LOGGED_USERS.entrySet()) {
                UsersAlarmThread.submit(() -> {
//                    SUB_PROCESS_CONTROLLER.notify(this, new NotifyBag(tempEntry.getKey(),new Report(0, tempEntry.getValue().getLogin() + " disconnected from the server!")));
                    SUB_PROCESS_CONTROLLER.notify(this, new NotifyBag(tempEntry.getKey(),new Report(0, tempEntry.getValue().getLogin() + " вышел с сервера!")));
            });
            }
        }catch (NullPointerException ex) {/*NOPE*/}
    }
    public synchronized void killExistConnections() {
        for (Map.Entry<SocketChannel,User> tempVictim : LOGGED_USERS.entrySet()) {
            try {
                tempVictim.getKey().socket().close();
            }catch (IOException ex) {
                logger.error("Unabled to close socket: " +tempVictim.getKey().socket().toString());
//                System.out.println("Unabled to close socket: " +tempVictim.getKey().socket().toString());
            }
        }
    }
}
