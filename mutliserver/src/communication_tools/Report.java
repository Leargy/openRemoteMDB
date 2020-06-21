package communication_tools;

import java.io.Serializable;

public class Report implements Serializable {
    private final int ERROR_CODE;
    private final String MESSAGE;

    public Report(String message) {
        MESSAGE = message;
        ERROR_CODE = 0;
    }

    public Report(int errorCode, String message) {
        ERROR_CODE = errorCode;
        MESSAGE = message;
    }

    public String getMessage() { return MESSAGE; }
    public int getErrorCode() { return ERROR_CODE; }

    public boolean isSuccessful() {
        return ERROR_CODE == 0;
    }
}
