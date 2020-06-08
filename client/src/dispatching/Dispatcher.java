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
import instructions.rotten.Accessible;
import instructions.rotten.RawDecree;
import instructions.rotten.base.RawExit;
import instructions.rotten.base.RawHelp;
import instructions.rotten.base.RawSignOut;

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
    private PassCheck passCheck;

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
    public void giveOrder(Segment parcel) {
        RawDecree tempCommand = null;
        try {
            tempCommand = dataHandler.handle(parcel);
        }catch(CommandSyntaxException e) {
            //exception will be thrown if entered command doesn't pass the verification.
            e.getMessage();
            System.out.println("For more information use \"help\" command.");
            return;
        }
        if(tempCommand instanceof RawExit) {
            try {
                byteArrayOutputStream.close();
                objectOutputStream.close();
            }catch (IOException ex) {
                new IOException("Dropped an exception during closing streams.", ex);
            }catch (NullPointerException ex) {/*NOP*/}
            parcel.setMarker(Markers.STOP);
            mediator.notify(this,parcel);
        }
        if (tempCommand instanceof Accessible) {
            if (tempCommand instanceof RawSignOut) {
                passCheck.setLogin("");
                passCheck.setPassword("");
            }else {
                passCheck.setLogin(((Accessible) tempCommand).getLogin());
                passCheck.setPassword(((Accessible) tempCommand).getPassword());
            }
        }
        parcel.setCommandData(tempCommand);
        send(parcel);
    }

    /**
     * Метод производящий сериализацию объекта и последующую его отправку.
     * @param parcel
     * @throws IOException
     */
    public void send(Segment parcel) {
        byteArrayOutputStream = new ByteArrayOutputStream();
        if (passCheck.isConfirmed() || parcel.getCommandData() instanceof Accessible || parcel.getCommandData() instanceof RawHelp) {
            try {
                objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                objectOutputStream.writeObject(parcel.prepareDataObject());
            } catch (IOException e) {/*NOPE*/}
            try {
                parcel.getSocketChannel().write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
            }catch (IOException e) {
                System.err.println("─────Server connection is interrupted─────");
                parcel.setMarker(Markers.INTERRUPTED);
                mediator.notify(this,parcel);
            }finally {
                try {
                    byteArrayOutputStream.close();
                    objectOutputStream.close();
                }catch (IOException e) { /*NOPE*/};
            }
        }else {
            System.err.println("You haven't authorised yet");
        }
        // TODO: Обработка разрыва подключения
    }

    // Method to set status of combination of Login and Pass (confirmed/rejected)
    // Which comes from receiver module.
    public void Confirm(boolean isConfirmed) {
        if (passCheck != null) {
            passCheck.setIsConfirmed(isConfirmed);
        }
    }
}
// Class to check if user has been confirmed on server.
// Also is used to collect Login and Password for sending in exchanging package
class PassCheck {
    private boolean isConfirmed = false;
    private String login = "";
    private String password = "";

    public void setIsConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }
    public boolean isConfirmed() {
        return isConfirmed;
    }
    public void setLogin(String login) { this.login = login; }
    public void setPassword(String password) { this.password = password; }
    public String getLogin() {
        return login;
    }
    public String getPassword() {
        return password;
    }
}