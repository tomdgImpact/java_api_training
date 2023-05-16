package fr.lernejo.navy_battle;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

public class Game {
    // State enum
    public enum StateOfFire {
        miss,
        out,
        sunk,
        hit
    }

    public final ServerSide server;
    final TheFinal shoot;
    final Pattern reg = Pattern.compile("^[A-J](10|[1-9])$");
    final GameBoard init;
    public final List<List<int[]>> gameBoard;
    public final boolean[] inGame;

    public Game(ServerSide server) {
        this.server = server;
        shoot = new TheFinal(this);
        init = new GameBoard();
        gameBoard = init.BuildBoard();
        inGame = new boolean[]{false};
    }

    public void Fire() {
        int[] posShoot = shoot.FirePosition();
        posShoot[0]++;
        shoot.Shoot(posShoot);
    }

    public StateOfFire ShootAction(String case_position) throws IOException {
        if (!reg.matcher(case_position).find())
            return StateOfFire.out;
        int x_pos = Integer.parseInt(case_position.split("[A-J]")[1]) - 1;
        int y_pos = (case_position.charAt(0) - 'A');
        for (List<int[]> shipPos : gameBoard) {
            for (int[] ships : shipPos) {
                if (ships[0] == x_pos && ships[1] == y_pos) {
                    shipPos.remove(ships);
                    // Boat hit and sunk, remove it from game Board
                    if (shipPos.size() == 0) {
                        gameBoard.remove(shipPos);
                        return StateOfFire.sunk;
                    }
                    return StateOfFire.hit;
                }
            }
        }
        // Player miss his shoot
        return StateOfFire.miss;
    }
}
