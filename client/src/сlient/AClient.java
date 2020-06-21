package сlient;

import communication_tools.Mediating;

/**
 * Абстрактный класс модуля установления/завершение подключений, определения вектора взаимодействия с сервером или клиентом.
 */
public abstract class AClient {
    protected static Mediating mediator;

    /**
     * конструктор, принимающий объект посредника между модулям.
     * @param mediator
     */
    public AClient(Mediating mediator){
        this.mediator = mediator;
    }
}
