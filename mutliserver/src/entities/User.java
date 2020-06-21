package entities;

import parameters.entities.UsersParameters;

import java.util.UUID;

public final class User {
    private final UUID ID;
    private final String LOGIN;
    private final String PASSWORD;
    private final String ENVIRONMENT_VARIABLE_NAME;

    public User(UUID user_id, String login, String password, String environment_name) {
        this(new UsersParameters(login, password, environment_name, user_id));
    }

    public UUID getID() { return ID; }

    public String getLogin() {
        return LOGIN;
    }

    public String getPassword() {
        return PASSWORD;
    }

    public String getEnvironmentVariableName() {
        return ENVIRONMENT_VARIABLE_NAME;
    }

    public User(UsersParameters userParams) {
        ID =  userParams.getID();
        LOGIN = userParams.getLogin();
        PASSWORD = userParams.getPassword();
        ENVIRONMENT_VARIABLE_NAME = userParams.getEnvironmentVariableName();
    }
}
