package communication;

import java.io.Serializable;

public class Report implements Serializable {
    private final String MESSAGE;

    public Report(String message) { MESSAGE = message; }

    public String getMessage() { return MESSAGE; }
}
