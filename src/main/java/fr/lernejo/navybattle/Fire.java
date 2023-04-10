package fr.lernejo.navybattle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class Fire implements HttpHandler {
    final Grid gameMap;

    Fire() {
        gameMap = new Grid();
    }

    private void NotFoundMethod(HttpExchange exchange) throws IOException {
        String error = "Error while trying to parse method, give a correct method";
        exchange.sendResponseHeaders(404, error.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(error.getBytes());
        }
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equals("GET"))
            NotFoundMethod(exchange);
        var jsonParsing = Parser(exchange);
        if (jsonParsing != null) {
            int getBoatStat = CheckBoatStat(jsonParsing);
        }
    }

    private int CheckBoatStat(FireJsonHandler jsonParsing) {
        if (gameMap.grid[jsonParsing.x][jsonParsing.y] == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    private FireJsonHandler Parser(HttpExchange exchange) throws IOException{
        String param = exchange.getRequestURI().getQuery();
        final int len = param.substring(param.indexOf("=") + 1).length();
        if (len < 2) {
            SendResponse(exchange, "Wrong Param", 404);
            return null;
        }
        final int num = param.substring(param.indexOf("=") + 1).charAt(1) - '0';
        if (len > 3 || (len == 3 && !param.substring(param.indexOf("=") + 2).equals("10"))
            || param.substring(param.indexOf("=") + 1).charAt(0) > 'J'
            || num == 0 || num > 10) {
            SendResponse(exchange, "Wrong Param", 404);
            return null;
        } else {
            return new FireJsonHandler(param.substring(param.indexOf("=") + 1));
        }
    }

    private void SendResponse(HttpExchange exchange, String message, int httpCode) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(httpCode, message.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(message.getBytes());
        }
    }
}
