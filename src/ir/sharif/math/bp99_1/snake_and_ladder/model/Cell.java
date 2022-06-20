package ir.sharif.math.bp99_1.snake_and_ladder.model;

import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;
import ir.sharif.math.bp99_1.snake_and_ladder.model.prizes.Prize;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.EarthWorm;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.KillerTransmitter;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.MagicalTransmitter;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.Transmitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cell {
    private Color color;
    private final int x, y;
    private Transmitter transmitter;

    private EarthWorm earthWorm;
    private MagicalTransmitter magicalTransmittet;
    private KillerTransmitter killerTransmitter;

    private Prize prize;
    private Piece piece;
    private final List<Cell> adjacentOpenCells;
    private final List<Cell> adjacentCells;
    private Board board;

    public void setBoard(Board board){
        this.board=board;
    }

    public Board getBoard(){
        return this.board;
    }

    public Cell(Color color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.transmitter = null;

        this.earthWorm=null;
        this.killerTransmitter=null;
        this.magicalTransmittet=null;


        this.prize = null;
        this.piece = null;
        this.adjacentOpenCells = new ArrayList<>();
        this.adjacentCells = new ArrayList<>();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }



    public List<Cell> getAdjacentCells() {
        return adjacentCells;
    }

    public List<Cell> getAdjacentOpenCells() {
        return adjacentOpenCells;
    }

    public Piece getPiece() {
        return piece;
    }

    public Prize getPrize() {
        return prize;
    }

    public Transmitter getTransmitter() {
        return transmitter;
    }


    public EarthWorm getEarthWorm() { return earthWorm; }
    public MagicalTransmitter getMagicalTransmittet() { return magicalTransmittet; }
    public KillerTransmitter getKillerTransmitter() { return killerTransmitter; }


    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public void setPrize(Prize prize) {
        this.prize = prize;
    }

    public void setTransmitter(Transmitter transmitter) {
        this.transmitter = transmitter;
    }



    public void setEarthWorm(EarthWorm earthWorm) {
        this.earthWorm = earthWorm;
    }
    public void setMagicalTransmitter(MagicalTransmitter magicalTransmittet) {
        this.magicalTransmittet = magicalTransmittet;
    }
    public void setKillerTransmitter(KillerTransmitter killerTransmitter) {
        this.killerTransmitter = killerTransmitter;
    }



    public void setColor(Color color){this.color = color;}

    /**
     * @return true if piece can enter this cell, else return false
     */
    public boolean canEnter(Piece piece) {
        if(!piece.getColor().equals(color.BLUE)) {
            if ((this.color.equals(piece.getColor()) || this.color.equals(Color.WHITE)) && !(this.color.equals(Color.BLACK)) && this.piece == null) {
                return true;
            }
        }
         if(piece.getColor().equals(Color.BLUE)){
            if(this.piece == null)return true;
        }

            return false;
    }


    /**
     * DO NOT CHANGE FOLLOWING METHODS.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return x == cell.x && y == cell.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
