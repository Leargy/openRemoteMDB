package patterns.command;

import communication.Report;

public interface Command {
    Report execute();
}
