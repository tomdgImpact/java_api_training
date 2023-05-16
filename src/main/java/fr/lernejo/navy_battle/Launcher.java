package fr.lernejo.navy_battle;

import java.io.IOException;
import java.net.URISyntaxException;

public class Launcher {

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        if (args.length < 1 || args.length > 2) {
            System.out.println("Specify port number, the program needs exactly 1 argument");
            System.exit(1);
        }
        try {
            ServerSide serv = new ServerSide(args[0]);
            if (args.length == 2) {
                Client client = new Client(serv, args[1]);
                client.Connect();
            }
        } catch (IOException | URISyntaxException | InterruptedException e) {
            System.out.println("Not Integer, please specify a correct port number");
            throw e;
        }
    }
}
