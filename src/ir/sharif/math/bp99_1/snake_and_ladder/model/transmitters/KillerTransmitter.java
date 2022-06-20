package ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;

public class KillerTransmitter extends Transmitter{
    public KillerTransmitter(Cell firstCell, Cell lastCell) {
        super(firstCell, lastCell);
    }

    public void transmit(Piece piece) {


        if(this.getLastCell().canEnter(piece)){
            piece.moveTo(getLastCell());

        }
        piece.getPlayer().applyOnScore(-3);
        if(!piece.getColor().equals(Color.GREEN)) {
            piece.setDead(true);
            piece.setOn(false);
            System.out.println("ur piece is dead");
        }
        if(piece.getColor().equals(Color.GREEN)){
            System.out.println("This piece is supposed to be dead but it's healer!");
        }



        if(piece.getColor().equals(Color.BLUE)){
            piece.getPlayer().getThief().setHasPrize(false);
            piece.getPlayer().getThief().setPrizePoint(0);
            piece.getPlayer().getThief().setPrizeChance(0);
            piece.getPlayer().getThief().setPrizeDiceNumber(0);
        }




    }


}
