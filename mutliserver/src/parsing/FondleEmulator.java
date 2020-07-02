package parsing;

import instructions.concrete.ConcreteDecree;
import instructions.concrete.base.Exit;
import instructions.concrete.base.Save;
import patterns.mediator.Component;
import patterns.mediator.Controllers;

import java.util.ArrayList;
import java.util.List;

/**
 * Абстракция Invoker'a пригодного
 * работать в сети и взаимодействовать
 * с контроллером
 * @author Come_1LL_F00 aka Lenar Khannanov
 * @author Leargy aka Anton Sushkevich
 * @see Prompter
 * @see Component
 * @see Invoker
 */
public abstract class FondleEmulator extends Prompter implements Component {
  protected final Controllers MAGIV; // ссылка на SSPC
//  protected Shell fReader;

  /**
   * Конструктор устанавливающий контроллер
   * над эмулятором
   * @param controller ссылка на SSPC
   */
  public FondleEmulator(Controllers controller) { MAGIV = controller; }

  /**
   * Вместо скроллинга ленты в ВК,
   * скроллим список доступных комманд
   * @return список доступных комманд
   */
  public final List<ConcreteDecree> scroll() {
    List<ConcreteDecree> listing = new ArrayList<>();
    availableCommands
        .entrySet()
        .stream()
        .forEach((entry) -> {
          entry.getValue(); if (!(Exit.NAME.equals(entry.getKey()) || Save.NAME.equals(entry.getKey())))
            listing.add(entry.getValue());
        });
    return listing;
  }
}
