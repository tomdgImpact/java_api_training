package fr.lernejo.navybattle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class Start implements HttpHandler {
    public final int port;

    Start(int p) {
        port = p;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

    }
}
