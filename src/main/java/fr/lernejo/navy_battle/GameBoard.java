package fr.lernejo.navy_battle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameBoard {
    final int[] shipsPosition = new int[]{5, 4, 3, 3, 2};
    final Random rand;

    public GameBoard() {
        rand = new Random();
    }

    public boolean CorrectLocation(List<List<int[]>> ships,  int pos, int x, int y, int shipSize) {
        if (pos != 0) {
            if (y - shipSize < 0)
                return false;
            for (List<int[]> ship : ships) {
                for (int[] loc : ship) {
                    for (int i = 0; i < shipSize; i++) {
                        if (loc[0] == x && loc[1] == (y - i))
                            return false;
                    }
                }
            }
        }
        else {
            if (x - shipSize < 0)
                return false;
            for (List<int[]> ship : ships) {
                for (int[] loc : ship) {
                    for (int i = 0; i < shipSize; i++) {
                        if (loc[0] == (x - i) && loc[1] == y)
                            return false;
                    }
                }
            }
        }
        return true;
    }

    public void ShipPosition(List<List<int[]>> ships, int x, int y, int boatSize, int pos) {
        List<int[]> boatList = new ArrayList<>();
        if (pos == 0) {
            for (int i = 0; i < boatSize; i++)
                boatList.add(new int[]{x - i, y});
        } else {
            for (int i = 0; i < boatSize; i++)
                boatList.add(new int[]{x, y - i});
        }
        ships.add(boatList);
    }

    public List<List<int[]>> BuildBoard() {
        List<List<int[]>> lists = new ArrayList<>();
        for (int ship : shipsPosition) {
            boolean iscorrect = false;
            while (!iscorrect) {
                int x = rand.nextInt(10);
                int y = rand.nextInt(10);
                int nextposition = rand.nextInt(2);
                iscorrect = CorrectLocation(lists, x, y, nextposition, ship);
                if (iscorrect) {
                    ShipPosition(lists, x, y, nextposition, ship);
                }
            }
        }
        return lists;
    }
}
