package ir.sharif.math.bp99_1.snake_and_ladder.logic;

import ir.sharif.math.bp99_1.snake_and_ladder.model.*;
import ir.sharif.math.bp99_1.snake_and_ladder.model.prizes.Prize;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.EarthWorm;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.KillerTransmitter;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.MagicalTransmitter;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.Transmitter;


public class BoardBuilder {

//    public BoardBuilder(String src) {
//
//
//    }

    /**
     * give you a string in constructor.
     * <p>
     * you should read the string and create a board according to it.
     */
//    public Board build() {
//        Board board=new Board();
//
//
//        return null;
//    }

    public void setCell (Board board,int x, int y, String color){
        Cell cell=new Cell(setColor(color),x,y);
        board.getCells().add(cell);

    }

    public void setAdjacentCells (Board board, int x, int y, int satr, int sotoon){
        if(x+1<=satr){
//            Cell cell=new Cell(board.getCell(x+1,y).getColor(),x+1,y);
            board.getCell(x,y).getAdjacentCells().add(board.getCell(x+1,y));
        }
        if(x-1>=1){
//            Cell cell=new Cell(board.getCell(x-1,y).getColor(),x-1,y);
            board.getCell(x,y).getAdjacentCells().add(board.getCell(x-1,y));
        }
        if(y+1<=sotoon){
//            Cell cell=new Cell(board.getCell(x,y+1).getColor(),x,y+1);
            board.getCell(x,y).getAdjacentCells().add(board.getCell(x,y+1));
        }
        if(y-1>=1){
//            Cell cell=new Cell(board.getCell(x,y-1).getColor(),x,y-1);
            board.getCell(x,y).getAdjacentCells().add(board.getCell(x,y-1));
        }

    }

    public void setAdjacentOpenCells (Board board, int x, int y){

        for (Cell cell:
             board.getCell(x,y).getAdjacentCells()) {
            if(!checkWall(board,x,y,cell.getX(),cell.getY())){
                board.getCell(x,y).getAdjacentOpenCells().add(cell);

            }
        }

//
    }




    public Color setColor (String color){


        if(color.equals("WHITE")){
            return Color.WHITE;
        }
        if(color.equals("BLACK")){
            return Color.BLACK;
        }
        if(color.equals("BLUE")){
            return Color.BLUE;
        }
        if(color.equals("RED")){
            return Color.RED;
        }
        if(color.equals("YELLOW")){
            return Color.YELLOW;
        }
        if(color.equals("GREEN")){
            return Color.GREEN;
        }

        return null;
    }

    public void setStartingCells (Board board, int x, int y, int playerNumber){
        Cell cell=new Cell(board.getCell(x,y).getColor(),x,y);
        board.getStartingCells().put(cell, playerNumber);
    }

    public  void setWall (Board board, int x1, int y1, int x2, int y2){

        Wall wall=new Wall(board.getCell(x1,y1),board.getCell(x2,y2));
        board.getWalls().add(wall);

    }

    public void setTransmitter(Board board, int x1, int y1, int x2, int y2, String type){
        if(type.equals("N")) {
            Transmitter transmitter = new Transmitter(board.getCell(x1, y1), board.getCell(x2, y2));
            board.getTransmitters().add(transmitter);
            board.getNormalTransmitters().add(transmitter);

            board.getCell(x1, y1).setTransmitter(transmitter);
        }
        if(type.equals("E")){
            EarthWorm earthWorm=new EarthWorm(board.getCell(x1,y1),board.getCell(x2,y2));
            board.getTransmitters().add(earthWorm);
            board.getEarthWorms().add(earthWorm);
            board.getCell(x1,y1).setEarthWorm(earthWorm);
        }
        if(type.equals("K")){
            KillerTransmitter killerTransmitter=new KillerTransmitter(board.getCell(x1,y1),board.getCell(x2,y2));
            board.getTransmitters().add(killerTransmitter);
            board.getKillerTransmitters().add(killerTransmitter);
            board.getCell(x1,y1).setKillerTransmitter(killerTransmitter);
        }
        if(type.equals("M")){
            MagicalTransmitter magicalTransmitter=new MagicalTransmitter(board.getCell(x1,y1),board.getCell(x2,y2));
            board.getTransmitters().add(magicalTransmitter);
            board.getMagicalTransmitters().add(magicalTransmitter);
            board.getCell(x1,y1).setMagicalTransmitter(magicalTransmitter);
        }

    }

    public void setPrize (Board board,int x, int y, int point, int chance, int diceNumber){
        Prize prize=new Prize(board.getCell(x,y),point,chance,diceNumber);
        board.getCell(x,y).setPrize(prize);
    }

    public boolean checkWall(Board board, int x1,int y1, int x2, int y2){
        for (Wall wall:
             board.getWalls()) {
            if(((wall.getCell1().getX()==x1 && wall.getCell1().getY()==y1) && (wall.getCell2().getX()==x2 && wall.getCell2().getY()==y2)) ||
                    ((wall.getCell2().getX()==x1 && wall.getCell2().getY()==y1) && (wall.getCell1().getX()==x2 && wall.getCell1().getY()==y2)) ){
                return true;
            }
        }
        return false;
    }




}
