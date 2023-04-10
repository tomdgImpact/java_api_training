package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

class CustomFireHandlerTest {
    @Test
    void MissBoat() throws IOException, InterruptedException {
        var server = new StartServer(9876);
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:9876/api/game/fire?cell=B1"))
            .GET()
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals("{\n" +
            "\t\"consequence\": \"miss\",\n" +
            "\t\"shipLeft\": true\n" +
            "}", response.body());

        server.Stop();
    }

    @Test
    void HitAircraftCarrierBoat() throws IOException, InterruptedException {
        var server = new StartServer(9876);
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:9876/api/game/fire?cell=A1"))
            .GET()
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals("{\n" +
            "\t\"consequence\": \"hit\",\n" +
            "\t\"shipLeft\": true\n" +
            "}", response.body());

        server.Stop();
    }

    @Test
    void SameHitAircraftCarrierBoat() throws IOException, InterruptedException {
        var server = new StartServer(9876);
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:9876/api/game/fire?cell=A1")).GET().build();

        HttpRequest request2 = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:9876/api/game/fire?cell=A1")).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals("{\n\t\"consequence\": \"hit\",\n\t\"shipLeft\": true\n}", response.body());

        Assertions.assertEquals("{\n\t\"consequence\": \"miss\",\n\t\"shipLeft\": true\n}", response2.body());

        server.Stop();
    }

    @Test
    void SunkAircraftCarrierBoat() throws IOException, InterruptedException {
        var server = new StartServer(9876);
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:9876/api/game/fire?cell=A1")).GET().build();
        HttpRequest request2 = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:9876/api/game/fire?cell=A2")).GET().build();
        HttpRequest request3 = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:9876/api/game/fire?cell=A3")).GET().build();
        HttpRequest request4 = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:9876/api/game/fire?cell=A4")).GET().build();
        HttpRequest request5 = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:9876/api/game/fire?cell=A5")).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response3 = client.send(request3, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response4 = client.send(request4, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response5 = client.send(request5, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals("{\n\t\"consequence\": \"hit\",\n\t\"shipLeft\": true\n}", response.body());
        Assertions.assertEquals("{\n\t\"consequence\": \"sunk\",\n\t\"shipLeft\": true\n}", response5.body());

        server.Stop();
    }

    @Test
    void HitCruiserBoat() throws IOException, InterruptedException {
        var server = new StartServer(9876);
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:9876/api/game/fire?cell=G1"))
            .GET()
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals("{\n" +
            "\t\"consequence\": \"hit\",\n" +
            "\t\"shipLeft\": true\n" +
            "}", response.body());

        server.Stop();
    }

    @Test
    void SameHitCruiserBoat() throws IOException, InterruptedException {
        var server = new StartServer(9876);
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:9876/api/game/fire?cell=G1"))
            .GET()
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response2 = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals("{\n\t\"consequence\": \"hit\",\n\t\"shipLeft\": true\n}", response.body());
        Assertions.assertEquals("{\n\t\"consequence\": \"miss\",\n\t\"shipLeft\": true\n}", response2.body());

        server.Stop();
    }

