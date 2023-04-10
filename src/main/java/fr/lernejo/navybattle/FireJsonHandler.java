package fr.lernejo.navybattle;

public class FireJsonHandler {
    public final String gridCell;
    public final int x;
    public final int y;

    public FireJsonHandler(String cell) {
        gridCell = cell;
        y = cell.charAt(0) - 'A';
        if (cell.length() == 2) {
            x = cell.charAt(1) - '0' - 1;
        } else
            x = 9;
    }
}
