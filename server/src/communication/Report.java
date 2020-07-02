package communication;

import java.io.Serializable;

/**
 * Класс отчетов, хранящих информацию о результатах,
 * работы сервера.
 * Может использоваться для:
 * <ul>
 *   <li>Хранения результатов обработки запросов</li>
 *   <li>Хранения результатов чтения запросов</li>
 *   <li>Хранения результатов попытки отправки результатов обработки запросов</li>
 * </ul>
 * Но по факту, используется лишь для отправки результатов
 * @author Come_1LL_F00 aka Lenar Khannanov
 * @author Leargy aka Anton Sushkevich
 */
public final class Report implements Serializable {
  // fields
  private final int ERROR_CODE; // Error code
  private final String MESSAGE; // Message
  private boolean isConfirmed;

  // builders

  // Main builder
  /**
   * Основной конструктор, устанавливающий
   * параметры отправляемого ответа
   * @param errorCode код ошибки
   * @param message передаваемое сообщение
   */
  public Report(int errorCode, String message) {
    ERROR_CODE = errorCode;
    MESSAGE = (message == null)? "" : message;
  }

  // properties and methods
  /**
   * Свойство, определяющее успешность
   * выполнения операции
   * @return успешно/неуспешно
   */
  public boolean isSuccessful() { return ERROR_CODE == 0; }

  /**
   * Метод взятия сообщения
   * @return строка с сообщением
   */
  public String Message() { return MESSAGE; }

  public boolean getIsConfirmed() {
    return isConfirmed;
  }
  public void setIsConfirmed(boolean isConfirmed) {
    this.isConfirmed = isConfirmed;
  }
}
