package ir.sharif.math.bp99_1.snake_and_ladder.model.pieces;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Board;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.Transmitter;

public class Piece {
    private Cell currentCell;
    private final Color color;
    private final Player player;
    private boolean isSelected;
    private boolean isDead;

    private boolean isOn;

    public boolean isOn(){return isOn;}
    public void setOn(boolean on){isOn = on;}


    private String type;

    public Piece(Player player, Color color) {
        this.color = color;
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public Color getColor() {
        return color;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public String getType(){return type;}

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
    }

    public void setType(String type){this.type=type;}

    public boolean isDead(){return isDead;}
    public void setDead(boolean dead){isDead = dead;}


    /**
     * @return "true" if your movement is valid  , else return " false"
     * <p>
     * In this method, you should check if the movement is valid of not.
     * <p>
     * You can use some methods ( they are recommended )
     * <p>
     * 1) "canEnter" method in class "Cell"
     * <p>
     * if your movement is valid, return "true" , else return " false"
     */
    public boolean isValidMove(Cell destination, int diceNumber) {



        Cell mycell=this.currentCell;


        if(mycell.getX()==destination.getX()){
            if(destination.getY()>mycell.getY()){
                if(destination.getY()-mycell.getY()!=diceNumber){
                    return false;
                }
            }
            if(destination.getY()<mycell.getY()){
                if(mycell.getY()-destination.getY()!=diceNumber){
                    return false;
                }
            }
        }
        if(mycell.getY()==destination.getY()){
            if(destination.getX()>mycell.getX()){
                if(destination.getX()-mycell.getX()!=diceNumber){
                    return false;
                }
            }
            if(destination.getX()<mycell.getX()){
                if(mycell.getX()-destination.getX()!=diceNumber){
                    return false;
                }
            }
        }



        //check kon balayii ro

        if(destination.canEnter(this)) {
//            System.out.println("can enter");
            for (int i = 1; i <= diceNumber; i++) {
                if (mycell.getX() == destination.getX()) {

                    if (destination.getY() < mycell.getY()) {
                        for (Cell x :
                                mycell.getAdjacentOpenCells()) {

                            if (x.getX() == mycell.getX() && x.getY() < mycell.getY()) {
                                mycell = x;
                            }
                        }
                    }

                    if (destination.getY() > mycell.getY()) {
                        for (Cell x :
                                mycell.getAdjacentOpenCells()) {

                            if (x.getX() == mycell.getX() && x.getY() > mycell.getY()) {
                                mycell = x;
                            }
                        }
                    }
                }

                if (mycell.getY() == destination.getY()) {
                    if (destination.getX() < mycell.getX()) {
                        for (Cell x :
                                mycell.getAdjacentOpenCells()) {

                            if (x.getY() == mycell.getY() && x.getX() < mycell.getX()) {
                                mycell = x;
                            }
                        }
                    }
                    if (destination.getX() > mycell.getX()) {
                        for (Cell x :
                                mycell.getAdjacentOpenCells()) {

                            if (x.getY() == mycell.getY() && x.getX() > mycell.getX()) {
                                mycell = x;
                            }
                        }
                    }
                }
            }

            if(mycell==destination){
                return true;
            }else{
                return false;
            }

        }
        else
            {
        return false;
        }


    }

    /**
     * @param destination move selected piece from "currentCell" to "destination"
     */
    public void moveTo(Cell destination) {


        if(destination.getPrize()!=null){
            this.player.usePrize(destination.getPrize());
        }


        if(destination.getColor().equals(this.color)){
            this.getPlayer().applyOnScore(4);
        }

        //bepors in null kardan okaye ya na

        Piece helpPiece=this;
        currentCell.setPiece(null);
        helpPiece.setCurrentCell(destination);
        destination.setPiece(helpPiece);


        if(destination.getEarthWorm()!=null){
            destination.getEarthWorm().transmit(this);
        }
        if(destination.getKillerTransmitter()!=null){
            destination.getKillerTransmitter().transmit(this);
        }
        if(destination.getMagicalTransmittet()!=null){
            destination.getMagicalTransmittet().transmit(this);
        }
        if(destination.getTransmitter()!=null){
            destination.getTransmitter().transmit(this);
        }

//        System.out.println(destination.getX()+" "+destination.getY());



    }
}
