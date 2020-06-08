package communication.uplinkbags;

import communication.treasures.Wealths;
import patterns.command.Command;

public class QueryBag implements ValuableBags {
    private final Command INSTRUCTION;

    public QueryBag(Command instruction) { INSTRUCTION = instruction; }


    public Command getInstruction() { return INSTRUCTION; }

    @Override
    public Wealths getContent() { return INSTRUCTION; }
}
