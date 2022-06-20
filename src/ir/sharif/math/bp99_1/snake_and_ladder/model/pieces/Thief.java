package ir.sharif.math.bp99_1.snake_and_ladder.model.pieces;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;
import ir.sharif.math.bp99_1.snake_and_ladder.model.prizes.Prize;

public class Thief extends Piece{

    private int prizePoint;
    private int prizeChance;
    private int prizeDiceNumber;
    private boolean hasPrize;


    public boolean isHasPrize() {
        return hasPrize;
    }

    public void setHasPrize(boolean hasPrize) {
        this.hasPrize = hasPrize;
    }

    public void setPrizePoint(int prizePoint) {
        this.prizePoint = prizePoint;
    }

    public void setPrizeChance(int prizeChance) {
        this.prizeChance = prizeChance;
    }

    public int getPrizePoint() {
        return prizePoint;
    }

    public int getPrizeChance() {
        return prizeChance;
    }

    public int getPrizeDiceNumber() {
        return prizeDiceNumber;
    }

    public void setPrizeDiceNumber(int prizeDiceNumber) {
        this.prizeDiceNumber = prizeDiceNumber;
    }


    public Thief(Player player, Color color) {
        super(player, color);
        this.setType("Thief");
        this.prizePoint=0;
        this.prizeChance=0;
        this.prizeDiceNumber=0;
        this.setOn(true);
    }

    public void stealPrize(Cell cell){
        if(!this.hasPrize) {
            this.prizeChance = cell.getPrize().getChance();
            this.prizePoint = cell.getPrize().getPoint();
            this.prizeDiceNumber = cell.getPrize().getChance();
            hasPrize = true;
            cell.setPrize(null);
        }

    }


    public void leavePrize(Cell cell){
        //aya in kar doroste?
        if(cell.getPrize()!=null){
            cell.setPrize(null);
        }

        Prize prize=new Prize(cell,this.prizePoint,this.prizeChance,this.prizeDiceNumber);
        cell.setPrize(prize);

        hasPrize=false;
        prizePoint=0;
        prizeChance=0;
        prizeDiceNumber=0;

    }

}