    @Test
    void SunkCruiserBoat() throws IOException, InterruptedException {
        var server = new StartServer(9876);
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:9876/api/game/fire?cell=G1")).GET().build();
        HttpRequest request2 = HttpRequest.newBuilder().uri(URI.create("http://localhost:9876/api/game/fire?cell=G2")).GET().build();
        HttpRequest request3 = HttpRequest.newBuilder().uri(URI.create("http://localhost:9876/api/game/fire?cell=G3")).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response3 = client.send(request3, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals("{\n\t\"consequence\": \"hit\",\n\t\"shipLeft\": true\n}", response.body());
        Assertions.assertEquals("{\n\t\"consequence\": \"hit\",\n\t\"shipLeft\": true\n}", response2.body());
        Assertions.assertEquals("{\n\t\"consequence\": \"sunk\",\n\t\"shipLeft\": true\n}", response3.body());

        server.Stop();
    }

    @Test
    void SunkALLCruiserBoat() throws IOException, InterruptedException {
        var server = new StartServer(9876);
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:9876/api/game/fire?cell=G1")).GET().build();
        HttpRequest request2 = HttpRequest.newBuilder().uri(URI.create("http://localhost:9876/api/game/fire?cell=G2")).GET().build();
        HttpRequest request3 = HttpRequest.newBuilder().uri(URI.create("http://localhost:9876/api/game/fire?cell=G3")).GET().build();

        HttpRequest request4 = HttpRequest.newBuilder().uri(URI.create("http://localhost:9876/api/game/fire?cell=G6")).GET().build();
        HttpRequest request5 = HttpRequest.newBuilder().uri(URI.create("http://localhost:9876/api/game/fire?cell=F6")).GET().build();
        HttpRequest request6 = HttpRequest.newBuilder().uri(URI.create("http://localhost:9876/api/game/fire?cell=H6")).GET().build();


        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response3 = client.send(request3, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response4 = client.send(request4, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response5 = client.send(request5, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response6 = client.send(request6, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals("{\n\t\"consequence\": \"hit\",\n\t\"shipLeft\": true\n}", response.body());
        Assertions.assertEquals("{\n\t\"consequence\": \"hit\",\n\t\"shipLeft\": true\n}", response2.body());
        Assertions.assertEquals("{\n\t\"consequence\": \"sunk\",\n\t\"shipLeft\": true\n}", response3.body());
        Assertions.assertEquals("{\n\t\"consequence\": \"hit\",\n\t\"shipLeft\": true\n}", response4.body());
        Assertions.assertEquals("{\n\t\"consequence\": \"hit\",\n\t\"shipLeft\": true\n}", response5.body());
        Assertions.assertEquals("{\n\t\"consequence\": \"sunk\",\n\t\"shipLeft\": true\n}", response6.body());

        server.Stop();
    }

    @Test
    void HitTorpedoBoat() throws IOException, InterruptedException {
        var server = new StartServer(9876);
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:9876/api/game/fire?cell=I3")).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals("{\n\t\"consequence\": \"hit\",\n\t\"shipLeft\": true\n}", response.body());

        server.Stop();
    }

    @Test
    void SameHitTorpedoBoat() throws IOException, InterruptedException {
        var server = new StartServer(9876);
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:9876/api/game/fire?cell=I3")).GET().build();
        HttpRequest request2 = HttpRequest.newBuilder().uri(URI.create("http://localhost:9876/api/game/fire?cell=I3")).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals("{\n\t\"consequence\": \"hit\",\n\t\"shipLeft\": true\n}", response.body());
        Assertions.assertEquals("{\n\t\"consequence\": \"miss\",\n\t\"shipLeft\": true\n}", response2.body());

        server.Stop();
    }

    @Test
    void SunkTorpedoBoat() throws IOException, InterruptedException {
        var server = new StartServer(9876);
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:9876/api/game/fire?cell=I3")).GET().build();
        HttpRequest request2 = HttpRequest.newBuilder().uri(URI.create("http://localhost:9876/api/game/fire?cell=J3")).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals("{\n\t\"consequence\": \"hit\",\n\t\"shipLeft\": true\n}", response.body());
        Assertions.assertEquals("{\n\t\"consequence\": \"sunk\",\n\t\"shipLeft\": true\n}", response2.body());

        server.Stop();
    }

    @Test
    void WrongParamDeleteNumber() throws IOException, InterruptedException {
        var server = new StartServer(9876);
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:9876/api/game/fire?cell=K")).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(404, response.statusCode());
        Assertions.assertEquals("Wrong Param", response.body());

        server.Stop();
    }

    @Test
    void WrongParamWrongNumber() throws IOException, InterruptedException {
        var server = new StartServer(9876);
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:9876/api/game/fire?cell=A0")).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(404, response.statusCode());
        Assertions.assertEquals("Wrong Param", response.body());

        server.Stop();
    }

    @Test
    void WrongParamWrongNumberUpper() throws IOException, InterruptedException {
        var server = new StartServer(9876);
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:9876/api/game/fire?cell=A28")).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(404, response.statusCode());
        Assertions.assertEquals("Wrong Param", response.body());

        server.Stop();
    }

    @Test
    void WrongParamWrongLetter() throws IOException, InterruptedException {
        var server = new StartServer(9876);
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:9876/api/game/fire?cell=K8")).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(404, response.statusCode());
        Assertions.assertEquals("Wrong Param", response.body());

        server.Stop();
    }

    @Test
    void WrongParamWrongLetterAndNumber() throws IOException, InterruptedException {
        var server = new StartServer(9876);
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:9876/api/game/fire?cell=K0")).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(404, response.statusCode());
        Assertions.assertEquals("Wrong Param", response.body());

        server.Stop();
    }

    @Test
    void ShipLeaftFalse() throws IOException, InterruptedException {
        var server = new StartServer(9876);
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:9876/api/game/fire?cell=A1")).GET().build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        request = HttpRequest.newBuilder().uri(URI.create("http://localhost:9876/api/game/fire?cell=A2")).GET().build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        request = HttpRequest.newBuilder().uri(URI.create("http://localhost:9876/api/game/fire?cell=A3")).GET().build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        request = HttpRequest.newBuilder().uri(URI.create("http://localhost:9876/api/game/fire?cell=A4")).GET().build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        request = HttpRequest.newBuilder().uri(URI.create("http://localhost:9876/api/game/fire?cell=A5")).GET().build();
        client.send(request, HttpResponse.BodyHandlers.ofString());

        request = HttpRequest.newBuilder().uri(URI.create("http://localhost:9876/api/game/fire?cell=G1")).GET().build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        request = HttpRequest.newBuilder().uri(URI.create("http://localhost:9876/api/game/fire?cell=G2")).GET().build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        request = HttpRequest.newBuilder().uri(URI.create("http://localhost:9876/api/game/fire?cell=G3")).GET().build();
        client.send(request, HttpResponse.BodyHandlers.ofString());

        request = HttpRequest.newBuilder().uri(URI.create("http://localhost:9876/api/game/fire?cell=I3")).GET().build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        request = HttpRequest.newBuilder().uri(URI.create("http://localhost:9876/api/game/fire?cell=J3")).GET().build();
        client.send(request, HttpResponse.BodyHandlers.ofString());

        request = HttpRequest.newBuilder().uri(URI.create("http://localhost:9876/api/game/fire?cell=F6")).GET().build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        request = HttpRequest.newBuilder().uri(URI.create("http://localhost:9876/api/game/fire?cell=G6")).GET().build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        request = HttpRequest.newBuilder().uri(URI.create("http://localhost:9876/api/game/fire?cell=H6")).GET().build();
        client.send(request, HttpResponse.BodyHandlers.ofString());

        request = HttpRequest.newBuilder().uri(URI.create("http://localhost:9876/api/game/fire?cell=D7")).GET().build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        request = HttpRequest.newBuilder().uri(URI.create("http://localhost:9876/api/game/fire?cell=D8")).GET().build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        request = HttpRequest.newBuilder().uri(URI.create("http://localhost:9876/api/game/fire?cell=D9")).GET().build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        request = HttpRequest.newBuilder().uri(URI.create("http://localhost:9876/api/game/fire?cell=D10")).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals("{\n\t\"consequence\": \"sunk\",\n\t\"shipLeft\": false\n}", response.body());

        server.Stop();
    }
}
