package fr.lernejo.navy_battle;

import java.util.*;

public class Player {
    private final String[] adversaryURL = new String[1];
    private final int[][] sea = new int[10][10];
    private final String[][] enemySea = new String[10][10];
    private final List<Boats> boats = new ArrayList<>();

    public List<Boats> getBoats() {
        return boats;
    }

    public String[][] getEnemySea() {
        return enemySea;
    }

    public int[][] getSea() {
        return sea;
    }

    String[] getAdversaryURL() {
        return adversaryURL;
    }
    public void initSeas(){
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){sea[i][j] = 0; enemySea[i][j] = "o";}
        }
    }

    public boolean validCoordinates(int boatSize, int direction, int xPos, int yPos){
        if(!((yPos<=9 && yPos>=0) && (xPos<=9 && xPos>=0))){System.out.println("You can't place the boat here!"); return false;}
        for(int i=0; i<boatSize; i++) {
            if (direction == 0)
                if (sea[xPos][yPos+i] != 0 || (yPos - 1) + boatSize > 9) {
                    System.out.println("You can't place the boat here!");
                    return false;
                }
            if (direction == 1)
                if (sea[xPos+i][yPos] != 0 || ((xPos) - 1) + boatSize > 9) {
                    System.out.println("You can't place the boat here!");
                    return false;
                }
        }
        return true;
    }

    public boolean checkCell(int xPos, int yPos, String cell){
        if(enemySea[xPos][yPos].equals("o") && cell.matches("^[A-J]{1}([1-9]|10)$"))
            return true;

        System.out.println("You can't fire here!");
        return false;
    }

    public String chooseCellToTarget(){
        String cell; int xPos; int yPos;
        System.out.println("Enter a cell to target:");
        do{
            xPos = new Random().nextInt(10);
            yPos = new Random().nextInt(10);
            cell = (char)(yPos+65) + "" + (xPos+1);
            System.out.println(cell);
        }while(!checkCell(xPos, yPos, cell));
        return cell;
    }

    public void placeBoats(Boats.Type typeBoat){
        System.out.print("Boat : " + typeBoat + ", Size : " + typeBoat.getSize() + "\n");
        System.out.println("Choose direction: horizontal(0) or vertical(1)");
        int direction = (int) Math.round(Math.random());
        System.out.println(direction);
        int xPos = 0; int yPos = 0;
        System.out.println("Choose a cell to place the head of your boat:");
        do{
            xPos = new Random().nextInt(10);
            yPos = new Random().nextInt(10);
            System.out.println((char)(xPos+65) + "" + yPos);
        }while(!validCoordinates(typeBoat.getSize(), direction, xPos, yPos));
        setBoats(typeBoat, direction, xPos, yPos);
    }

    public void setBoats(Boats.Type typeBoat, int direction, int xPos, int yPos){
        int[] x = new int[typeBoat.getSize()]; int[] y = new int[typeBoat.getSize()];
        for(int i = 0; i< typeBoat.getSize(); i++){
            if(direction == 0){x[i] = (xPos); y[i] = (yPos+i);
                sea[xPos][yPos+i] = typeBoat.getSize();
            }
            if(direction == 1){x[i] = xPos+i; y[i] = yPos;
                sea[xPos+i][yPos] = typeBoat.getSize();
            }
        }
        boats.add(new Boats(typeBoat.getSize(), x, y));
    }
}
