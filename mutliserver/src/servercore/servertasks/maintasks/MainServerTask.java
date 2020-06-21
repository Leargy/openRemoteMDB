package servercore.servertasks.maintasks;

import parameters.server.ConfiguredServerParameters;
import patterns.mediator.Component;
import patterns.mediator.Controllers;
import servercore.ServerController;
import uplink_bags.RegistrationBag;
import uplink_bags.TransportableBag;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainServerTask implements Runnable, Component {
    private  ConfiguredServerParameters CSP ;
    private final ServerController SERVER_CONTROLLER;
    private ExecutorService fixedTP = Executors.newFixedThreadPool(2);

    public MainServerTask (ConfiguredServerParameters CSP, ServerController serverController) {
        this.SERVER_CONTROLLER = serverController;
        this.CSP = CSP;
    }

    @Override
    public void run() {
        System.out.println("You can shutdown server by typing \"exit\" in console at any time");
        while (!CSP.getServerChannel().socket().isClosed()) { //this part was slowed down because of the heavy load on processors
            try {
                Thread.sleep(100);
            }catch (InterruptedException ex) {
            }
//              System.out.println(Thread.currentThread().getName() + " mew in mainServer");
                fixedTP.submit(() -> SERVER_CONTROLLER.notify(this, new RegistrationBag(CSP.getServerChannel())));
        }
        // TODO: write base logic with selector and processing users queries
    }
}
