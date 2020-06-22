package dispatching.validators;

import communication.Segment;
import data_section.Commands;
import data_section.Encrepted;
import data_section.Encryptor;
import exceptions.CommandSyntaxException;
import instructions.rotten.RawDecree;
import instructions.rotten.base.*;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class AuthorizationHandler extends DataHandler {
    private final HashMap<String,String> commandMap;
    private boolean isConfirmed = false;
    private Encrepted encryptor;

    public AuthorizationHandler(Commands commandList) {
        commandMap = commandList.getCommandMap();
        encryptor = new Encryptor();
    }

    @Override
    public RawDecree handle(Segment parcel) throws CommandSyntaxException {
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
                    throw new CommandSyntaxException("Missed some command's arguments");
                }
                switch (tempCommand) {
                    case RawSignUp.NAME: return new RawSignUp(login, password);
                    case RawSignIn.NAME: return new RawSignIn(login, password);
                    case RawSignOut.NAME: return new RawSignOut();
                }
            }else {
                switch (tempCommand) {
                    case RawExit.NAME: return new RawExit();
                    case RawHelp.NAME: return new RawHelp();
                }
            }
        }
        return null;
    }
    public void setIsConfirmed(boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }
}
