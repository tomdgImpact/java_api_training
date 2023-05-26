package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class RequestHandle implements HttpHandler  {
    private final Game game;
    public RequestHandle(Game game){
        this.game = game;
    }

    public JSONObject getJsonRequest(HttpExchange exchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody());
        BufferedReader br = new BufferedReader(isr);
        String value = br.readLine();
        return new JSONObject(value);
    }

    public String sendPOSTResponse(HttpExchange exchange) throws IOException {
        final String response; Validator schemaValidator = new Validator();JSONObject json = getJsonRequest(exchange);
        String schema = "{\"$schema\": \"http://json-schema.org/schema#\",\"type\": \"object\",\"properties\": " +
            "{\"id\": {\"type\": \"string\"},\"url\": {\"type\": " +
            "\"string\"},\"message\": {\"type\": \"string\"}},\"required\": " +
            "[\"id\",\"url\",\"message\"]}";
        if(schemaValidator.schemaValidation(json.toString(), schema)){
            final int port = exchange.getHttpContext().getServer().getAddress().getPort();
            response = "{\"id\":\"0\", \"url\":\"http://localhost:" + port +
                "\", \"message\":\"Response from Server\"}";
            exchange.sendResponseHeaders(202, response.length());
        }
        else{ response = "Bad request"; exchange.sendResponseHeaders(400, response.length());}
        try (OutputStream os = exchange.getResponseBody()) {os.write(response.getBytes());}
        return json.getString("url");
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if(exchange.getRequestMethod().equals("POST")){
            String adversaryURL = sendPOSTResponse(exchange);
            System.out.println("The game starts!");
            game.getPlayer().getAdversaryURL()[0] =  adversaryURL;
            System.out.println("Enter a cell to target:");
            String cell = game.getPlayer().chooseCellToTarget();
            Fire fire = new Fire(game);
            try {
                String response = fire.sendFireRequest(adversaryURL, cell);
                fire.applyResponse(response, cell);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
