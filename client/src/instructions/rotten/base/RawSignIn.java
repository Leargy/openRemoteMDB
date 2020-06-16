package instructions.rotten.base;

import instructions.rotten.Accessible;
import instructions.rotten.RawDecree;

import java.io.Serializable;

public class RawSignIn extends RawDecree implements Accessible, Serializable {
    public static final String NAME = "sign_in";
    public static final String BRIEF = "Авторизация пользователя.";
    public static final String SYNTAX = NAME + " [login]"+" [password]";
    public static final int ARGNUM = 2;
    private String login;
    private String password;

    public RawSignIn(String login, String password) {
        this.login = login;
        this.password = password;
    }
    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
