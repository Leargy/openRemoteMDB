package server_core;

public class ServerRunner {
    public static void main(String[] args) {
        Server myServer = Server.install();
        myServer.launch();
    }
}