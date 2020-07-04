package dispatching.validators;

import communication.Segment;
import data_section.Commands;
import dispatching.Dispatcher;
import dispatching.script_handler.ExecuteScript;
import entities.Descriptor;
import entities.JunkerCreator;
import entities.organizationFactory.OrganizationBuilder;
import exceptions.CommandSyntaxException;
import exceptions.PartNotFoundException;
import instructions.rotten.base.*;
import instructions.rotten.extended.*;
import instructions.rotten.RawDecree;
import organization.Organization;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Звено проверки аргументов команд.Реализация паттерна "Цепочка обязанностей" (Chain of Responsibility)
 * @author Come_1LL_F00 aka Lenar Khannanov
 * @author Leargy aka Anton Sushkevich
 */
public class ArgumentHandler extends DataHandler{
    private final HashMap<String,String> commandMap;
    private final Descriptor fileDescriptor;
    private ExecuteScript executeScript;
    private Dispatcher dispatcher;
    private OrganizationBuilder organizationBuilder;
    /**
     * Конструктор принимающий список команд относительно которых будет производиться проверка.
     * @param commandList
     */
    public ArgumentHandler(Commands commandList){
        commandMap = commandList.getCommandMap();
        junkerCreator = new JunkerCreator();
        fileDescriptor = new Descriptor();
    }

    public void setDispatcher(Dispatcher dispatcher) {
        organizationBuilder = new OrganizationBuilder();
        executeScript = new ExecuteScript(dispatcher,organizationBuilder);
    };

    /**
     * Метод седержащий логику проверки аргументной части команды.
     * @param parcel
     * @return RawDecree
     * @throws CommandSyntaxException
     */
    @Override
    public RawDecree handle(Segment parcel) throws CommandSyntaxException{
        String tempCommand = parcel.getStringData()[0];

        boolean isLimited = false;
        Map.Entry<String,String> foundedCommand = commandMap.entrySet().stream().filter((a) -> (a.getKey().equals(tempCommand))).findFirst().get();
        if (foundedCommand.getValue().matches(".*\\s*\\[(key|id)\\].*")) {
            isLimited = true;
        }
        if (tempCommand.equals(RawRemoveLower.NAME)) {
            return new RawRemoveLower(organizationBuilder.make(junkerCreator.prepareJunker()));
        }
        String stringArgument = "";
        try {
            if (parcel.getStringData()[1] != null)
                for (int i = 1; i < parcel.getStringData().length; i++) {
                    stringArgument += parcel.getStringData()[i] ;
                    if (i != parcel.getStringData().length - 1) {
                        stringArgument += " ";
                    }
                }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new CommandSyntaxException("Command should have at list one argument!");
        }
        if (isLimited) {
            Integer intArgument = null;
            try {
                intArgument = Integer.valueOf(stringArgument.split(" ")[0]);
                if (intArgument < 0) throw new NumberFormatException();
                if (parcel.getStringData().length > 3) {
                    throw new CommandSyntaxException("Argument should be only one number!");
                }
            } catch (NumberFormatException e) {
                throw new CommandSyntaxException("Entered argument should be one positive integer!");
            }
            switch (foundedCommand.getKey()) {
                case RawRemoveKey.NAME: return new RawRemoveKey(intArgument);
                case RawInsertByStep.NAME:{
                    try {
                        Organization organization = organizationBuilder.makeWithParsing(parcel.getStringData()[2]);
                        return new RawInsert(intArgument, organization);
                    }catch (PartNotFoundException ex) {
                        throw new CommandSyntaxException(ex.getMessage());
                    }
                }
                case RawInsert.NAME: return new RawInsert(intArgument, organizationBuilder.make(junkerCreator.prepareJunker()));
                case RawUpdate.NAME: return new RawUpdate(intArgument, organizationBuilder.make(junkerCreator.prepareJunker()));
                case RawReplaceIfLower.NAME: return new RawReplaceIfLower(intArgument, organizationBuilder.make(junkerCreator.prepareJunker()));
                case RawReplaceIfGreater.NAME: return new RawReplaceIfGreater(intArgument, organizationBuilder.make(junkerCreator.prepareJunker()));
                default: return new RawExecuteScript(null);
            }
        }else {
            switch (foundedCommand.getKey()) {
                case RawExecuteScript.NAME:
                    try{
//                        return new RawExecuteScript(fileDescriptor.discript(stringArgument));
                        executeScript.read(fileDescriptor.discript(stringArgument),parcel.getSocketChannel());
                        return new RawExecuteScript(null);
                    }catch (IOException ex) {
                        throw new CommandSyntaxException(ex.getMessage());
                    }
                case RawFilterContainsName.NAME: return new RawFilterContainsName(stringArgument);
                default: return new RawExecuteScript(null);
                //case RawRemoveLower.NAME: return new RawRemoveLower(junkerCreator.prepareJunker());
            }
        }
    }
}
