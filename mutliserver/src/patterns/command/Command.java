package patterns.command;

import communication.Report;

import java.io.Serializable;

@FunctionalInterface
public interface Command extends Serializable {
    Report execute();
}
