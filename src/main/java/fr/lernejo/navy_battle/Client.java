package fr.lernejo.navy_battle;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

public class Client {
    final ServerSide server;
    final String url;

    public Client(ServerSide serv, String urlPoint) throws URISyntaxException {
        server = serv;
        url = urlPoint;
    }

    public boolean Connect() throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI gameStart = new URI(url + "/api/game/start");
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(gameStart).setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(String.format("{\"id\": \"%s\",\"url\": \"%s\",\"message\": \"%s\"}", server.serverID, server.url, "Trying to connect")))
            .build();
        client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        server.endPoints[0] = url;
        server.gameBoard.inGame[0] = true;
        return true;
    }
}
