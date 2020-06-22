package testing_block;

import base_modules.processors.SubProcessorController;
import czerkaloggers.customer.B_4D4_GE3;
import entities.*;
import instructions.concrete.base.*;
import instructions.concrete.extended.*;
import parsing.customer.bootstrapper.NakedCrateLoader;
import parsing.customer.distro.ShedBlock;
import parsing.customer.local.TotalCommander;
import systemcore.ServerController;

import static org.junit.Assert.*;

import java.util.List;

public class ParsingTest {
    private NakedCrateLoader nakedCrateLoader = new NakedCrateLoader();
    private ShedBlock totalCommander = new ShedBlock(nakedCrateLoader,new B_4D4_GE3(null));

    //test for loading collection
    public void collectionLoadingTest() {
        System.out.print("Testing collection loading: ");
        List<OrganizationWithUId> organizationsList = organizationsList = nakedCrateLoader.load();
        assertEquals(true, organizationsList != null);
        System.out.println("SUCCESS");
        totalCommander.DataRebase(organizationsList);
    }

    public void commandsTest() {
        OrganizationWithUId organizationWithUId = new OrganizationWithUId(new Organization("removers",new Coordinates(12, new Float(54)),123,"",2,OrganizationType.PUBLIC,new Address("suck",new Location(41,new Long(123),53))),"Nekit");
        Show showCommand = new Show(totalCommander);
        System.out.println(showCommand.execute().Message());
        Insert insertCommand = new Insert(totalCommander,13, organizationWithUId);
        System.out.println(insertCommand.execute().Message());
        //
        System.out.println(showCommand.execute().Message());
        //
        Clear clearCommand = new Clear(totalCommander,"Nekit");
//        System.out.println(clearCommand.execute().Message());
        //
        System.out.println(showCommand.execute().Message());
        //
        RemoveKey removeKeyCommand = new RemoveKey(totalCommander,30491);
        System.out.println(removeKeyCommand.execute().Message());
        //
        System.out.println(showCommand.execute().Message());
        //
        Update updateCommand = new Update(totalCommander,48297, organizationWithUId);
        updateCommand.execute();
        //
        System.out.println(showCommand.execute().Message());
        //
        FilterContainsName filterContainsNameCommand = new FilterContainsName(totalCommander,"gg");
        System.out.println(filterContainsNameCommand.execute().Message());
        //
        MaxByDate maxByDateCommander = new MaxByDate(totalCommander);
        System.out.println(maxByDateCommander.execute().Message());
        //
        RemoveLower removeLowerCommand = new RemoveLower(totalCommander,organizationWithUId);
        System.out.println(removeLowerCommand.execute().Message());
        //
//        ReplaceIfGreater replaceIfGreaterCommand = new ReplaceIfGreater(totalCommander, 35671, organizationWithUId);
//        System.out.println(replaceIfGreaterCommand.execute().Message());
        //
        ReplaceIfLower replaceIfLowerCommand = new ReplaceIfLower(totalCommander, 35671, organizationWithUId);
        System.out.println(replaceIfLowerCommand.execute().Message());
        //
        SumOfAnnualTurnover sumOfAnnualTurnoverCommand = new SumOfAnnualTurnover(totalCommander);
        System.out.println(showCommand.execute().Message());
        System.out.println(sumOfAnnualTurnoverCommand.execute().Message());

//        totalCommander.save();
    }
}
