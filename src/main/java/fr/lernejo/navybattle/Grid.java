package fr.lernejo.navybattle;

public class Grid {
    public final int[][] grid = new int[10][10];

    Grid() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; ++j) {
                if (j == 0 && i <= 4) {
                    grid[i][j] = 1;
                } else if (j == 3 && i >= 6) {
                    grid[i][j] = 2;
                } else if (j == 6 && i <= 2) {
                    grid[i][j] = 3;
                } else if (i == 5 && (j >= 5 && j <= 7)) {
                    grid[i][j] = 3;
                } else if (i == 2 && (j == 8 || j == 9)) {
                    grid[i][j] = 4;
                } else
                    grid[i][j] = 0;
            }
        }
    }
}
