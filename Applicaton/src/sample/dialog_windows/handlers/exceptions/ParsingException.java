package sample.dialog_windows.handlers.exceptions;

public class ParsingException extends Exception {
    private String message;

    /**
     * Конструктор, принимающий контекст ошибки.
     * @param message
     */
    public ParsingException(String message) {
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
