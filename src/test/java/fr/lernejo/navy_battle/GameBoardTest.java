package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameBoardTest {
    @Test
    void good_position() {
        GameBoard test = new GameBoard();
        List<List<int[]>> ships = new ArrayList<>();
        List<int[]> arrayList = new ArrayList<>();
        arrayList.add(new int[]{0, 0});
        ships.add(arrayList);
        assertTrue(test.PosOnBoard(ships, 5, 5, 0, 2), "boat is correctly inside the game board");
        assertTrue(test.PosOnBoard(ships, 0, 3, 1, 2), "boat is correctly inside the game board");
        assertTrue(test.PosOnBoard(ships, 3, 0, 0, 2), "boat is correctly inside the game board");
        assertTrue(test.PosOnBoard(ships, 5, 5, 1, 2), "boat is correctly inside the game board");
        assertFalse(test.PosOnBoard(ships, 2, 0, 1, 2), "boat is not inside the game board");
        assertFalse(test.PosOnBoard(ships, 0, 0, 0, 1), "boat is not inside the game board");
        assertFalse(test.PosOnBoard(ships, 0, 2, 0, 2), "boat is not inside the game board");
        assertFalse(test.PosOnBoard(ships, 1, 0, 0, 2), "boat is not inside the game board");
        assertFalse(test.PosOnBoard(ships, 0, 1, 1, 2), "boat is not inside the game board");
        assertFalse(test.PosOnBoard(ships, 0, 0, 1, 1), "boat is not inside the game board");
    }

    @Test
    void position() {
        GameBoard test = new GameBoard();
        List<List<int[]>> ships = new ArrayList<>();
        test.BoatsPosition(ships, 0, 2, 0, 2);
        test.BoatsPosition(ships, 2, 0, 1, 2);
    }

    @Test
    void generate_game_board() {
        GameBoard test = new GameBoard();
        for (int i = 0; i < 25; i++) {
            int k = 0;
            List<List<int[]>> ships = test.GenerateBoard();
            for (List<int[]> ship_loc : ships) {
                k += ship_loc.size();
            }
            Assertions.assertEquals(17, k, "Number filled cell");
        }
    }
}
