package sample.dialog_windows.handlers.exceptions;

public class CommonHandlerException extends Exception {
    private String message;

    /**
     * Конструктор, принимающий контекст ошибки.
     * @param message
     */
    public CommonHandlerException(String message) {
        this.message = message;
    }

    /**
     * Метод, выводящий контекст ошибки.
     * @return String
     */
    public String getMessage(){
        return message;
    }
}
