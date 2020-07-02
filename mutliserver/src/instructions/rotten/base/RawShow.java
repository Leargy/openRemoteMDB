package instructions.rotten.base;

import instructions.rotten.RawDecree;

import java.io.Serializable;

public final class RawShow extends RawDecree implements Serializable {
    public static final String NAME = "show";
    public static final String SYNTAX = NAME;
    public static final String BRIEF = "Выводит все элементы коллекции в stdout";
    public static final int ARGNUM = 0;
}
