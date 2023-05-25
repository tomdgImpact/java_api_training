package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class Request {
    final ServerSide server;

    public Request(ServerSide serv) {
        server = serv;
    }

    public void ReceiveFire(HttpExchange httpExchange, boolean bool) throws IOException {
        String pos = "";
        try {
            pos = httpExchange.getRequestURI().getQuery().split("pos=")[1];
            Game.ShootState state = server.gameBoard .ShootAction(pos);
            String format = String.format("{\"consequence\": \"%s\",\"shipLeft\": %s}", state.toString(), (server.gameBoard .board.size() > 0) && (server.gameBoard.inGame[0]));
            if (!bool) {
                httpExchange.getResponseHeaders().add("Content-Type", "application/json");
            }
            httpExchange.sendResponseHeaders(202, format.length());
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(format.getBytes());
            }
            server.gameBoard.Fire();
        } catch (Exception e) {
            server.formatHtml(httpExchange, 400);
        }
    }

    String RequestBody(HttpExchange httpExchange) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(httpExchange.getRequestBody(), StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder buildRequest = new StringBuilder(512);
        int tmp;
        while ((tmp = bufferedReader.read()) != -1) {
            buildRequest.append((char) tmp);
        }
        bufferedReader.close();
        inputStreamReader.close();
        return buildRequest.toString();
    }

    public void HandlerStart(HttpExchange httpExchange, boolean test) throws IOException, InterruptedException {
        try {
            RequestBody(httpExchange);
        } catch (Exception e) {
            server.formatHtml(httpExchange, 400);
        }
        String bodyresponse = String.format("{\"id\": \"%s\",\"url\": \"%s\",\"message\": \"OK\"}", server.serverID, server.url);
        httpExchange.sendResponseHeaders(202, bodyresponse.length());
        if (!test)
            httpExchange.getResponseHeaders().add("Content-Type", "application/json");

        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(bodyresponse.getBytes());
        }
        server.gameBoard.inGame[0] = true;
        server.gameBoard.Fire();
    }
}
