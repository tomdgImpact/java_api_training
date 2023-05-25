package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FinishTest {
    @Test
    void argument_not_exist() {
        Finish a = new Finish(null);
        try {
            a.Shoot(new int[]{1, 2});
            assertEquals(Game.ShootState.out, a.Shoot(new int[]{1, 2}), "bad shot is");
        } catch (Exception ignored) {
        }
    }

    @Test
    void end_game_test() throws IOException {
        Game g = new Game( new ServerSide("2134"));
        g.server.endPoints[0] = "http://localhost:2134";
        Finish a = new Finish(g);
        a.Shoot(new int[]{1,1});
    }
}
