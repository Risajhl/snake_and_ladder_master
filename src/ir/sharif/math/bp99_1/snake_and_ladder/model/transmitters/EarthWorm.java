package ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;

import java.util.Random;

public class EarthWorm extends Transmitter{
    public EarthWorm(Cell firstCell, Cell lastCell) {
        super(firstCell, lastCell);
    }

    public void transmit(Piece piece) {


        if(this.getLastCell().canEnter(piece)){
            piece.moveTo(getLastCell());
        }

        piece.getPlayer().applyOnScore(-3);

        Random random=new Random();
        int x=random.nextInt(7);
        int y=random.nextInt(16);
        x++;
        y++;

        System.out.println("earthworm moves ur piece to "+x+" "+y);
        if(piece.getCurrentCell().getBoard().getCell(x,y).canEnter(piece))
        {piece.moveTo(piece.getCurrentCell().getBoard().getCell(x,y));}

        if(piece.getColor().equals(Color.BLUE)){
            piece.getPlayer().getThief().setHasPrize(false);
            piece.getPlayer().getThief().setPrizePoint(0);
            piece.getPlayer().getThief().setPrizeChance(0);
            piece.getPlayer().getThief().setPrizeDiceNumber(0);
        }

    }
}
