package fr.lernejo.navy_battle;

import java.io.IOException;
import java.net.URISyntaxException;
public class Launcher {
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        ServerSide server;
        if (args.length < 1) {
            System.out.println("Specify the port for connection");
            System.exit(1);
        }
        else if (args.length > 2) {
            System.out.println("Too much arguments, please only specify the port for connection");
            System.exit(1);
        }
        try {
            server = new ServerSide(args[0]);
            if (args.length == 2) {
                ClientHandler client = new ClientHandler(server, args[1]);
                client.clientConnect();
            }
        } catch (IOException | URISyntaxException | InterruptedException e) {
            System.out.println("Not an Integer");
            throw e;
        }
    }
}
