package ir.sharif.math.bp99_1.snake_and_ladder.model;

import ir.sharif.math.bp99_1.snake_and_ladder.Save;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.EarthWorm;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.KillerTransmitter;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.MagicalTransmitter;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.Transmitter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Board implements Save {
    private final List<Cell> cells;
    private final List<Transmitter> transmitters;
    private final List<EarthWorm> earthWorms;
    private final List<MagicalTransmitter> magicalTransmitters;
    private final List<KillerTransmitter> killerTransmitters;
    private final List<Transmitter> normalTransmitters;

    private final List<Wall> walls;
    private final Map<Cell, Integer> startingCells;

    public Board() {
        cells = new LinkedList<>();
        transmitters = new LinkedList<>();
        earthWorms = new LinkedList<>();
        magicalTransmitters=new LinkedList<>();
        killerTransmitters=new LinkedList<>();
        normalTransmitters=new LinkedList<>();

        walls = new LinkedList<>();
        startingCells = new HashMap<>();
    }

    public List<Cell> getCells() {
        return cells;
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public Map<Cell, Integer> getStartingCells() {
        return startingCells;
    }

    public List<Transmitter> getTransmitters() {
        return transmitters;
    }
    public List<EarthWorm> getEarthWorms(){ return earthWorms;}
    public List<MagicalTransmitter> getMagicalTransmitters(){ return magicalTransmitters;}
    public List<KillerTransmitter> getKillerTransmitters(){ return killerTransmitters;}
    public List<Transmitter> getNormalTransmitters(){ return normalTransmitters;}


    /**
     * give x,y , return a cell with that coordinates
     * return null if not exist.
     */
    public Cell getCell(int x, int y) {
        for(int i=0;i<this.cells.size();i++){
            if(this.cells.get(i).getX()==x && this.cells.get(i).getY()==y){
                return this.cells.get(i);
            }
        }

            return null;


    }

    @Override
    public String save() {

        String s="";
        s=s+"CELLS[ 7 16 ]:"+"\n";
        for(int x=1;x<=7;x++){
            for(int y=1;y<=16;y++){
                if(this.getCell(x,y).getColor().equals(Color.RED)) s=s+"RED ";
                if(this.getCell(x,y).getColor().equals(Color.BLUE)) s=s+"BLUE ";
                if(this.getCell(x,y).getColor().equals(Color.BLACK)) s=s+"BLACK ";
                if(this.getCell(x,y).getColor().equals(Color.GREEN)) s=s+"GREEN ";
                if(this.getCell(x,y).getColor().equals(Color.WHITE)) s=s+"WHITE ";
                if(this.getCell(x,y).getColor().equals(Color.YELLOW)) s=s+"YELLOW ";
            }
            s=s+"\n";
        }
        s=s+"\n";

        s=s+"WALLS[ "+ walls.size() +" ]:" +"\n";
        for(int i=0;i<walls.size();i++){
            int x1=walls.get(i).getCell1().getX();
            int x2=walls.get(i).getCell1().getY();
            int y1=walls.get(i).getCell2().getX();
            int y2=walls.get(i).getCell2().getY();
            s=s+""+x1+" "+x2+" "+y1+" "+y2+ "\n";
        }
        s=s+"\n";

        s=s+"TRANSMITTERS[ "+ transmitters.size() +" ]:" +"\n";

        for(int i=0;i<normalTransmitters.size();i++){
            int x1=normalTransmitters.get(i).getFirstCell().getX();
            int y1=normalTransmitters.get(i).getFirstCell().getY();
            int x2=normalTransmitters.get(i).getLastCell().getX();
            int y2=normalTransmitters.get(i).getLastCell().getY();

            s=s+""+x1+" "+y1+" "+x2+" "+y2+" N"+"\n";
        }
        for(int i=0;i<earthWorms.size();i++){
            int x1=earthWorms.get(i).getFirstCell().getX();
            int y1=earthWorms.get(i).getFirstCell().getY();
            int x2=earthWorms.get(i).getLastCell().getX();
            int y2=earthWorms.get(i).getLastCell().getY();

            s=s+""+x1+" "+y1+" "+x2+" "+y2+" E"+"\n";
        }
        for(int i=0;i<killerTransmitters.size();i++){
            int x1=killerTransmitters.get(i).getFirstCell().getX();
            int y1=killerTransmitters.get(i).getFirstCell().getY();
            int x2=killerTransmitters.get(i).getLastCell().getX();
            int y2=killerTransmitters.get(i).getLastCell().getY();

            s=s+""+x1+" "+y1+" "+x2+" "+y2+" K"+"\n";
        }
        for(int i=0;i<magicalTransmitters.size();i++){
            int x1=magicalTransmitters.get(i).getFirstCell().getX();
            int y1=magicalTransmitters.get(i).getFirstCell().getY();
            int x2=magicalTransmitters.get(i).getLastCell().getX();
            int y2=magicalTransmitters.get(i).getLastCell().getY();

            s=s+""+x1+" "+y1+" "+x2+" "+y2+" M"+"\n";
        }
        s=s+"\n";

        int prizeNum=0;
        for (Cell cell:
             this.cells) {
            if(cell.getPrize()!=null){
                prizeNum++;
            }
        }

        s=s+"PRIZES[ "+ prizeNum +" ]:"+"\n";
        for (Cell cell:
             cells) {
            if(cell.getPrize()!=null){
                int x=cell.getX();
                int y=cell.getY();
                int prizePoint =cell.getPrize().getPoint();
                int prizeChance = cell.getPrize().getChance();
                int prizeDiceNumber= cell.getPrize().getDiceNumber();
                s=s+""+x+" "+y+" "+prizePoint+" "+prizeChance+" "+prizeDiceNumber+"\n";
            }

        }
        s=s+"\n";




        return s;
    }
}
