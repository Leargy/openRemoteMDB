package parsing;

import instructions.concrete.ConcreteDecree;
import patterns.command.Invoker;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Абстракция Инвокера, содержащая
 * данные необходимые для вызова комманд
 * @author Come_1LL_F00 aka Lenar Khannanov
 * @author Leargy aka Anton Sushkevich
 * @see Invoker
 */
public abstract class Prompter implements Invoker {
  protected final Map<String, ConcreteDecree> availableCommands; // доступные к вызову команды
  protected final SortedSet<String> junkedCommands = new TreeSet<String>() {
          {
              add("insert");
              add("update");
              add("remove_lower");
              add("replace_if_lower");
              add("replace_if_greater");
          }
      };

  /**
   * Конструктор устанавливащий
   * this very необходимые данные,
   * что нужны для вызова комманд
   */
  protected Prompter() {
    availableCommands = new HashMap<>();
  }
}
