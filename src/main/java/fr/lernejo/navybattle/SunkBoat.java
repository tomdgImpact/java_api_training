package fr.lernejo.navybattle;

public class SunkBoat {
    public int sunBoat(Grid gameMap, FireJsonHandler jsonParsing) {
        if (gameMap.grid[jsonParsing.x][jsonParsing.y] == 1 && sunkCarrier(gameMap) == 0)
            return 0;
        else if (gameMap.grid[jsonParsing.x][jsonParsing.y] == 2 && sunkCruiser(gameMap) == 0)
            return 0;
        else if (gameMap.grid[jsonParsing.x][jsonParsing.y] == 3 && sunkDestroyer(gameMap) == 0)
            return 0;
        else if (gameMap.grid[jsonParsing.x][jsonParsing.y] == 4 && sunkTorpedo(gameMap) == 0)
            return 0;
        return 1; // there is no boat on this cell
    }

    private int sunkTorpedo(Grid gameMap) {
        int boatPart = 0;
        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 10; ++j) {
                if (gameMap.grid[i][j] == 4)
                    boatPart = 1;
            }
        }
        // this boat has only one cell
        if (boatPart == 1)
            return 0;
        return 1;
    }

    private int sunkDestroyer(Grid gameMap) {
        int boatPart = 0;
        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 10; ++j) {
                if (gameMap.grid[i][j] == 3)
                    boatPart = 1;
            }
        }
        if (boatPart == 1)
            return 0;
        else if (boatPart == 4)
            return 
        return 1;
    }

    private int sunkCruiser(Grid gameMap) {
        int boatPart = 0;
        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 10; ++j) {
                if (gameMap.grid[i][j] == 2)
                    boatPart += 1;
            }
        }
        if (boatPart == 1)
            return 0;
        return 1;
    }

    private int sunkCarrier(Grid gameMap) {
        int boatPart = 0;
        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 10; ++j) {
                if (gameMap.grid[i][j] == 1)
                    boatPart = 1;
            }
        }
        if (boatPart == 1)
            return 0;
        return 1;
    }

}
