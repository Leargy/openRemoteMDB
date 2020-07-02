package sample.assets.exceptions;

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
        System.err.println(message);
        return message;
    }
}
