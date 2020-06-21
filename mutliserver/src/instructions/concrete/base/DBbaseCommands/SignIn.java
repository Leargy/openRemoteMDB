package instructions.concrete.base.DBbaseCommands;

import base_modules.processors.processing_tasks.AuthenticationTask;
import com.sun.org.apache.regexp.internal.RE;
import communication.Report;
import entities.User;
import extension_modules.dbinteraction.UsersTableInteractor;
import instructions.concrete.ConcreteDecree;
import parameters.entities.UsersParameters;
import patterns.command.Receiver;

import java.nio.channels.SocketChannel;
import java.sql.SQLException;

public class SignIn extends ConcreteDecree {
    public final UsersTableInteractor USER_TABLE_INTERACTOR;
    public final AuthenticationTask AUTHENTICATION_TASK;
    //TODO: Написать реализацию signIn
    public static final String NAME = "sign_in";
    public static final String BRIEF = "Авторизация пользователя.";
    public static final String SYNTAX = NAME + " [login]"+" [password]";
    public static final int ARGNUM = 2;
    private String userLogin;
    private String userPassword;
    private SocketChannel userSocketChannel;

    /**
     * Конструктор, устанавливающий ссылку на
     * управленца коллекцией
     *
     * @param sieve текущий управленец коллекцией
     */
    public SignIn(Receiver sieve, UsersTableInteractor usersTableInteractor, AuthenticationTask authenticationTask) {
        super(sieve);
        USER_TABLE_INTERACTOR = usersTableInteractor;
        AUTHENTICATION_TASK = authenticationTask;
    }

    @Override
    public Report execute() {
        try {
            USER_TABLE_INTERACTOR.selectUserFromParameters(new UsersParameters(new String[] {userLogin,userPassword}));
        }catch (SQLException ex) {
            /*If DB method throws exception then only send the report of failure*/
            Report report = new Report(10, "\"Login\" or \"Password\" is incorrect!");
            report.setIsConfirmed(false);
            return report;
        }
        AUTHENTICATION_TASK.addAuthorizedUser(userSocketChannel,new User(new UsersParameters(new String[]{userLogin, userPassword})));
        Report report = new Report(0,"You successfully entered under " + "\"" + userLogin + "\".");
        report.setIsConfirmed(true);
        return report;
    }

    public void setTempUserParametrs(String login, String passWord, SocketChannel socketChannel) {
        this.userLogin = login;
        this.userPassword = passWord;
        this.userSocketChannel = socketChannel;
    }
}
