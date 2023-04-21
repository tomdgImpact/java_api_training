package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ServerSide {
    public final Game game;
    public final String serverID;
    final int port;
    final HandlerRequests requests;
    public final String url;
    public final String[] saveUrl;
    public final HttpServer server;

    final HttpHandler ping = exchange -> {
        String httpBody = "OK";
        exchange.sendResponseHeaders(200, httpBody.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(httpBody.getBytes());
        }
    };

    public void htmlFormat(HttpExchange httpExchange, int error) throws IOException {
        String httpBody = String.format("<h1>%s</h1>", error);
        httpExchange.sendResponseHeaders(error, httpBody.length());
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(httpBody.getBytes());
        }
    }

    final HttpHandler start = new HttpHandler() {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            if (game.inGame[0] || !httpExchange.getRequestMethod().equals("POST")) {
                htmlFormat(httpExchange, 404);
                return;
            }
            try {
                requests.HandlerStart(httpExchange, false);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
    };

    final HttpHandler shoot = new HttpHandler() {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            if (!httpExchange.getRequestMethod().equals("GET")) {
                htmlFormat(httpExchange, 404);
                return;
            }
            requests.HandlerFire(httpExchange, false);
        }
    };


    final HttpHandler defaultHandler = httpExchange -> htmlFormat(httpExchange, 404);

    public ServerSide(String port) throws IOException {
        this.port = Integer.parseInt(port);
        url = String.format("http://localhost:%s", port);
        Executor thread = Executors.newFixedThreadPool(1);
        server = HttpServer.create(new InetSocketAddress(this.port), 50);
        server.setExecutor(thread);
        server.createContext("/ping", this.ping);
        server.createContext("/api/game/start", this.start);
        server.createContext("/api/game/fire", this.shoot);
        server.createContext("/", this.defaultHandler);
        this.serverID = UUID.randomUUID().toString();
        requests = new HandlerRequests(this);
        server.start();
        game = new Game(this);
        saveUrl = new String[]{""};
    }

}
