package uplink_bags;

import instructions.concrete.ConcreteDecree;

import java.nio.channels.SocketChannel;

/**
 * Пакет исполнения, хранящий
 * клиентский канал и команду,
 * что клиента заказал
 * @author Come_1LL_F00 aka Lenar Khannanov
 * @author Leargy aka Anton Sushkevich
 * @see TunnelBag
 */
public final class ExecuteBag implements TransportableBag {
  private final ConcreteDecree CONCRETE_DECREE;
  private final SocketChannel CLIENT_SOCKET_CHANNEL;
  /**
   * Конструктор принимающий
   * наш this very k-nal и команду,
   * требующую выполнения
   * @param channel клиентский или серверный канал
   * @param decree конкретно передаваемая команда
   */
  public ExecuteBag(SocketChannel clientChannel, ConcreteDecree decree) {
    CLIENT_SOCKET_CHANNEL = clientChannel;
    CONCRETE_DECREE = decree;
  }

  /**
   * Свойство взятия канал
   * пользователя
   * @return канал клиента
   */
  public SocketChannel Channel() { return CLIENT_SOCKET_CHANNEL; }

  /**
   * Свойство взятия команды
   * на исполнение
   * @return конкретная команда
   */
  public ConcreteDecree getConcreteDecree() { return CONCRETE_DECREE; }
}
