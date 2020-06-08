package communication;

import communication.treasures.Wealths;

import java.io.Serializable;

public class Report implements Wealths, Serializable {
    private final String MESSAGE;

    public Report(String message) { MESSAGE = message; }

    public String getMessage() { return MESSAGE; }
}
