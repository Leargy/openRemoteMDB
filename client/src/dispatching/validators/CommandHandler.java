package dispatching.validators;import communication_tools.Segment;import dataSection.Commands;import exceptions.CommandSyntaxException;import instructions.rotten.RawDecree;import instructions.rotten.base.*;import instructions.rotten.extended.*;import java.util.HashMap;import java.util.Map;import java.util.regex.Matcher;import java.util.regex.Pattern;/** * Звено проверки валидности введенной команды.В случае несовпадения - прерывает цепочку проверок. * @author Come_1LL_F00 aka Lenar Khannanov * @author Leargy aka Anton Sushkevich * @see DataHandler,ArgumentHandler */public class CommandHandler extends DataHandler {    private final HashMap<String,String> commandMap;    /**     * Конструктор принимающий список команд относительно которых будет производиться проверка.     * @param commandList     */    public CommandHandler(Commands commandList){        commandMap = commandList.getCommandMap();    }    /**     * Метод седержащий логику проверки строки на признак комманды.     * @param parcel     * @return RawDecree     * @throws CommandSyntaxException     */    @Override    public RawDecree handle(Segment parcel) throws CommandSyntaxException{        String tempCommand = parcel.getStringData()[0];        Pattern noCommandPattern = Pattern.compile(".+[\\s\\+.\\+]+");        Matcher matcher = null;        boolean isFound;        for(Map.Entry<String,String> entry : commandMap.entrySet()) {            matcher = noCommandPattern.matcher(entry.getValue());            isFound = matcher.find();            if (tempCommand.equals(entry.getKey()) && !isFound) {                if (parcel.getStringData().length > 1) {                    throw new CommandSyntaxException("This command mustn't have any argument!");                }                switch (entry.getKey()){                    case RawExit.NAME: return new RawExit();                    case RawHelp.NAME: return new RawHelp();                    case RawClear.NAME: return new RawClear();                    case RawShow.NAME: return new RawShow();                    case RawInfo.NAME: return new RawInfo();                    case RawSumOfAnnualTurnover.NAME: return new RawSumOfAnnualTurnover();                    case RawMaxByDate.NAME: return new RawMaxByDate();                }            }else if(tempCommand.equals(entry.getKey()) && isFound){                return super.handle(parcel);            }        }        throw new CommandSyntaxException("Entered command doesn't exist or has wrong syntax!");    }}