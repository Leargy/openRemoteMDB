package instructions.concrete.base.DBbaseCommands;

import base_modules.processors.processing_tasks.AuthenticationTask;
import communication.Report;
import entities.User;
import extension_modules.dbinteraction.TablesInteractor;
import extension_modules.dbinteraction.UsersTableInteractor;
import instructions.concrete.ConcreteDecree;
import instructions.rotten.Accessible;
import parameters.entities.UsersParameters;
import patterns.command.Receiver;

import java.nio.channels.SocketChannel;
import java.sql.SQLException;

public class SignUp extends ConcreteDecree{
    public final UsersTableInteractor USER_TABLE_INTERACTOR;
    public final AuthenticationTask AUTHENTICATION_TASK;
    //TODO: Написать реализацию signUp
    public static final String NAME = "sign_up";
    public static final String BRIEF = "Регистрация пользователя и последующая его авторизация.";
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
    public SignUp(Receiver sieve, UsersTableInteractor usersTableInteractor, AuthenticationTask authenticationTask) {
        super(sieve);
        USER_TABLE_INTERACTOR = usersTableInteractor;
        AUTHENTICATION_TASK = authenticationTask;
    }

    @Override
    public Report execute() {
        Report dbReport = null;
        try {
            dbReport = USER_TABLE_INTERACTOR.addNewUser(new UsersParameters(new String[] {userLogin,userPassword}));
        }catch (SQLException ex) {
            /*If DB method throws exception then only send the report of failure*/
//            return new Report(10, "Account with this \"login\" is already exist!\n");
            return new Report(10, "Аккаунт с таким логином уже существует!\n");
        }
//        Report report = new Report(0,"New account was registered.");
        Report report = new Report(0,"Аккаунт был успешно зарегестривован.");
        report.setIsConfirmed(true);
        AUTHENTICATION_TASK.addAuthorizedUser(userSocketChannel,new User(new UsersParameters(new String[]{userLogin, userPassword})));
        return report;
    }

    public void setTempUserParametrs(String login, String passWord, SocketChannel socketChannel) {
        this.userLogin = login;
        this.userPassword = passWord;
        this.userSocketChannel = socketChannel;
    }

    @Override
    public String toString() { return NAME; }
}
