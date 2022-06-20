package ir.sharif.math.bp99_1.snake_and_ladder.model.pieces;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;

public class Sniper extends Piece{

//    boolean isOn;
//
//    public boolean isOn(){return isOn;}
//    public void setOn(boolean on){isOn = on;}

    public Sniper(Player player, Color color) {
        super(player, color);
        this.setType("Sniper");
    }

    public boolean canKill(Piece piece, int diceNumber){
        if(this.isOn() && !piece.isDead()){
        if(piece.getPlayer().getPlayerNumber()!=this.getPlayer().getPlayerNumber()){
            if(piece.getCurrentCell().getX()==this.getCurrentCell().getX()){
                if(piece.getCurrentCell().getY()>this.getCurrentCell().getY()) {
                    if(piece.getCurrentCell().getY()-this.getCurrentCell().getY()<=diceNumber){
//                        piece.setDead(true);
//                        setOn(false);
                        return true;
                }
             }
                if(piece.getCurrentCell().getY()<this.getCurrentCell().getY()) {
                    if(this.getCurrentCell().getY()-piece.getCurrentCell().getY()<=diceNumber){
//                        piece.setDead(true);
//                        setOn(false);
                        return true;
                    }
                }
            }
            if(piece.getCurrentCell().getY()==this.getCurrentCell().getY()){
                if(piece.getCurrentCell().getX()>this.getCurrentCell().getX()) {
                    if(piece.getCurrentCell().getX()-this.getCurrentCell().getX()<=diceNumber){
//                        piece.setDead(true);
//                        setOn(false);
                        return true;
                }
             }
                if(piece.getCurrentCell().getX()<this.getCurrentCell().getX()) {
                    if(this.getCurrentCell().getX()-piece.getCurrentCell().getX()<=diceNumber){
//                        piece.setDead(true);
//                        setOn(false);
                        return true;
                    }
                }
            }



        }

     }
        return false;
    }



    public void kill(Piece piece){
        if(!piece.getColor().equals(Color.GREEN)) {
            System.out.println("sniper killed this piece");
            piece.setDead(true);
            this.setOn(false);
        }
    }




}
