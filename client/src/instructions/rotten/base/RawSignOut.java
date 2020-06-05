package instructions.rotten.base;

import instructions.rotten.Accessible;
import instructions.rotten.RawDecree;

import java.io.Serializable;

public class RawSignOut extends RawDecree implements Accessible, Serializable {
    public static final String NAME = "sign_out";
    public static final String BRIEF = "Деавторизация пользователя.";
    public static final String SYNTAX = NAME + " [login]"+" [password]";
    public static final int ARGNUM = 2;
    private String login;
    private String password;

    @Override
    public String getLogin() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }
}
