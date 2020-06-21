package patterns.command;

import communication_tools.Report;

public interface Command {
    Report execute();
}
