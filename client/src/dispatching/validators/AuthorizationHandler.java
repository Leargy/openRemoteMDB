package dispatching.validators;

import communication.Segment;
import data_section.Commands;
import data_section.Encrepted;
import data_section.Encryptor;
import dispatching.Dispatcher;
import dispatching.script_handler.ExecuteScript;
import entities.Descriptor;
import entities.organizationFactory.OrganizationBuilder;
import exceptions.CommandSyntaxException;
import instructions.rotten.RawDecree;
import instructions.rotten.base.*;
import instructions.rotten.extended.RawExecuteScript;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class AuthorizationHandler extends DataHandler {
    private final HashMap<String,String> commandMap;
    private volatile boolean isConfirmed = false;
    private Encrepted encryptor;
    private ExecuteScript executeScript;
    private Dispatcher dispatcher;
    private OrganizationBuilder organizationBuilder;
    private final Descriptor fileDescriptor;

    public AuthorizationHandler(Commands commandList) {
        commandMap = commandList.getCommandMap();
        encryptor = new Encryptor();
        fileDescriptor = new Descriptor();
    }
    public void setDispatcher(Dispatcher dispatcher) {
        organizationBuilder = new OrganizationBuilder();
        executeScript = new ExecuteScript(dispatcher,organizationBuilder);
    };

    @Override
    public RawDecree handle(Segment parcel) throws CommandSyntaxException {
//        System.out.println("in handling " + isConfirmed);
        if (isConfirmed) {
            return nextHandler.handle(parcel);
        }else {
            boolean isLimited = false;
            String tempCommand= parcel.getStringData()[0];
            Map.Entry<String, String> foundedCommand = null;
            try {
                foundedCommand = commandMap.entrySet().stream().filter((x) -> (x.getKey().equals(tempCommand))).findFirst().get();
            }catch (NoSuchElementException ex) {
                throw new CommandSyntaxException("Entered command doesn't exist or has wrong syntax!");
            }
            if (foundedCommand.getValue().matches(".*\\s*\\[(login|password)\\].*")) {
                isLimited = true;
            }
            if (isLimited) {
                String login;
                String password;
                try {
                    login = parcel.getStringData()[1];
                    password = encryptor.encrypt(parcel.getStringData()[2]);
                }catch (ArrayIndexOutOfBoundsException | NoSuchAlgorithmException ex) {
//                    if (ex instanceof NoSuchAlgorithmException ) throw new CommandSyntaxException("Problems in encrypting the password");
                    throw new CommandSyntaxException("Missed some arguments");
                }
                switch (tempCommand) {
                    case RawSignUp.NAME: return new RawSignUp(login, password);
                    case RawSignIn.NAME: return new RawSignIn(login, password);
                }
            }else {
                switch (tempCommand) {
                    case RawExit.NAME: return new RawExit();
                    case RawHelp.NAME: return new RawHelp();
//                    case RawExecuteScript.NAME:
//                        try{
////                        return new RawExecuteScript(fileDescriptor.discript(stringArgument));
//                            executeScript.read(fileDescriptor.discript(parcel.getStringData()[1]),parcel.getSocketChannel());
//                            return new RawExecuteScript(null);
//                        }catch (IOException ex) {
//                            throw new CommandSyntaxException(ex.getMessage());
//                        }
                }
            }
        }
        return null;
    }
    public synchronized void setIsConfirmed(boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }
    public synchronized void setServerKey(String key) { ((Encryptor)encryptor).setTempPaper(key);}
}
