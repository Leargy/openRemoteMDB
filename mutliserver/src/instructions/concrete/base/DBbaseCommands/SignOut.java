package instructions.concrete.base.DBbaseCommands;

import base_modules.processors.processing_tasks.AuthenticationTask;
import communication.Report;
import entities.Organization;
import instructions.concrete.ConcreteDecree;
import instructions.rotten.RawCommitter;
import patterns.command.Receiver;

import java.nio.channels.SocketChannel;

public class SignOut extends ConcreteDecree {
    //TODO: Написать реализацию signOut
    public static final String NAME = "sign_out";
    public static final String BRIEF = "Деавторизация пользователя.";
    public static final String SYNTAX = NAME ;
    public static final int ARGNUM = 2;
    public final AuthenticationTask AUTHENTICATION_TASK;
    private SocketChannel userSocketChannel;

    /**
     * Конструктор, устанавливающий ссылку на
     * управленца коллекцией
     *
     * @param sieve текущий управленец коллекцией
     */
    public SignOut(Receiver sieve, AuthenticationTask authenticationTask) {
        super(sieve);
        this.AUTHENTICATION_TASK = authenticationTask;
    }

    @Override
    public Report execute() {
        AUTHENTICATION_TASK.removeAuthorizedUser(userSocketChannel);
        return new Report(0,"You successfully deauthorized.");
    }

    public void setTempUserParametrs(SocketChannel socketChannel) {
        this.userSocketChannel = socketChannel;
    }

    @Override
    public String toString() { return NAME; }
}
