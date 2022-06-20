package ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;

public class MagicalTransmitter extends Transmitter{
    public MagicalTransmitter(Cell firstCell, Cell lastCell) {
        super(firstCell, lastCell);
    }
    public void transmit(Piece piece) {


        if(this.getLastCell().canEnter(piece)){
            piece.moveTo(getLastCell());

        }
        piece.getPlayer().applyOnScore(3);
        piece.setOn(true);

        if(piece.getColor().equals(Color.BLUE)){
//            piece.getPlayer().getThief().setHasPrize(false);
            piece.getPlayer().getThief().setPrizePoint(0);
            piece.getPlayer().getThief().setPrizeChance(0);
            piece.getPlayer().getThief().setPrizeDiceNumber(0);
        }


    }


}
