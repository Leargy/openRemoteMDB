package instructions.rotten.base;

import instructions.rotten.RawDecree;

import java.io.Serializable;

/**
 * "Сырая" команда "info".
 * содержит основную информацию о команде.
 * выводит информацию о коллекции.
 *  @author Come_1LL_F00 aka Lenar Khannanov
 *  @author Leargy aka Anton Sushkevich
 */
public final class RawInfo extends RawDecree implements Serializable {
  public static final String NAME = "info";
  public static final String BRIEF = "выводит информацию о коллекции.";
  public static final String SYNTAX = NAME;
  public static final int ARGNUM = 0;
}
