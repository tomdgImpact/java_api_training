package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.URISyntaxException;

public class ClientTest {
    @Test
    void accept() throws IOException, URISyntaxException, InterruptedException {
        String port = "9876";
        ServerSide clientServer = new ServerSide(port);
        Client client = new Client(clientServer, "http://localhost:" + port);
        client.Connect();
    }
}
