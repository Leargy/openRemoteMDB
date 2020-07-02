package sample.assets.exceptions;

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
        System.err.println(message);
        return message;
    }
}
