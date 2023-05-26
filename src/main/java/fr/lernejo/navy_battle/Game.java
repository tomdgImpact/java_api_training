package fr.lernejo.navy_battle;

import java.io.IOException;

public class Game {
    private final Player player;

    public Player getPlayer() {
        return player;
    }

    public Game(Player play){
        player = play;
    }

    public void initGame() throws IOException {
        this.player.initSeas();
        for(Boats.Type typeBoat : Boats.Type.values()){
                this.player.placeBoats(typeBoat);
                displayBoards();
            }
    }

    public void displayBoards(){
        StringBuilder builder = new StringBuilder();
        for(int x=0; x<2; x++){
            builder.append("  ");
            for (int i=0; i< 10; i++) builder.append(" ").append((char)(i+65));
        }
        System.out.println(builder.toString());
        for (int i = 0; i < 10; i++) {
            System.out.print(i+1);
            if(i<9) System.out.print(" ");
            for (int j = 0; j < 10; j++) System.out.print(" " + this.player.getSea()[i][j]);
            System.out.print("  ");
            for (int j = 0; j < 10; j++) System.out.print(" " + this.player.getEnemySea()[i][j]);
            System.out.print("\n");
        }
    }

    public String consequenceFire(String cellTargeted){
        int xPos = Integer.parseInt(cellTargeted.replaceAll("[A-Z]", "")) - 1; int yPos = (int)cellTargeted.charAt(0)-65;
        if(this.player.getSea()[xPos][yPos] == 0) return "miss";
        if(this.player.getSea()[xPos][yPos] >= 1){
            Boats boatHitted;
            for(int i = 0; i< this.player.getBoats().toArray().length; i++){
                for(int j = 0; j< this.player.getBoats().get(i).getSize(); j++){
                    if(this.player.getBoats().get(i).getxPos()[j] == xPos && this.player.getBoats().get(i).getyPos()[j] == yPos){
                        this.player.getBoats().get(i).getxPos()[j]=-1; this.player.getBoats().get(i).getyPos()[j]=-1;
                        boatHitted = this.player.getBoats().get(i);
                        this.player.getSea()[xPos][yPos] = 0;
                        if(boatHitted.isSunk()) return "sunk";
                        else return "hit";}}}}
        return "error";
    }

    public boolean isShipLeft(){
        for(int i = 0; i< this.player.getBoats().toArray().length; i++){
            for (int j = 0; j< this.player.getBoats().get(i).getSize(); j++){
                if(this.player.getBoats().get(i).getxPos()[j] >= 0 || this.player.getBoats().get(i).getyPos()[j] >= 0)
                    return true;
            }
        }
        return false;
    }

    public void updateBoards(String consequence, String cell){
        int yPos = ((int)cell.charAt(0))-65;
        int xPos = Integer.parseInt(cell.replaceAll("[A-Z]", "")) - 1;
        if(consequence.equals("miss"))
            this.player.getEnemySea()[xPos][yPos] = "x";

        if(consequence.equals("hit") || consequence.equals("sunk"))
            this.player.getEnemySea()[xPos][yPos] = "H";
    }
}
