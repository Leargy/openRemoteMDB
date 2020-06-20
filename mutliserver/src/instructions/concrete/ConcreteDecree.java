package instructions.concrete;

import communication.Report;
import communication.Valuable;
import instructions.Decree;
import patterns.command.Command;
import patterns.command.Receiver;

public class ConcreteDecree extends Decree implements Command, Valuable {
    protected final Receiver SIEVE;

    /**
     * Конструктор, устанавливающий ссылку на
     * управленца коллекцией
     * @param sieve текущий управленец коллекцией
     */
    public ConcreteDecree(Receiver sieve) { SIEVE = sieve; }
    @Override
    public Report execute() {
        return null;
    }
}
