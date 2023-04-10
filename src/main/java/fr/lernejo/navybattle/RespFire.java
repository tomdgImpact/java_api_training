package fr.lernejo.navybattle;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RespFire {
    public void fire(HttpExchange exchange) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String param = exchange.getRequestURI().getQuery();
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create("http://localhost:9876/api/game/fire?"
            + param)).setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json").GET().build();
        HttpResponse<String> respond = client.send(req, HttpResponse.BodyHandlers.ofString());
        System.out.println(respond.body());
    }
}
