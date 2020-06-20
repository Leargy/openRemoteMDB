package base_modules.processors.processing_tasks.handlers;

import communication.ClientPackage;
import exceptions.NotAuthorizedException;

public interface Handling {
    ClientPackage handle(ClientPackage clientPackage) throws NotAuthorizedException;
}
