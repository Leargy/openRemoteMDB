package exceptions;

public class PartNotFoundException extends Exception {
    private String exceptionMessage;
    /**
     * Конструктор, принимающий контекст ошибки.
     */
    public PartNotFoundException(String exceptionMessage) {this.exceptionMessage = exceptionMessage;}
    /**
     * Метод, выводящий контекст ошибки.
     * @return String
     */
    public String getMessage() {
        System.err.println(exceptionMessage);
        return exceptionMessage;
    }
}
