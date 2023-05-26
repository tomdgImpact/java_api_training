package fr.lernejo.navy_battle;

public class Boats {
    private final int size;
    private final int[] xPos;
    private final int[] yPos;

    public int[] getyPos() {
        return yPos;
    }

    public int[] getxPos() {
        return xPos;
    }

    public int getSize() {
        return size;
    }

    public Boats(int size, int[] xPositions, int[] yPositions){
        this.size = size;
        xPos = xPositions;
        yPos = yPositions;
    }

    public enum Type {
        carrier(5);
        private final int size;
        public int getSize() {
            return size;
        }
        Type(int size){
            this.size = size;
        }
    }

    public boolean isSunk(){
        for(int i=0; i<this.size; i++){
            if(this.xPos[i] >= 0 || this.yPos[i] >= 0)
                return false;
        }
        return true;
    }
}
