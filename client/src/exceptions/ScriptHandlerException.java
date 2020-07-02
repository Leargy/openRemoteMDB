package exceptions;

public class ScriptHandlerException extends Exception{
    private String exceptionMessage;
    /**
     * Конструктор, принимающий отчет ошибки.
     * @param message
     */
    public ScriptHandlerException(String exceptionMessage) {this.exceptionMessage = exceptionMessage;}
    /**
     * Метод, выводящий контекст ошибки.
     * @return String
     */
    public String getMessage() {
        System.err.println(exceptionMessage);
        return exceptionMessage;
    }

}
