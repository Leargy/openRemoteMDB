package servercore.servertasks.inputtasks;

import java.util.Scanner;

public class InputServerTask implements Runnable {
    @Override
    public void run() {
        Scanner sysAdminInputScanner = new Scanner("System.in");
        do {
            System.out.print(">> ");
            String sysAdminInput = sysAdminInputScanner.nextLine();
            if ("exit".equals(sysAdminInput)) {
                System.out.println("Entered the exit command");
                break;
            } else System.out.println("Entered command hasn't been recognised");
        } while (true);
        System.out.println("The exit command has been recognised");
        System.out.println("Shutdowning the server...");
        System.exit(0);
    }
}
