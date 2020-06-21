package dispatching;

import dataSection.Commands;
import dataSection.enumSection.Markers;
import communication.Mediating;
import communication.Segment;
import dataSection.CommandList;
import dispatching.validators.AuthorizationHandler;
import exceptions.CommandSyntaxException;
import dispatching.validators.ArgumentHandler;
import dispatching.validators.CommandHandler;
import dispatching.validators.Handler;
import exceptions.ScriptHandlerException;
import instructions.rotten.Accessible;
import instructions.rotten.RawDecree;
import instructions.rotten.base.RawExit;
import instructions.rotten.base.RawHelp;
import instructions.rotten.base.RawSignIn;
import instructions.rotten.base.RawSignOut;

import javax.script.ScriptException;
import java.io.*;
import java.nio.ByteBuffer;

/**
 * Модуль отправки пакета данных на сервер.
 * @author Come_1LL_F00 aka Lenar Khannanov
 * @author Leargy aka Anton Sushkevich
 */
public class Dispatcher extends ADispatcher {
    private final Handler dataHandler;
    private Mediating mediator;
    private Commands commandList = new CommandList();
    private ByteArrayOutputStream byteArrayOutputStream ;
    private ObjectOutputStream objectOutputStream;
    private volatile PassCheck passCheck;
    private volatile boolean scriptOrder = false;

    /**
     * конструктор, пренимающий ссылку на посредника.
     * @param mediator посредник
     */
    public Dispatcher(Mediating mediator){
        this.mediator = mediator;
        //Initialising the handling chain.
        AuthorizationHandler authorizationHandler = new AuthorizationHandler(commandList);
        CommandHandler commandHandler = new CommandHandler(commandList);
        ArgumentHandler argumentHandler = new ArgumentHandler(commandList);
        commandHandler.setNext(argumentHandler);
        authorizationHandler.setNext(commandHandler);

        argumentHandler.setDispatcher(this);

        dataHandler = authorizationHandler;
        //Local class to check if user has been confirmed.
        passCheck = new PassCheck();
    }

    /**
     * метод в котором полученное сообщение от клиента отправляется на проверку
     * в случии валидности возвращается объект сырой команды,
     * заполненный необходимый для ее иполнения информацией.
     * @param parcel объект необходимый для пересылки информации между модулями.
     * @throws IOException
     */
    public synchronized void giveOrder(Segment parcel) {
        if (!scriptOrder) {
            RawDecree tempCommand = null;
            try {
                tempCommand = dataHandler.handle(parcel);
            } catch (CommandSyntaxException ex) {
                //exception will be thrown if entered command doesn't pass the verification.
                ex.getMessage();
                System.out.println("For more information use \"help\" command.");
                mediator.notify(this, new Segment(Markers.BADINPUTCONDITION));
                return;
            }

            if (tempCommand instanceof RawExit) {
                try {
                    byteArrayOutputStream.close();
                    objectOutputStream.close();
                } catch (IOException ex) {
                    new IOException("Dropped an exception during closing streams.", ex);
                } catch (NullPointerException ex) {/*NOP*/}
                parcel.setMarker(Markers.STOP);
                mediator.notify(this, parcel);
            }
            if (tempCommand instanceof Accessible) {
                if (tempCommand instanceof RawSignOut) {
                    if (!passCheck.isConfirmed()) {
                        System.err.println("You haven't authorised yet");
                        mediator.notify(this, new Segment(Markers.BADINPUTCONDITION));
                        return;
                    }else {
                        ((RawSignOut)tempCommand).setDeauthorizationData(passCheck.getLogin(),passCheck.getPassword());
                        passCheck.setIsConfirmed(false);
                        passCheck.setLogin("");
                        passCheck.setPassword("");
                    }
                }
                else if (tempCommand instanceof RawSignIn){
                    passCheck.setLogin(((Accessible) tempCommand).getLogin());
                    passCheck.setPassword(((Accessible) tempCommand).getPassword());
                }
            }
            if (tempCommand == null) {
                System.err.println("You haven't authorised yet");
                mediator.notify(this, new Segment(Markers.BADINPUTCONDITION));
                return;
            }
            parcel.setCommandData(tempCommand);
        }
        send(parcel);
    }

    /**
     * Метод производящий сериализацию объекта и последующую его отправку.
     * @param parcel
     * @throws IOException
     */
    public synchronized void send(Segment parcel) {
        byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            Thread.sleep(100);
        }catch (InterruptedException ex) {/*NOPE*/}

        if (passCheck.isConfirmed() || parcel.getCommandData() instanceof Accessible || parcel.getCommandData() instanceof RawHelp) {
            mediator.notify(this,new Segment(Markers.GOODINPUTCONDITION));
            parcel.setLogin(passCheck.getLogin());
            parcel.setPassWord(passCheck.getPassword());
            try {
                objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                objectOutputStream.writeObject(parcel.prepareDataObject());
            } catch (IOException ex) {/*NOPE*/}
            try {
                parcel.getSocketChannel().write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
            }catch (IOException ex) {
                System.out.println(ex.getMessage());
                parcel.setMarker(Markers.INTERRUPTED);
                mediator.notify(this,parcel);
            }finally {
                try {
                    byteArrayOutputStream.close();
                    objectOutputStream.close();
                }catch (IOException ex) { /*NOPE*/};
            }
        }else {
            System.err.println("You haven't authorised yet");
            mediator.notify(this, new Segment(Markers.BADINPUTCONDITION));
            return;
        }
    }

    // Method to set status of combination of Login and Pass (confirmed/rejected)
    // Which comes from receiver module.
    public synchronized void Confirm(boolean isConfirmed) {
        if (passCheck != null) {
            ((AuthorizationHandler)dataHandler).setIsConfirmed(isConfirmed);
            passCheck.setIsConfirmed(isConfirmed);
        }
    }
    public synchronized boolean switchOrder() {
        if (!scriptOrder) {
            return scriptOrder = true;
        }
        else {
            return scriptOrder = false;
        }
    }
}
// Class to check if user has been confirmed on server.
// Also is used to collect Login and Password for sending in exchanging package
class PassCheck {
    private volatile boolean isConfirmed = false;
    private volatile String login = "";
    private volatile String password = "";
    public synchronized void setIsConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }
    public synchronized boolean isConfirmed() {
        return isConfirmed;
    }
    public String getLogin() { return login; }
    public String getPassword() { return password; }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}