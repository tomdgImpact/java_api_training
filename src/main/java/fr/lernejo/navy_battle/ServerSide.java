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
    public final Game gameBoard;
    public final String serverID;
    public final String[] endPoints;
    public final HttpServer server;
    final Request request;
    public final String url;
    final int portNum;

    final HttpHandler ping = exchange -> {
        String body = "OK";
        exchange.sendResponseHeaders(200, body.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(body.getBytes());
        }
    };

    public void formatHtml(HttpExchange httpExchange, int error) throws IOException {
        String body = String.format("<h1>%s</h1>", error);
        httpExchange.sendResponseHeaders(error, body.length());
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(body.getBytes());
        }
    }

    final HttpHandler startServer = new HttpHandler() {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            if (gameBoard .inGame[0] || !httpExchange.getRequestMethod().equals("POST")) {
                formatHtml(httpExchange, 404);
                return;
            }
            try {
                request.HandlerStart(httpExchange, false);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
    };

    final HttpHandler shoot = new HttpHandler() {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            if (!httpExchange.getRequestMethod().equals("GET")) {
                formatHtml(httpExchange, 404);
                return;
            }
            request.ReceiveFire(httpExchange, false);
        }
    };

    final HttpHandler defaultVal = httpExchange -> formatHtml(httpExchange, 404);

    public ServerSide(String port) throws IOException {
        portNum = Integer.parseInt(port);
        InetSocketAddress socket = new InetSocketAddress(portNum);
        server = HttpServer.create(socket, 0); // use default backlog
        Executor execServer = Executors.newFixedThreadPool(1);
        server.setExecutor(execServer);
        server.createContext("/ping", ping);
        server.createContext("/api/game/start", startServer);
        server.createContext("/api/game/fire", shoot);
        server.createContext("/", defaultVal);
        this.serverID = UUID.randomUUID().toString();
        request = new Request(this);
        server.start();
        gameBoard  = new Game(this);
        endPoints = new String[]{""};
        url = String.format("http://localhost:%s", portNum);
    }
}
