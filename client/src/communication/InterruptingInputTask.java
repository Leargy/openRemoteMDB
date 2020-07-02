package communication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

public class InterruptingInputTask implements Callable<String> {

    @Override
    public String call() throws Exception {
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(System.in));
        String input;
        do {
            System.out.print(">");
            try {
                // wait until we have data to complete a readLine()
                while (!bufferedReader.ready()) {
                    Thread.sleep(100);
                }
                input = bufferedReader.readLine();
            } catch (InterruptedException e) {
                return "";
            }
        } while ("".equals(input));
        return input;
    }
}
