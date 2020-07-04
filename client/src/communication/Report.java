package communication;

import organization.OrganizationWithUId;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Класс отчетов, хранящих информацию о результатах,
 * работы сервера.
 * Может использоваться для:
 * <ul>
 *   <li>Хранения результатов обработки запросов</li>
 *   <li>Хранения результатов чтения запросов</li>
 *   <li>Хранения результатов попытки отправки результатов обработки запросов</li>
 * </ul>
 */
public class Report implements Serializable {
    private final int ERROR_CODE; // Error code
    private final String MESSAGE; // Message
    private Boolean isConfirmed;
    private ArrayList<OrganizationWithUId> organizations;

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
    public Boolean getIsConfirmed() {
        return isConfirmed;
    }
    public void setIsConfirmed(boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    public ArrayList<OrganizationWithUId> getOrganizations() { return organizations; }
    public void setOrganizations(ArrayList<OrganizationWithUId> organizations) {
        this.organizations = organizations;
    }
}
