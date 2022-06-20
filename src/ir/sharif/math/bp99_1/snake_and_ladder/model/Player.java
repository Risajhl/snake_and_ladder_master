package ir.sharif.math.bp99_1.snake_and_ladder.model;

import ir.sharif.math.bp99_1.snake_and_ladder.Save;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.*;
import ir.sharif.math.bp99_1.snake_and_ladder.model.prizes.Prize;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player implements Save {
    private final String name;
    private int score;
    private final List<Piece> pieces;
    private final Dice dice;
    private Player rival;
    private final int id;
    private int playerNumber;
    private boolean isReady;
    private boolean dicePlayedThisTurn;

    private List<Integer> diceNums;

    private int moveLeft;
    private Piece selectedPiece;

    private Sniper sniper;
    private Healer healer;
    private Thief thief;
    private Bomber bomber;


    public Player(String name, int score, int id, int playerNumber) {
        this.name = name;
        this.score = score;
        this.id = id;
        this.playerNumber = playerNumber;
        this.dice = new Dice();
        this.pieces = new ArrayList<>();
        this.bomber=new Bomber(this,Color.RED);
        this.thief= new Thief(this, Color.BLUE);
        this.healer= new Healer(this, Color.GREEN);
        this.sniper= new Sniper(this, Color.YELLOW);

//        this.pieces.add(new Bomber(this, Color.RED));
//        this.pieces.add(new Thief(this, Color.BLUE));
//        this.pieces.add(new Healer(this, Color.GREEN));
//        this.pieces.add(new Sniper(this, Color.YELLOW));
        this.pieces.add(this.bomber);
        this.pieces.add(this.thief);
        this.pieces.add(this.healer);
        this.pieces.add(this.sniper);

        this.diceNums = new ArrayList<>();

        this.moveLeft = 0;
        this.selectedPiece = null;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Dice getDice() {
        return dice;
    }

    public int getScore() {
        return score;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public Player getRival() {
        return rival;
    }

    public int getMoveLeft() {
        return moveLeft;
    }

    public List getDiceNums(){ return diceNums; }


    public Piece getSelectedPiece() {
        return selectedPiece;
    }

    public Sniper getSniper() { return sniper; }

    public Healer getHealer() { return healer; }

    public Thief getThief() { return thief; }

    public Bomber getBomber() { return bomber; }


    public boolean isDicePlayedThisTurn() {
        return dicePlayedThisTurn;
    }

    public void setDicePlayedThisTurn(boolean dicePlayedThisTurn) {
        this.dicePlayedThisTurn = dicePlayedThisTurn;
    }

    public void setSelectedPiece(Piece selectedPiece) {
        this.selectedPiece = selectedPiece;
    }

    public void setMoveLeft(int moveLeft) {
        this.moveLeft = moveLeft;
    }



    public void setRival(Player rival) {
        this.rival = rival;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void applyOnScore(int score) {
        this.score += score;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }


    public void setSniper(Sniper sniper) { this.sniper = sniper; }

    public void setHealer(Healer healer) { this.healer = healer; }

    public void setThief(Thief thief) { this.thief = thief; }

    public void setBomber(Bomber bomber) { this.bomber = bomber; }

    /**
     * @param prize according to input prize , apply necessary changes to score and dice chance
     *              <p>
     *              you can use method "addChance" in class "Dice"(not necessary, but recommended)
     */
    public void usePrize(Prize prize) {
        if(!(this.getSelectedPiece().getColor().equals(Color.BLUE) && this.getThief().isHasPrize())) {
            score = score + prize.getPoint();
//        System.out.println("prize point: "+prize.getPoint());
            if (prize.getDiceNumber() != 0) {
                this.getDice().addChance(prize.getDiceNumber(), prize.getChance());
            }
        }



    }


    /**
     * check if any of player pieces can move to another cell.
     *
     * @return true if at least 1 piece has a move , else return false
     * <p>
     * you can use method "isValidMove" in class "Piece"(not necessary, but recommended)
     */
    public boolean hasMove(Board board, int diceNumber) {
        System.out.println("dice number is: "+diceNumber);

        for (Piece i:
             pieces) {
            if (!i.isDead()) {

//                System.out.println(i.getCurrentCell());

                if (board.getCell(i.getCurrentCell().getX() + diceNumber, i.getCurrentCell().getY()) != null) {
                    if (i.isValidMove(board.getCell(i.getCurrentCell().getX() + diceNumber, i.getCurrentCell().getY()), diceNumber)) {
                        return true;
                    }
                }
                if (board.getCell(i.getCurrentCell().getX() - diceNumber, i.getCurrentCell().getY()) != null) {
                    if (i.isValidMove(board.getCell(i.getCurrentCell().getX() - diceNumber, i.getCurrentCell().getY()), diceNumber)) {
                        return true;
                    }
                }
                if (board.getCell(i.getCurrentCell().getX(), i.getCurrentCell().getY() + diceNumber) != null) {
                    if (i.isValidMove(board.getCell(i.getCurrentCell().getX(), i.getCurrentCell().getY() + diceNumber), diceNumber)) {
                        return true;
                    }
                }
                if (board.getCell(i.getCurrentCell().getX(), i.getCurrentCell().getY() - diceNumber) != null) {
                    if (i.isValidMove(board.getCell(i.getCurrentCell().getX(), i.getCurrentCell().getY() - diceNumber), diceNumber)) {
                        return true;
                    }

                }
            }
        }



        if(this.healer.isOn()) {
            if(bomber.isDead()){
                if((bomber.getCurrentCell().getX()==healer.getCurrentCell().getX() && bomber.getCurrentCell().getY()-healer.getCurrentCell().getY()<=diceNumber && bomber.getCurrentCell().getY()-healer.getCurrentCell().getY()>=0)
                        || (bomber.getCurrentCell().getX()==healer.getCurrentCell().getX() && healer.getCurrentCell().getY()-bomber.getCurrentCell().getY()<=diceNumber && healer.getCurrentCell().getY()-bomber.getCurrentCell().getY()>=0)
                        || (bomber.getCurrentCell().getY()==healer.getCurrentCell().getY() && bomber.getCurrentCell().getX()-healer.getCurrentCell().getX()<=diceNumber && bomber.getCurrentCell().getX()-healer.getCurrentCell().getX()>=0)
                        || (bomber.getCurrentCell().getY()==healer.getCurrentCell().getY() && healer.getCurrentCell().getX()-bomber.getCurrentCell().getX()<=diceNumber && healer.getCurrentCell().getX()-bomber.getCurrentCell().getX()>=0))
                {
                    System.out.println("healer is on and can heal the bomber!");
                    return true;
                }
            }
            if(sniper.isDead()){
                if((sniper.getCurrentCell().getX()==healer.getCurrentCell().getX() && sniper.getCurrentCell().getY()-healer.getCurrentCell().getY()<=diceNumber && sniper.getCurrentCell().getY()-healer.getCurrentCell().getY()>=0)
                        || (sniper.getCurrentCell().getX()==healer.getCurrentCell().getX() && healer.getCurrentCell().getY()-sniper.getCurrentCell().getY()<=diceNumber && healer.getCurrentCell().getY()-sniper.getCurrentCell().getY()>=0)
                        || (sniper.getCurrentCell().getY()==healer.getCurrentCell().getY() && sniper.getCurrentCell().getX()-healer.getCurrentCell().getX()<=diceNumber && sniper.getCurrentCell().getX()-healer.getCurrentCell().getX()>=0)
                        || (sniper.getCurrentCell().getY()==healer.getCurrentCell().getY() && healer.getCurrentCell().getX()-sniper.getCurrentCell().getX()<=diceNumber && healer.getCurrentCell().getX()-sniper.getCurrentCell().getX()>=0))
                {
                    System.out.println("healer is on and can heal the sniper!");
                    return true;
                }
            }
            if(thief.isDead()){
                if((thief.getCurrentCell().getX()==healer.getCurrentCell().getX() && thief.getCurrentCell().getY()-healer.getCurrentCell().getY()<=diceNumber && thief.getCurrentCell().getY()-healer.getCurrentCell().getY()>=0)
                || (thief.getCurrentCell().getX()==healer.getCurrentCell().getX() && healer.getCurrentCell().getY()-thief.getCurrentCell().getY()<=diceNumber && healer.getCurrentCell().getY()-thief.getCurrentCell().getY()>=0)
                || (thief.getCurrentCell().getY()==healer.getCurrentCell().getY() && thief.getCurrentCell().getX()-healer.getCurrentCell().getX()<=diceNumber && thief.getCurrentCell().getX()-healer.getCurrentCell().getX()>=0)
                || (thief.getCurrentCell().getY()==healer.getCurrentCell().getY() && healer.getCurrentCell().getX()-thief.getCurrentCell().getX()<=diceNumber && healer.getCurrentCell().getX()-thief.getCurrentCell().getX()>=0))
                {
                    System.out.println("healer is on and can heal the thief!");
                    return true;
                }
            }
        }

        if(sniper.isOn() && !sniper.isDead()){
            if(!rival.getSniper().isDead()){
                if((rival.getSniper().getCurrentCell().getX()==sniper.getCurrentCell().getX() && rival.getSniper().getCurrentCell().getY()-sniper.getCurrentCell().getY()<=diceNumber && rival.getSniper().getCurrentCell().getY()-sniper.getCurrentCell().getY()>=0 )
                || (rival.getSniper().getCurrentCell().getX()==sniper.getCurrentCell().getX() && sniper.getCurrentCell().getY()-rival.getSniper().getCurrentCell().getY()<=diceNumber && sniper.getCurrentCell().getY()-rival.getSniper().getCurrentCell().getY()>=0)
                || (rival.getSniper().getCurrentCell().getY()==sniper.getCurrentCell().getY() && sniper.getCurrentCell().getX()-rival.getSniper().getCurrentCell().getX()<=diceNumber && sniper.getCurrentCell().getX()-rival.getSniper().getCurrentCell().getX()>=0)
                || (rival.getSniper().getCurrentCell().getY()==sniper.getCurrentCell().getY() && rival.getSniper().getCurrentCell().getX()-sniper.getCurrentCell().getX()<=diceNumber && rival.getSniper().getCurrentCell().getX()-sniper.getCurrentCell().getX()>=0))
                {
                    System.out.println("sniper is on and can kill rival's sniper");
                    return true;
                }
            }
            if(!rival.getBomber().isDead()){
                if((rival.getBomber().getCurrentCell().getX()==sniper.getCurrentCell().getX() && rival.getBomber().getCurrentCell().getY()-sniper.getCurrentCell().getY()<=diceNumber && rival.getBomber().getCurrentCell().getY()-sniper.getCurrentCell().getY()>=0 )
                        || (rival.getBomber().getCurrentCell().getX()==sniper.getCurrentCell().getX() && sniper.getCurrentCell().getY()-rival.getBomber().getCurrentCell().getY()<=diceNumber && sniper.getCurrentCell().getY()-rival.getBomber().getCurrentCell().getY()>=0)
                        || (rival.getBomber().getCurrentCell().getY()==sniper.getCurrentCell().getY() && sniper.getCurrentCell().getX()-rival.getBomber().getCurrentCell().getX()<=diceNumber && sniper.getCurrentCell().getX()-rival.getBomber().getCurrentCell().getX()>=0)
                        || (rival.getBomber().getCurrentCell().getY()==sniper.getCurrentCell().getY() && rival.getBomber().getCurrentCell().getX()-sniper.getCurrentCell().getX()<=diceNumber && rival.getBomber().getCurrentCell().getX()-sniper.getCurrentCell().getX()>=0))
                {
                    System.out.println("sniper is on and can kill rival's bomber");
                    return true;
                }
            }
            if(!rival.getThief().isDead()){
                if((rival.getThief().getCurrentCell().getX()==sniper.getCurrentCell().getX() && rival.getThief().getCurrentCell().getY()-sniper.getCurrentCell().getY()<=diceNumber && rival.getThief().getCurrentCell().getY()-sniper.getCurrentCell().getY()>=0 )
                        || (rival.getThief().getCurrentCell().getX()==sniper.getCurrentCell().getX() && sniper.getCurrentCell().getY()-rival.getThief().getCurrentCell().getY()<=diceNumber && sniper.getCurrentCell().getY()-rival.getThief().getCurrentCell().getY()>=0)
                        || (rival.getThief().getCurrentCell().getY()==sniper.getCurrentCell().getY() && sniper.getCurrentCell().getX()-rival.getThief().getCurrentCell().getX()<=diceNumber && sniper.getCurrentCell().getX()-rival.getThief().getCurrentCell().getX()>=0)
                        || (rival.getThief().getCurrentCell().getY()==sniper.getCurrentCell().getY() && rival.getThief().getCurrentCell().getX()-sniper.getCurrentCell().getX()<=diceNumber && rival.getThief().getCurrentCell().getX()-sniper.getCurrentCell().getX()>=0))
                {
                    System.out.println("sniper is on and can kill rival's thief");
                    return true;

                }
            }
        }

        if(bomber.isOn() && !bomber.isDead()){
            System.out.println("bomber is on u can boom boom");
            return true;
        }

        if(thief.isOn()){
            if(thief.isHasPrize()){
                System.out.println("ur thief has a prize u can leave it here");
                return true;
            }
            if(!thief.isHasPrize()){
               if(thief.getCurrentCell().getX()+diceNumber<=16)
               {
                   if(board.getCell(thief.getCurrentCell().getX()+diceNumber,thief.getCurrentCell().getY())==null){
                       System.out.println("u can move thief");
                       return true;
                   }
               }
               if(thief.getCurrentCell().getX()-diceNumber>=0){
                   if(board.getCell(thief.getCurrentCell().getX()-diceNumber,thief.getCurrentCell().getY())==null){
                       System.out.println("u can move thief");
                       return true;
                   }
               }
               if(thief.getCurrentCell().getY()+diceNumber<=7){
                   if(board.getCell(thief.getCurrentCell().getX(),thief.getCurrentCell().getY()+diceNumber)==null){
                       System.out.println("u can move thief");
                       return true;
                   }
               }
               if(thief.getCurrentCell().getY()-diceNumber>=0){
                   if(board.getCell(thief.getCurrentCell().getX(),thief.getCurrentCell().getY()-diceNumber)==null){
                       System.out.println("u can move thief");
                       return true;
                   }
               }


            }
        }



        return false;
    }


    /**
     * Deselect selectedPiece and make some changes in this class fields.
     */
    // **
    public void endTurn() {
        if(this.getSelectedPiece()!=null)this.getSelectedPiece().setSelected(false);
        this.setSelectedPiece(null);
        this.setMoveLeft(0);
        this.setDicePlayedThisTurn(false);

    }


    /**
     * DO NOT CHANGE FOLLOWING METHODS.
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String save() {

        String s="";
        s=s+"PLAYER"+playerNumber+": "+"\n";
        //playernumber
        s=s+""+playerNumber+"\n";
        //name
        s=s+""+this.name+"\n";
        //score
        s=s+""+this.score+"\n";
        //isdiceplayed
        if(this.isDicePlayedThisTurn()){s=s+"TRUE "+"\n";}else{s=s+"FALSE "+"\n";}
        //moveleft
        s=s+""+this.moveLeft+"\n";
        //diceNums
        if(diceNums.size()!=0) {
            s=s+"notEmpty"+"\n";
            for (int i = 0; i < diceNums.size(); i++) {
                s = s + "previousMoveLeft: " + diceNums.get(i) + "\n";
            }
        }else{
            s=s+"empty"+"\n";
        }
        //selectedpiece
        if(selectedPiece!=null){
            if(selectedPiece.getColor().equals(Color.RED))s=s+"RED"+"\n";
            if(selectedPiece.getColor().equals(Color.BLUE))s=s+"BLUE"+"\n";
            if(selectedPiece.getColor().equals(Color.YELLOW))s=s+"YELLOW"+"\n";
            if(selectedPiece.getColor().equals(Color.GREEN))s=s+"GREEN"+"\n";
        }else{
            s=s+"null"+"\n";
        }


        s=s+"\n";
        //bomber
        s=s+"BOMBER: "+"\n";
        //coordinate
        s=s+""+bomber.getCurrentCell().getX()+" "+bomber.getCurrentCell().getY()+"\n";
        //isdead
        if(bomber.isDead()){s=s+"TRUE "+"\n";}else{s=s+"FALSE "+"\n";}
        //ison
        if(bomber.isOn()){s=s+"TRUE "+"\n";}else{s=s+"FALSE "+"\n";}

        s=s+"\n";
        //healer
        s=s+"HEALER: "+"\n";
        //coordinate
        s=s+""+healer.getCurrentCell().getX()+" "+healer.getCurrentCell().getY()+"\n";
        //isdead
        if(healer.isDead()){s=s+"TRUE "+"\n";}else{s=s+"FALSE "+"\n";}
        //ison
        if(healer.isOn()){s=s+"TRUE "+"\n";}else{s=s+"FALSE "+"\n";}

        s=s+"\n";
        //sniper
        s=s+"SNIPER: "+"\n";
        //coordinate
        s=s+""+sniper.getCurrentCell().getX()+" "+sniper.getCurrentCell().getY()+"\n";
        //isdead
        if(sniper.isDead()){s=s+"TRUE "+"\n";}else{s=s+"FALSE "+"\n";}
        //ison
        if(sniper.isOn()){s=s+"TRUE "+"\n";}else{s=s+"FALSE "+"\n";}

        s=s+"\n";
        //thief
        s=s+"THIEF: "+"\n";
        //coordinate
        s=s+""+thief.getCurrentCell().getX()+" "+thief.getCurrentCell().getY()+"\n";
        //isdead
        if(thief.isDead()){s=s+"TRUE "+"\n";}else{s=s+"FALSE "+"\n";}
        //ison
        if(thief.isOn()){s=s+"TRUE "+"\n";}else{s=s+"FALSE "+"\n";}
        //hasprize
        if(thief.isHasPrize()){
            s=s+"TRUE "+"\n";
            s=s+""+thief.getPrizePoint()+""+"\n";
            System.out.println("prizepoint added"+ thief.getPrizePoint());
            s=s+""+thief.getPrizeChance()+""+"\n";
            System.out.println("prizechance added"+ thief.getPrizeChance());
            s=s+""+thief.getPrizeDiceNumber()+""+"\n";
            System.out.println("prizedicenumber added"+ thief.getPrizeDiceNumber());
        }else{s=s+"FALSE "+"\n";}
        s=s+"\n";




        return s;
    }
}

