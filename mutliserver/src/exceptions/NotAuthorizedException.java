package exceptions;

public class NotAuthorizedException extends Exception {
    private final String exceptionMessage;
    public NotAuthorizedException(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public String getMessage() {
        return exceptionMessage;
    }
    @Override
    public String toString() {
        return exceptionMessage;
    }
}
