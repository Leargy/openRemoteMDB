package instructions.concrete.extended;

import communication.Report;
import instructions.concrete.ConcreteDecree;
import patterns.command.Receiver;

import java.util.ArrayList;

/**
 * Команда исполнения скрипта из
 * каталога scripts
 */
public final class ExecuteScript extends ConcreteDecree {
  private final ArrayList<String> scriptString;
  /**
   * Конструктор, устанавливающий ссылку на
   * управленца коллекцией
   * @param sieve текущий управленец коллекцией
   */
  public ExecuteScript(Receiver receiver,ArrayList<String> scriptString) {
    super(receiver);
    this.scriptString = scriptString;
  }

  public ArrayList<String> getScriptParts() { return scriptString; }

  /**
   * Общий метод исполнения для всех исполняемых комманд
   */
  @Override
  public Report execute() { return new Report(-1,null); }

  public static final String NAME = "execute_script";
  public static final String BRIEF = "исполняет скрипт по указанному имени файла";
  public static final String SYNTAX = NAME + " [file_name]";
  public static final int ARGNUM = 1;

  @Override
  public String toString() { return NAME; }
}
