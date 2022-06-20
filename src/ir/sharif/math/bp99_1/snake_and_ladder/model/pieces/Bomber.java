package ir.sharif.math.bp99_1.snake_and_ladder.model.pieces;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Board;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;

import java.util.ArrayList;

public class Bomber extends Piece{

//    boolean isOn;
//
//    public boolean isOn(){return isOn;}
//    public void setOn(boolean on){isOn = on;}

    public Bomber(Player player, Color color) {
        super(player, color);
        this.setType("Bomber");
    }

    public void blowUp() {
        if (this.isOn()) {
            ArrayList<Cell> cellsAround = new ArrayList<>();
            cellsAround.add(this.getCurrentCell());
            for (Cell cell:
                 this.getCurrentCell().getAdjacentCells()) {
                cellsAround.add(cell);
            }

            if(this.getCurrentCell().getX()-1>=1 ){
                if(getCurrentCell().getY()-1>=1){cellsAround.add(this.getCurrentCell().getBoard().getCell(this.getCurrentCell().getX()-1,getCurrentCell().getY()-1)); }
                if(getCurrentCell().getY()+1<=16){cellsAround.add(this.getCurrentCell().getBoard().getCell(this.getCurrentCell().getX()-1,getCurrentCell().getY()+1)); }
            }
            if(this.getCurrentCell().getX()+1<=7 ){
                if(getCurrentCell().getY()-1>=1){cellsAround.add(this.getCurrentCell().getBoard().getCell(this.getCurrentCell().getX()+1,getCurrentCell().getY()-1)); }
                if(getCurrentCell().getY()+1<=16){cellsAround.add(this.getCurrentCell().getBoard().getCell(this.getCurrentCell().getX()+1,getCurrentCell().getY()+1)); }
            }

            for (Cell cell:
                 cellsAround) {
                if(cell.getPiece()!=null) {
                    if(!cell.getPiece().getColor().equals(Color.GREEN)) {
                        cell.getPiece().setDead(true);
                        cell.getPiece().setOn(false);
                    }
                }
                if(cell.getPrize()!=null){
                    cell.setPrize(null);
                }
            }

            this.getCurrentCell().setColor(Color.BLACK);
            this.setOn(false);


        }
    }


}
