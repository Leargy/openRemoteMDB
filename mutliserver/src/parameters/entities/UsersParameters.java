package parameters.entities;

import java.util.Random;
import java.util.UUID;

public final class UsersParameters {
    private final Integer ID;
    private final String LOGIN;
    private final String PASSWORD;
//    private final String ENVIRONMENT_VARIABLE_NAME;

    public UsersParameters(String login, String password) {
        this(new String[]{login, password});

    }

    public UsersParameters(String[] params) {
        LOGIN = params[0];
        PASSWORD = params[1];
//        ENVIRONMENT_VARIABLE_NAME = params[2];
       ID = Math.abs(PASSWORD.hashCode())/2;
    }

    public UsersParameters(String login, String password, Integer id) {
        LOGIN = login;
        PASSWORD = password;
//        ENVIRONMENT_VARIABLE_NAME = environmentVariableName;
        this.ID = id;
    }

    private String buildHexLineFromParameters(String login, String pass) {
        StringBuilder hexNumber = new StringBuilder();
        hexNumber.append(getHexStringFromLine(login));
        hexNumber.append(getHexStringFromLine(pass));
//        hexNumber.append(getHexStringFromLine(envName));
        return hexNumber.toString();
    }

    private String getHexStringFromLine(String line) { return Integer.toHexString(line.hashCode()); }

    public String getLogin() { return LOGIN; }

    public String getPassword() { return PASSWORD; }

//    public String getEnvironmentVariableName() { return ENVIRONMENT_VARIABLE_NAME; }

    public Integer getID() { return ID; }
}
