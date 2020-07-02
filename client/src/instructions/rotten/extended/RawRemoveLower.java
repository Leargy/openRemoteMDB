package instructions.rotten.extended;

import organization.Organization;
import instructions.rotten.IJunked;
import instructions.rotten.RawCommitter;

import java.io.Serializable;

/**
 * "Сырая" команда "remove_lover".
 * содержит основную информацию о команде.
 * удаляет из коллекции элементы, меньшие чем заданный.
 */
public final class RawRemoveLower extends RawCommitter implements IJunked, Serializable {
    public static final String NAME = "remove_lower";
    public static final String BRIEF = "Удаляет из коллекции элементы, меньшие чем заданный.";
    public static final String SYNTAX = NAME + " {element}";
    public static final int ARGNUM = 1;

    public RawRemoveLower(Organization organization) {
        super(organization);
    }
}
