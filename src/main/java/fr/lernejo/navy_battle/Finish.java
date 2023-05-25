package fr.lernejo.navy_battle;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class Finish {
    final Game game;
    final List<int[]> shoots = new ArrayList<>();

    public Finish(Game gameFinished) {
        game = gameFinished;
        for (int i = 0; i <= 9; i++) {
            for (int j = 0; j <= 9; j++) {
                shoots.add(new int[]{i, j});
            }
        }
    }

    public Game.ShootState EndShoot() {
        game.inGame[0] = false;
        return Game.ShootState.out;
    }

    public int[] CellToRemove() {
        return shoots.remove(game.init.rand.nextInt(shoots.size()));
    }

    public Game.ShootState Shoot(int[] pos) {
        String format = String.format("%s%s", (char) (pos[1] + 'A'), pos[0] + 1);
        if (!game.inGame[0])
            return EndShoot();
        try {
            String url = String.format("%s/api/game/fire?format=%s", game.server.endPoints[0], format);
            URI uriUrl = new URI(url);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest fire_request = HttpRequest.newBuilder()
                .uri(uriUrl).setHeader("Accept", "application/json").setHeader("Content-Type", "application/json").GET()
                .build();
            client.send(fire_request, HttpResponse.BodyHandlers.ofString());
            return Game.ShootState.sunk;
        } catch (Exception exception) {
            return Game.ShootState.out;
        }
    }
}
