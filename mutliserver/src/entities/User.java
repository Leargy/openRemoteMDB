package entities;

import com.sun.istack.internal.NotNull;
import parameters.entities.UsersParameters;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.UUID;

public final class User {
    private final UUID ID;
    private final String LOGIN;
    private final String PASSWORD;
//    private final String ENVIRONMENT_VARIABLE_NAME;

    public UUID getID() { return ID; }

    public String getLogin() {
        return LOGIN;
    }

    public String getPassword() {
        return PASSWORD;
    }

//    public String getEnvironmentVariableName() {
//        return ENVIRONMENT_VARIABLE_NAME;
//    }

    public User(UsersParameters userParams) {
        ID =  userParams.getID();
        LOGIN = userParams.getLogin();
        PASSWORD = userParams.getPassword();
//        ENVIRONMENT_VARIABLE_NAME = userParams.getEnvironmentVariableName();
    }
}
