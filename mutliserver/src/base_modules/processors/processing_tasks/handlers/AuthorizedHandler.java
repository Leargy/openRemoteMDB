package base_modules.processors.processing_tasks.handlers;

import communication.ClientPackage;
import exceptions.NotAuthorizedException;
import instructions.rotten.RawDecree;
import instructions.rotten.base.RawSignIn;
import instructions.rotten.base.RawSignUp;

public class AuthorizedHandler implements Handling {
    @Override
    public ClientPackage handle(ClientPackage clientPackage) throws NotAuthorizedException {
        RawDecree tempCommand = clientPackage.getCommand();
        if (tempCommand instanceof RawSignUp || tempCommand instanceof RawSignIn){
            throw new NotAuthorizedException("You have already been authorized.");
        }else {
            return clientPackage;
        }
    }
}
