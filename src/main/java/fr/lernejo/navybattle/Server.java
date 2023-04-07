package fr.lernejo.navybattle;

import com.sun.net.httpserver.HttpsServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Server {
    final HttpsServer server;

    Server(int port) throws IOException {
        InetSocketAddress socket = new InetSocketAddress(port);
        server = HttpsServer.create(socket, 0);
        server.setExecutor(Executors.newSingleThreadExecutor());
        // Setup game endpoint
        server.createContext("/ping", new Ping());
        server.createContext("/api/game/start", new Start(port));
        server.createContext("/api/game/fire", new Fire());
        server.start();
    }

    public final void stop() {
        server.stop(0);
    }

}
