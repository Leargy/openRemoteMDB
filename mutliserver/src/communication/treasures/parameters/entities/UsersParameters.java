package communication.treasures.parameters.entities;

public final class UsersParameters {
    private final String LOGIN;
    private final String PASSWORD;
    private final String ENVIRONMENT_VARIABLE_NAME;

    public UsersParameters(String login, String password, String environmentVariableName) {
        LOGIN = login;
        PASSWORD = password;
        ENVIRONMENT_VARIABLE_NAME = environmentVariableName;
    }

    public UsersParameters(String[] params) {
        LOGIN = params[0];
        PASSWORD = params[1];
        ENVIRONMENT_VARIABLE_NAME = params[2];
    }

    public String getLogin() { return LOGIN; }

    public String getPassword() { return PASSWORD; }

    public String getEnvironmentVariableName() { return ENVIRONMENT_VARIABLE_NAME; }
}
