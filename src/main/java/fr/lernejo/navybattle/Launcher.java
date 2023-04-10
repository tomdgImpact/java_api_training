package fr.lernejo.navybattle;

import java.io.IOException;

public class Launcher {
    public static void main(String[] args) throws IOException, InterruptedException {
        int port = Integer.parseInt(args[0]);
        Server server = new Server(port);
        if (args.length > 1) {
            new GameInstance().newGame(port, args[1]);
        }
    }
}
