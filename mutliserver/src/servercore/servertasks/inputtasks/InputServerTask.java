package servercore.servertasks.inputtasks;

import patterns.mediator.Component;
import patterns.mediator.Controllers;

import java.util.Scanner;

public class InputServerTask implements Runnable, Component {
    protected final Controllers SERVER_CONTROLLER;
    public InputServerTask(Controllers controllers) {
        SERVER_CONTROLLER = controllers;
    }
    @Override
    public void run() {
        Scanner sysAdminInputScanner = new Scanner(System.in);
        String sysAdminInput = "";
        do {
            System.out.print(">> ");
            sysAdminInput = sysAdminInputScanner.next();
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
