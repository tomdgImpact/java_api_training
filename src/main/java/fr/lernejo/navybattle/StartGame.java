package fr.lernejo.navybattle;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class StartGame {
    final Grid gameMap = new Grid();
    public final StartJson ennemiJson;
    public final StartJson myJson;
    public StartGame(StartJson reqJson, StartJson reqJson1) {
        ennemiJson = reqJson;
        myJson = reqJson1;
    }

    public void Launch() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(ennemiJson.url.substring(1, ennemiJson.url.length() - 1)
            + "/api/game/fire?cell=A1")).setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json").GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }
}
