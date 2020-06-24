package base_modules.processors.processing_tasks.handlers;

import communication.ClientPackage;
import exceptions.NotAuthorizedException;
import instructions.rotten.Accessible;
import instructions.rotten.RawDecree;
import instructions.rotten.base.RawHelp;
import instructions.rotten.base.RawNotAuthorizedHelp;
import instructions.rotten.base.RawSignIn;
import instructions.rotten.base.RawSignUp;

public class NotAuthorizedHandler implements Handling{
    @Override
    public ClientPackage handle(ClientPackage clientPackage) throws NotAuthorizedException{
        RawDecree tempRawCommand = clientPackage.getCommand();

        if (tempRawCommand instanceof Accessible) {
            if (tempRawCommand instanceof RawSignIn) {
                return clientPackage;
            }else if (tempRawCommand instanceof RawSignUp) {
                return clientPackage;
            }
        }else if (tempRawCommand instanceof RawHelp) {
            return new ClientPackage(new RawNotAuthorizedHelp(),clientPackage.getLogin(), clientPackage.getPassWord());
        }
//        throw new NotAuthorizedException("User wasn't authorized to execute this command: " + tempRawCommand.getClass());
        throw new NotAuthorizedException("Пользователь не авторезированн, чтобы выполнить команду: " + tempRawCommand.getClass());
    }
}
