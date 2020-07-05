package data_section;

import instructions.rotten.base.*;
import instructions.rotten.extended.*;

import java.util.HashMap;

/**
 * Класс содержащий в себе все доступные для клиента команды.
 * @author Leargy aka Anton Sushkevich
 * @author Come_1LL_F00 aka Lenar Khannanov
 */
public class CommandList implements Commands {
    private final HashMap<String,String> commandMap = new HashMap();

    /**
     * Конструктор в котором происходи добавление сырых команд в список доступных.
     */
    public CommandList() {
        commandMap.put(RawExit.NAME, RawExit.SYNTAX);
        commandMap.put(RawShow.NAME, RawShow.SYNTAX);
        commandMap.put(RawHelp.NAME, RawHelp.SYNTAX);
        commandMap.put(RawInfo.NAME, RawInfo.SYNTAX);
        commandMap.put(RawClear.NAME, RawClear.SYNTAX);
        commandMap.put(RawInsert.NAME, RawInsert.SYNTAX);
        commandMap.put(RawUpdate.NAME, RawUpdate.SYNTAX);
        commandMap.put(RawSignUp.NAME, RawSignUp.SYNTAX);
        commandMap.put(RawSignIn.NAME, RawSignIn.SYNTAX);
        commandMap.put(RawSignOut.NAME, RawSignOut.SYNTAX);
        commandMap.put(RawRemoveKey.NAME, RawRemoveKey.SYNTAX);
        commandMap.put(RawMaxByDate.NAME, RawMaxByDate.SYNTAX);
        commandMap.put(RawRemoveLower.NAME, RawRemoveLower.SYNTAX);
        commandMap.put(RawInsertByStep.NAME, RawInsertByStep.SYNTAX);
        commandMap.put(RawUpdateByStep.NAME, RawUpdateByStep.SYNTAX);
        commandMap.put(RawExecuteScript.NAME, RawExecuteScript.SYNTAX);
        commandMap.put(RawReplaceIfLower.NAME, RawReplaceIfLower.SYNTAX);
        commandMap.put(RawReplaceIfGreater.NAME, RawReplaceIfGreater.SYNTAX);
        commandMap.put(RawFilterContainsName.NAME, RawFilterContainsName.SYNTAX);
        commandMap.put(RawSumOfAnnualTurnover.NAME, RawSumOfAnnualTurnover.SYNTAX);
    }

    /**
     * Метод возвращающий проинициализированный список комманд.
     * @return HashMap<String,RawDecree>
     */
    public HashMap<String,String> getCommandMap() { return commandMap;}
}
