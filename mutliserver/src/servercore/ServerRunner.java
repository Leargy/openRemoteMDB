package servercore;

import testing_block.ParsingTest;

public class ServerRunner {
    public static void main(String[] args) {
        ParsingTest parsingTest = new ParsingTest();
        parsingTest.collectionLoadingTest();
        parsingTest.commandsTest();
//        Server.install();
//        Server.launch();
    }
}
