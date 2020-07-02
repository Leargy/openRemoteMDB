package communication;

import data_section.enumSection.Markers;
import instructions.rotten.RawDecree;

import java.nio.channels.SocketChannel;
import java.util.concurrent.Future;

/**
 * Класс, объект которого используются для перемещения информации между модулями
 * @author Come_1LL_F00 aka Lenar Khannanov
 * @author Leargy aka Anton Sushkevich
 */
//возможно логичнее было бы освободить этот класс от socketChannel и отправлять объекты этого класса,чтобы не плодить лишний класс.
public class Segment {
    private String[] stringData;
    private RawDecree commandData;
    private ClientPackage clientPackage;
    private SocketChannel socketChannel;
    private Markers marker;
    private String login;
    private String passWord;
    private Future futureTask;

    /**
     * @param marker
     */
    public Segment(Markers marker) { this.marker = marker; }

    /**
     * @param socketChannel
     * @param marker
     */
    public Segment(SocketChannel socketChannel, Markers marker) {
        this(marker);
        this.socketChannel = socketChannel;
    }

    public void setLogin(String login) { this.login = login; }
    public void setMarker(Markers marker) { this.marker = marker; }
    public void setPassWord(String passWord) { this.passWord = passWord; }
    public void setCommandData(RawDecree commandData) { this.commandData = commandData; }
    public void setStringData(final String[] stringData) { this.stringData = stringData; }
    public void setClientPackage(ClientPackage clientPackage) { this.clientPackage = clientPackage; }

    public SocketChannel getSocketChannel() { return this.socketChannel; }
    public ClientPackage getClientPackage() { return clientPackage; }
    public RawDecree getCommandData() { return commandData; }
    public String[] getStringData() { return stringData; }
    public String getPassWord() { return passWord; }
    public Markers getMarker() { return marker; }
    public String getLogin() { return login; }

    /**
     * Возвращает новый объект пакета, предназначенного для обмена информацией между клиентом и сервером.
     * В конструктор ClientPackage передается объект сырой команды.
     * @return ClientPackage
     */
    public ClientPackage prepareDataObject() { return new ClientPackage(commandData,login,passWord); }
}
