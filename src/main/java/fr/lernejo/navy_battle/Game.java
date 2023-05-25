package fr.lernejo.navy_battle;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

public class Game {
    public final ServerSide server;
    final Finish shoot;
    final GameBoard init;
    public final List<List<int[]>> board;
    public final boolean[] inGame;
    final Pattern pattern = Pattern.compile("^[A-J](10|[1-9])$"); // regex that matches only coordinates on the board

    // shot state
    public enum ShootState {
        miss,
        out,
        sunk,
        hit
    }

    public Game(ServerSide serv) {
        server = serv;
        shoot = new Finish(this);
        init = new GameBoard();
        board = init.GenerateBoard();
        inGame = new boolean[]{false};
    }

    public void Fire() {
        int[] posShoot = shoot.CellToRemove();
        posShoot[0]++;
        shoot.Shoot(posShoot);
    }

    /*  indicate state and remove boats from the grid */
    public ShootState ShootAction(String case_position) throws IOException {
        if (!pattern.matcher(case_position).find())
            return ShootState.out;
        int x_pos = Integer.parseInt(case_position.split("[A-J]")[1]) - 1;
        int y_pos = (case_position.charAt(0) - 'A');
        for (List<int[]> shipPos : board) {
            for (int[] ships : shipPos) {
                if (ships[0] == x_pos && ships[1] == y_pos) {
                    shipPos.remove(ships);
                    if (shipPos.size() == 0) {
                        board.remove(shipPos);
                        return ShootState.sunk;
                    }
                    return ShootState.hit;
                }
            }
        }
        return ShootState.miss;
    }
}
