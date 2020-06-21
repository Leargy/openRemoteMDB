package parameters.entities;

import parameters.Parameters;

import java.util.UUID;

public final class UsersParameters implements Parameters {
    private final UUID USER_ID;
    private final String LOGIN;
    private final String PASSWORD;
    private final String ENVIRONMENT_VARIABLE_NAME;

    public UsersParameters(String login, String password, String environmentVariableName) {
        this(new String[]{login, password, environmentVariableName});
    }

    public UsersParameters(String[] params) {
        LOGIN = params[0];
        PASSWORD = params[1];
        ENVIRONMENT_VARIABLE_NAME = params[2];
        USER_ID = UUID.fromString(
                buildHexLineFromParameters(LOGIN, PASSWORD, ENVIRONMENT_VARIABLE_NAME));
    }

    public UsersParameters(String login, String password, String environmentVariableName, UUID id) {
        LOGIN = login;
        PASSWORD = password;
        ENVIRONMENT_VARIABLE_NAME = environmentVariableName;
        USER_ID = id;
    }

    private String buildHexLineFromParameters(String login, String pass, String envName) {
        StringBuilder hexNumber = new StringBuilder();
        hexNumber.append(getHexStringFromLine(login));
        hexNumber.append(getHexStringFromLine(pass));
        hexNumber.append(getHexStringFromLine(envName));
        return hexNumber.toString();
    }

    private String getHexStringFromLine(String line) { return Integer.toHexString(line.hashCode()); }

    public String getLogin() { return LOGIN; }

    public String getPassword() { return PASSWORD; }

    public String getEnvironmentVariableName() { return ENVIRONMENT_VARIABLE_NAME; }

    public UUID getID() { return USER_ID; }
}
