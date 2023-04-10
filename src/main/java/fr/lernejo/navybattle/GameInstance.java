package fr.lernejo.navybattle;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GameInstance {
    public final void newGame(int port, String enemiUrl) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create(enemiUrl
        + "/api/game/start")).setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString("{\"id\":\"1\", \"url\":\"http://localhost:"
                + port + "\", \"message\":\"I will crush you!\"}")).build();
        HttpResponse<String> response = httpClient.send(req, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

    }
}
