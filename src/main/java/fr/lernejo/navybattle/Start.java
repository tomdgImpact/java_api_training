package fr.lernejo.navybattle;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class Start implements HttpHandler {
    private final StringBuilder url = new StringBuilder();
    public final int port;

    Start(int p) {
        port = p;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equals("POST")) {
            methodNotFound(exchange);
        } else {
            StartJson reqJson = parseBody(exchange);
            if (reqJson == null || reqJson.message.equals("\"\"")
                || reqJson.id.equals("\"\"") || reqJson.url.equals("\"\""))
                sendResponse(exchange, "Bad Json", 400);
            else {
                sendResponse(exchange, "{\n\t\"id\":\"" + UUID.randomUUID() + "\",\n\t\"url\":\""
                    + this.url + "\",\n\t\"message\":\"May the best code win\"\n}", 202);
            }
            var beginGame = new StartGame(reqJson, reqJson);
            try {
                beginGame.Launch();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendResponse(HttpExchange exchange, String badJson, int httpCode) throws IOException {
        exchange.sendResponseHeaders(httpCode, badJson.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(badJson.getBytes());
        }
    }

    private StartJson parseBody(HttpExchange exchange) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        StartJson req = null;
        String input = streamToString(exchange.getRequestBody());
        if (input.isBlank())
            return null;
        else {
            try {
                req = mapper.readValue(input, StartJson.class);
            } catch (IllegalArgumentException e) {
                exchange.sendResponseHeaders(400, "Bad request".length());
                throw new IllegalArgumentException();
            } catch (JsonMappingException e) {
                exchange.sendResponseHeaders(400, "Bad request".length());
                throw e;
            }
        }
        return req;
    }

    private String streamToString(InputStream requestBody) throws IOException {
        StringBuilder builds = new StringBuilder();
        int read = 0;
        while ((read = requestBody.read()) > 0){
            builds.append((char)read);
        }
        return builds.toString();
    }

    private void methodNotFound(HttpExchange exchange) throws IOException {
        String err = "not found";
        exchange.sendResponseHeaders(404, err.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(err.getBytes());
        }
    }
}
