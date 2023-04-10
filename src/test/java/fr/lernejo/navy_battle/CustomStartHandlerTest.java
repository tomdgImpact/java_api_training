package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

class CustomStartHandlerTest {

    @Test
    void StartHandlerGetMethosTest() throws IOException, InterruptedException {
        var server = new StartServer(9876);
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:9876/api/game/start"))
            .GET()
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(404, response.statusCode());
        server.Stop();
    }

    @Test
    void StartHandlerDELETEMethosTest() throws IOException, InterruptedException {
        var server = new StartServer(9876);
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:9876/api/game/start"))
            .DELETE()
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(404, response.statusCode());
        server.Stop();
    }

    @Test
    void StartHandlerPUTMethosTest() throws IOException, InterruptedException {
        var server = new StartServer(9876);
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:9876/api/game/start"))
            .PUT(HttpRequest.BodyPublishers.noBody())
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(404, response.statusCode());
        server.Stop();
    }

    @Test
    void StartHandlePost() throws IOException, InterruptedException {
        var server = new StartServer(9876);
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:9876/api/game/start"))
            .POST(HttpRequest.BodyPublishers.ofString("{\"id\":\"1\", \"url\":\"http://localhost:" + 9876 + "\", \"message\":\"I will crush you!\"}"))
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(202, response.statusCode());
        server.Stop();
    }

    @Test
    void StartHandlePostNoUrl() throws IOException, InterruptedException {
        var server = new StartServer(9876);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:9876/api/game/start"))
            .POST(HttpRequest.BodyPublishers.ofString("{\"id\":\"1\", \"url\":\"" + "\", \"message\":\"I will crush you!\"}"))
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(400, response.statusCode());
        server.Stop();
    }

    @Test
    void StartHandlePostNoId() throws IOException, InterruptedException {
        var server = new StartServer(9876);
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:9876/api/game/start"))
            .POST(HttpRequest.BodyPublishers.ofString("{\"id\":\"\", \"url\":\"http://localhost:" + 9876 + "\", \"message\":\"I will crush you!\"}"))
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(400, response.statusCode());
        server.Stop();
    }

    @Test
    void StartHandlePostNoMessage() throws IOException, InterruptedException {
        var server = new StartServer(9876);
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:9876/api/game/start"))
            .POST(HttpRequest.BodyPublishers.ofString("{\"id\":\"1\", \"url\":\"http://localhost:" + 9876 + "\", \"message\":\"\"}"))
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(400, response.statusCode());
        server.Stop();
    }

    @Test
    void StartHandlePostNoJson() throws IOException, InterruptedException {
        var server = new StartServer(9876);
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:9876/api/game/start"))
            .POST(HttpRequest.BodyPublishers.ofString(""))
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(400, response.statusCode());
        server.Stop();
    }

    /*@Test
    void StartHandlePostWrongJson() throws IOException, InterruptedException {
        var server = new StartServer(9876);
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:9876/api/game/start"))
            .POST(HttpRequest.BodyPublishers.ofString("{\"Id\":\"1\", \"UrL\":\"http://localhost:" + 9876 + "\", \"mescage\":\"\"}"))
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(400, response.statusCode());
        server.Stop();
    }*/
}
