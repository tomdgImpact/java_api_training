package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class GameTest {
    @Test
    void shot_out_of_board() throws IOException {
        Game g = new Game(null);
        Assertions.assertEquals(Game.ShootState.out, g.ShootAction("false"), "shot outside game board");
        Assertions.assertEquals(Game.ShootState.out, g.ShootAction("apple"), "shot outside game board");
        Assertions.assertEquals(Game.ShootState.out, g.ShootAction("X99"), "shot outside game board");
        Assertions.assertEquals(Game.ShootState.out, g.ShootAction("A0"), "shot outside game board");
        Assertions.assertEquals(Game.ShootState.out, g.ShootAction("Z9"), "shot outside game board");
    }

    @Test
    void fire() throws IOException {
        ServerSide s = new ServerSide("1235");
        Game g = new Game(s);
        g.Fire();
    }

    @Test
    void good_shot() throws IOException {
        Game game = new Game(null);
        int cpt = 0;
        while (cpt < 100) {
            int[] array = game.board.get(0).get(0);
            if (game.board.get(0).size() == 1)
                Assertions.assertEquals(Game.ShootState.sunk, game.ShootAction(String.format("%s%s", (char) (array[1] + 'A'), array[0] + 1)), "Shoot sunk a boat");
            else
                Assertions.assertEquals(Game.ShootState.hit, game.ShootAction(String.format("%s%s", (char) (array[1] + 'A'), array[0] + 1)), "Shoot hit a boat");
            if (game.board.size() == 0)
                break;
            cpt++;
        }
        for (int i = 1; i <= 10; i++) {
            for (int j = 0; j <= 9; j++) {
                Assertions.assertEquals(Game.ShootState.miss, game.ShootAction(String.format("%s%s", (char) (j + 'A'), i)), "Shoot missed boats");
            }
        }
    }
}
