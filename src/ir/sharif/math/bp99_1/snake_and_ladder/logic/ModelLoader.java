package ir.sharif.math.bp99_1.snake_and_ladder.logic;

import ir.sharif.math.bp99_1.snake_and_ladder.model.*;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.*;
import ir.sharif.math.bp99_1.snake_and_ladder.util.Config;

import java.io.*;
import java.util.Scanner;

public class ModelLoader {
    private final File boardFile, playersDirectory, archiveFile;


    /**
     * DO NOT CHANGE ANYTHING IN CONSTRUCTOR.
     */
    public ModelLoader() {
        boardFile = Config.getConfig("mainConfig").getProperty(File.class, "board");
        playersDirectory = Config.getConfig("mainConfig").getProperty(File.class, "playersDirectory");
        archiveFile = Config.getConfig("mainConfig").getProperty(File.class, "archive");
        if (!playersDirectory.exists()) playersDirectory.mkdirs();
    }


    /**
     * read file "boardFile" and craete a Board
     * <p>
     * you can use "BoardBuilder" class for this purpose.
     * <p>
     * pay attention add your codes in "try".
     */
    public Board loadBord() {
        try {
            Scanner scanner = new Scanner(boardFile);
            BoardBuilder boardBuilder=new BoardBuilder();
            Board board=new Board();

            scanner.next();
            int satr=scanner.nextInt();
            int sotoon=scanner.nextInt();

            scanner.next();
            for(int x=1;x<=satr;x++) {
                for (int y = 1; y <=sotoon; y++) {
                    String color=scanner.next();
                    boardBuilder.setCell(board,x,y,color);
                    board.getCell(x,y).setBoard(board);

                }
            }

            for (Cell cell:
                 board.getCells()) {
                boardBuilder.setAdjacentCells(board,cell.getX(),cell.getY(),satr,sotoon);
            }


            scanner.next();
            int startingCellsNumber =scanner.nextInt();
            scanner.next();
            for(int i=1;i<=startingCellsNumber;i++){
                int x=scanner.nextInt();
                int y=scanner.nextInt();
                int playerNumber=scanner.nextInt();

//                System.out.println(x+" "+y+" "+playerNumber);
                boardBuilder.setStartingCells(board,x,y,playerNumber);
                // ino okay konnnnnn!!!!
            }

            scanner.next();
            int wallNumber=scanner.nextInt();
            scanner.next();
            for(int i=1;i<=wallNumber;i++){
                int x1=scanner.nextInt();
                int y1=scanner.nextInt();
                int x2=scanner.nextInt();
                int y2=scanner.nextInt();
//                System.out.println("there's a wall between "+x1+" "+y1+" and "+x2+" "+y2);
                boardBuilder.setWall(board,x1,y1,x2,y2);
            }

            //~~~~~ inja biya ro liste cell ha foreach bezan vase harkodoom check kon wall doresho vase adjacentopencells


            for (Cell cell:
                 board.getCells()) {
//                System.out.println(cell.getX()+" "+cell.getY());
                boardBuilder.setAdjacentOpenCells(board,cell.getX(),cell.getY());


            }


            scanner.next();
            int transmitterNumber=scanner.nextInt();
            scanner.next();
            for(int i=1;i<=transmitterNumber;i++){
                int x1=scanner.nextInt();
                int y1=scanner.nextInt();
                int x2=scanner.nextInt();
                int y2=scanner.nextInt();
                String type=scanner.next();
//                System.out.println("there's a transmitter between "+x1+" "+y1+" and "+x2+" "+y2);
                boardBuilder.setTransmitter(board,x1,y1,x2,y2,type);
            }
            scanner.next();
            int prizeNumber=scanner.nextInt();
            scanner.next();
            for(int i=1;i<=prizeNumber;i++){
                int x=scanner.nextInt();
                int y=scanner.nextInt();
                int point=scanner.nextInt();
                int chance=scanner.nextInt();
                int diceNumber=scanner.nextInt();
                boardBuilder.setPrize(board,x,y,point,chance,diceNumber);

            }
            scanner.close();



            // Code Here


            return board;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("could not find board file");
            System.exit(-2);
        }
        return null;
    }

    /**
     * load player.
     * if no such a player exist, create an account(file) for him/her.
     * <p>
     * you can use "savePlayer" method of this class for that purpose.
     * <p>
     * add your codes in "try" block .
     */
    public Player loadPlayer(String name, int playerNumber) {
        try {
            File playerFile = getPlayerFile(name);
//            Scanner scanner = new Scanner(playerFile);

//            System.out.println(getPlayerFile(name));

            if(playerFile!=null){
                Scanner sc=new Scanner(playerFile);
                sc.nextLine();
                sc.nextLine();
//                sc.nextLine();
                int id=sc.nextInt();
//                System.out.println(sc.next());
                Player player=new Player(name,0,id,playerNumber);
                return player;
            }


            if(playerFile==null){
//                getPlayerFile(name).getParentFile().mkdirs();

                        int max=0;
                 for (File file:
                playersDirectory.listFiles()) {
                     Scanner sc = new Scanner(file);
                     if (!sc.next().equals("turn:")) {
//                     System.out.println(":>>");
                         sc.nextLine();
                         sc.nextLine();
//                sc.nextLine();

                         int id = sc.nextInt();
                         max = Math.max(max, id);
                         sc.close();
                     }
                 }

                 int newid=max+1;

                 String s=name+".txt";
                 File file=new File(playersDirectory,s);
                 file.createNewFile();
//                getPlayerFile(name).createNewFile();
                FileOutputStream fout= new FileOutputStream(file,false);
                PrintStream out= new PrintStream(fout);
                out.print("name:");
                out.println(name);
                out.println("id:");
                out.println(newid);
                out.println("score:");
                out.println(0);

                out.flush();
                out.close();

                Player player=new Player(name,0,newid, playerNumber);
                return player;

            }

            // Code in this part

            return null;

        } catch (FileNotFoundException | IllegalArgumentException e) {
            e.printStackTrace();
            System.err.println("could not find player file");
            System.exit(-2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * if player does not have a file, create one.
     * <p>
     * else update his/her file.
     * <p>
     * add your codes in "try" block .
     */
    public void savePlayer(Player player) {
        try {
            Scanner sc=new Scanner(getPlayerFile(player.getName()));
            sc.nextLine();
            sc.nextLine();
//            sc.nextLine();
            int id=sc.nextInt();
            sc.nextLine();
            sc.nextLine();
            int score=sc.nextInt();

            FileOutputStream fout= new FileOutputStream(getPlayerFile(player.getName()),false);
            PrintStream out= new PrintStream(fout);

            out.print("name:");
            out.println(player.getName());
            out.println("id");
            out.println(player.getId());
            out.println("total score");
            out.println(score+player.getScore());

            out.flush();
            out.close();


            // add your codes in this part
//            File file = getPlayerFile(player.getName());
//            PrintStream printStream = new PrintStream(new FileOutputStream(file));


        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("could not find player file");
            System.exit(-2);
        }
    }

    /**
     * give you a name (player name), search for its file.
     * return the file if exist.
     * return null if not.
     */
    private File getPlayerFile(String name) {
        String s=name+".txt";
        for (File file :
                playersDirectory.listFiles()) {
            if(file.getName().equals(s)){
                return file;
            }
        }

        return null;
    }

    private File getGameFile(String name){
        String s=name+".txt";
        for (File file:
             playersDirectory.listFiles()) {
            if(file.getName().equals(s)){
                return file;
            }
        }
        return null;
    }


    /**
     * at the end of the game save game details
     */
    public void archive(Player player1, Player player2) {
        try {
            FileOutputStream fout= new FileOutputStream(archiveFile,true);
            PrintStream out= new PrintStream(fout);
            out.println("Player1:");
            out.println(player1.getName());
            out.println("score: ");
            out.println(player1.getScore());
            out.println("Player2:");
            out.println(player2.getName());
            out.println("score: ");
            out.println(player2.getScore());
            out.println("The Winner: ");
            if(player1.getScore()>player2.getScore()){
            out.println(player1.getName());
            }
            if(player1.getScore()<player2.getScore()){
                out.println(player2.getName());
            }
            if(player1.getScore()==player2.getScore()){
                out.println("Draw");
            }
            out.println("...........");

            // add your codes in this part
            PrintStream printStream = new PrintStream(new FileOutputStream(archiveFile, true));


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void saveThisGame(GameState gameState) {

        String name = gameState.getPlayer1().getName() + ".." + gameState.getPlayer2().getName();
        Boolean exists=false;

        File gameFile=getGameFile(name);

        if(gameFile!=null){

            //turn
            String s0=gameState.save();

            //board
            String s1=gameState.getBoard().save();



            //player1
            String s2=gameState.getPlayer1().save();
            String s3=gameState.getPlayer1().getDice().save();

            //player2
            String s4=gameState.getPlayer2().save();
            String s5=gameState.getPlayer2().getDice().save();


            FileOutputStream fout= null;
            try {
                fout = new FileOutputStream(gameFile,false);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            PrintStream out= new PrintStream(fout);

            out.print(s0);
            out.print(s1);
            out.print(s2);
            out.print(s3);
            out.print(s4);
            out.print(s5);

            out.flush();
            out.close();


        }

        else if(gameFile==null){

            String s=name+".txt";
            File file=new File(playersDirectory,s);
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //turn
            String s0=gameState.save();


            //board
            String s1=gameState.getBoard().save();

            //player1
            String s2=gameState.getPlayer1().save();
            String s3=gameState.getPlayer1().getDice().save();

            //player2
            String s4=gameState.getPlayer2().save();
            String s5=gameState.getPlayer2().getDice().save();


            FileOutputStream fout= null;
            try {
                fout = new FileOutputStream(file,false);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            PrintStream out= new PrintStream(fout);

            out.print(s0);
            out.print(s1);
            out.print(s2);
            out.print(s3);
            out.print(s4);
            out.print("...");
            out.print(s5);

            out.flush();
            out.close();


        }


//        //board
//        String s1=gameState.getBoard().save();
//
//        //player1
//        String s2=gameState.getPlayer1().save();
//        String s3=gameState.getPlayer1().getDice().save();
//
//        //player2
//        String s4=gameState.getPlayer2().save();
//        String s5=gameState.getPlayer2().getDice().save();


    }

    public boolean gameExists(String name){
        String s=name+".txt";
        for (File file:
             playersDirectory.listFiles()) {

            if(file.getName().equals(s)) {
                System.out.println("existst:D");

                return true;}
        }

        return false;
    }


    public void deleteGameFile(String name) {
        String s = name + ".txt";
        for (File f :
                playersDirectory.listFiles()) {
            if (f.getName().equals(s)) {

                f.delete();


            }
        }

    }





    public GameState load(String name, Player player1, Player player2){

        File gameFile= getGameFile(name);
        try {
            Scanner scanner=new Scanner(gameFile);
            Board board=new Board();
            BoardBuilder boardBuilder=new BoardBuilder();

            scanner.next();
            int turn=scanner.nextInt();


            scanner.next();
            int satr=scanner.nextInt();
            int sotoon=scanner.nextInt();

            scanner.next();
            for(int x=1;x<=satr;x++) {
                for (int y = 1; y <=sotoon; y++) {
                    String color=scanner.next();
                    boardBuilder.setCell(board,x,y,color);
                    board.getCell(x,y).setBoard(board);

                }
            }

            for (Cell cell:
                    board.getCells()) {
                boardBuilder.setAdjacentCells(board,cell.getX(),cell.getY(),satr,sotoon);
            }

            scanner.next();
            int wallNumber=scanner.nextInt();
            scanner.next();
            for(int i=1;i<=wallNumber;i++){
                int x1=scanner.nextInt();
                int y1=scanner.nextInt();
                int x2=scanner.nextInt();
                int y2=scanner.nextInt();
//                System.out.println("there's a wall between "+x1+" "+y1+" and "+x2+" "+y2);
                boardBuilder.setWall(board,x1,y1,x2,y2);
            }



            for (Cell cell:
                    board.getCells()) {
//                System.out.println(cell.getX()+" "+cell.getY());
                boardBuilder.setAdjacentOpenCells(board,cell.getX(),cell.getY());


            }


            scanner.next();
            int transmitterNumber=scanner.nextInt();
            scanner.next();
            for(int i=1;i<=transmitterNumber;i++){
                int x1=scanner.nextInt();
                int y1=scanner.nextInt();
                int x2=scanner.nextInt();
                int y2=scanner.nextInt();
                String type=scanner.next();
//                System.out.println("there's a transmitter between "+x1+" "+y1+" and "+x2+" "+y2);
                boardBuilder.setTransmitter(board,x1,y1,x2,y2,type);
            }
            scanner.next();
            int prizeNumber=scanner.nextInt();
            scanner.next();
            for(int i=1;i<=prizeNumber;i++){
                int x=scanner.nextInt();
                int y=scanner.nextInt();
                int point=scanner.nextInt();
                int chance=scanner.nextInt();
                int diceNumber=scanner.nextInt();
                boardBuilder.setPrize(board,x,y,point,chance,diceNumber);

            }


            ///........



            for(int i=0;i<4;i++){
                player1.getPieces().remove(0);
            }

            scanner.next(); //player1
            scanner.next(); //1
            scanner.next(); //player name
            int score1= scanner.nextInt(); //score
            player1.setScore(score1);
            String diceplayed1= scanner.next();
//            System.out.println(diceplayed1);
            if(diceplayed1.equals("TRUE")){
                player1.setDicePlayedThisTurn(true);
            }else{
                player1.setDicePlayedThisTurn(false);
            }

            int moveleft1=scanner.nextInt();//moveleft
            if(diceplayed1.equals("TRUE"))player1.setMoveLeft(moveleft1);

            String isempty1= scanner.next();
//            System.out.println(isempty1);
            if(isempty1.equals("notEmpty")){
                scanner.next();
                int previousmoveleft1= scanner.nextInt(); //previous moveleft
                player1.getDiceNums().add(previousmoveleft1);
            }

            String selectedpiece1= scanner.next();
//        System.out.println(selectedpiece1);
            if(selectedpiece1.equals("null")){
                //setnull
                player1.setSelectedPiece(null);

//            }else{
////                System.out.println("its null");
//
//
            }

            scanner.next();//bomber
            int xb1= scanner.nextInt();
            int yb1= scanner.nextInt();
//            System.out.println(xb1+" "+yb1);
            Bomber bomber1=new Bomber(player1, Color.RED);
            player1.setBomber(bomber1);
            player1.getBomber().setCurrentCell(board.getCell(xb1,yb1));
            board.getCell(xb1,yb1).setPiece(player1.getBomber());
            player1.getPieces().add(player1.getBomber());


            String bomberisdead1= scanner.next();
//            System.out.println(bomberisdead1);
            if(bomberisdead1.equals("TRUE")){
                player1.getBomber().setDead(true);
                //setdead
            }else{
                player1.getBomber().setDead(false);
                //setnot dead
            }
            String bomberison1= scanner.next();
//            System.out.println(bomberison1);
            if(bomberison1.equals("TRUE")){
                player1.getBomber().setOn(true);
                //seton
            }else{
                player1.getBomber().setOn(false);
                //setnot on
            }

            scanner.next();//healer
            int xh1= scanner.nextInt();
            int yh1= scanner.nextInt();
//            System.out.println(xh1+" "+yh1);
            Healer healer1=new Healer(player1, Color.GREEN);
            player1.setHealer(healer1);
            player1.getHealer().setCurrentCell(board.getCell(xh1,yh1));
            board.getCell(xh1,yh1).setPiece(player1.getHealer());
            player1.getPieces().add(player1.getHealer());

            String healerisdead1= scanner.next();
//            System.out.println(healerisdead1);
            if(healerisdead1.equals("TRUE")){
                player1.getHealer().setDead(true);
                //setdead
            }else{
                player1.getHealer().setDead(false);
                //setnot dead
            }
            String healerison1= scanner.next();
//            System.out.println(healerison1);
            if(healerison1.equals("TRUE")){
                player1.getHealer().setOn(true);
                //seton
            }else{
                player1.getHealer().setOn(false);
                //setnot on
            }

            scanner.next();//sniper
            int xs1= scanner.nextInt();
            int ys1= scanner.nextInt();
//            System.out.println(xs1+" "+ys1);
            Sniper sniper1=new Sniper(player1, Color.YELLOW);
            player1.setSniper(sniper1);
            player1.getSniper().setCurrentCell(board.getCell(xs1,ys1));
            board.getCell(xs1,ys1).setPiece(player1.getSniper());
            player1.getPieces().add(player1.getSniper());

            String sniperisdead1= scanner.next();
//            System.out.println(sniperisdead1);
            if(sniperisdead1.equals("TRUE")){
                player1.getSniper().setDead(true);
                //setdead
            }else{
                player1.getSniper().setDead(false);
                //setnot dead
            }
            String sniperison1= scanner.next();
//            System.out.println(sniperison1);
            if(sniperison1.equals("TRUE")){
                player1.getSniper().setOn(true);
                //seton
            }else{
                player1.getSniper().setOn(false);
                //setnot on
            }

            scanner.next();//thief
            int xt1= scanner.nextInt();
            int yt1= scanner.nextInt();
//            System.out.println(xt1+" "+yt1);
            Thief thief1=new Thief(player1, Color.BLUE);
            player1.setThief(thief1);
            player1.getThief().setCurrentCell(board.getCell(xt1,yt1));
            board.getCell(xt1,yt1).setPiece(player1.getThief());
            player1.getPieces().add(player1.getThief());

            String thiefisdead1= scanner.next();
//            System.out.println(thiefisdead1);
            if(thiefisdead1.equals("TRUE")){
                player1.getThief().setDead(true);
                //setdead
            }else{
                player1.getThief().setDead(false);
                //setnot dead
            }
            String thiefison1= scanner.next();
//            System.out.println(thiefison1);
            if(thiefison1.equals("TRUE")){
                player1.getThief().setOn(true);
                //seton
            }else{
                player1.getThief().setOn(false);
                //setnot on
            }

            String hasprize1=scanner.next();
//            System.out.println(hasprize1);
            if(hasprize1.equals("TRUE")){
                player1.getThief().setHasPrize(true);
                int prizepoint1= scanner.nextInt();
                player1.getThief().setPrizePoint(prizepoint1);
                //setprizepoint
                int prizechance1= scanner.nextInt();
                player1.getThief().setPrizeChance(prizechance1);
                //setprizechance
                int prizedicenumber1= scanner.nextInt();
                player1.getThief().setPrizeDiceNumber(prizedicenumber1);
                //setprizedicenumber

            }else{
                player1.getThief().setHasPrize(false);
//                System.out.println("no prize:v");
            }

            scanner.nextLine();
            player1.getDice().setChance(1,scanner.nextInt());
            player1.getDice().setChance(2,scanner.nextInt());
            player1.getDice().setChance(3,scanner.nextInt());
            player1.getDice().setChance(4,scanner.nextInt());
            player1.getDice().setChance(5,scanner.nextInt());
            player1.getDice().setChance(6,scanner.nextInt());


                if(selectedpiece1.equals("RED")){
//                    Piece piece1=new Piece(player1,Color.RED);
                    player1.setSelectedPiece(player1.getBomber());
                    player1.getBomber().setSelected(true);
                }
                if(selectedpiece1.equals("BLUE")){
                    player1.setSelectedPiece(player1.getThief());
                    player1.getThief().setSelected(true);
                }
                if(selectedpiece1.equals("YELLOW")){
                    player1.setSelectedPiece(player1.getSniper());
                    player1.getSniper().setSelected(true);
                }
                if(selectedpiece1.equals("GREEN")){
                    player1.setSelectedPiece(player1.getHealer());
                    player1.getHealer().setSelected(true);
                }


            //setdicechance
            scanner.nextLine();


            for(int i=0;i<4;i++){
                player2.getPieces().remove(0);
            }


            //player2
            scanner.next(); //player2
            scanner.next(); //2
            scanner.next(); //player name
            int score2= scanner.nextInt(); //score
            player2.setScore(score2);
            String diceplayed2= scanner.next();
//            System.out.println(diceplayed2);
            if(diceplayed2.equals("TRUE")){
                player2.setDicePlayedThisTurn(true);
            }else{
                player2.setDicePlayedThisTurn(false);
            }

            int moveleft2=scanner.nextInt();//moveleft
            if(diceplayed2.equals("TRUE"))player2.setMoveLeft(moveleft2);

            String isempty2= scanner.next();
//            System.out.println(isempty2);
            if(isempty2.equals("notEmpty")){
                scanner.next();
                int previousmoveleft2= scanner.nextInt(); //previous moveleft
                player2.getDiceNums().add(previousmoveleft2);
            }

            String selectedpiece2= scanner.next();
//        System.out.println(selectedpiece2);
            if(selectedpiece2.equals("null")){
                player2.setSelectedPiece(null);
                //setnull
            }

            scanner.next();//bomber
            int xb2= scanner.nextInt();
            int yb2= scanner.nextInt();
//            System.out.println(xb2+" "+yb2);
            Bomber bomber2=new Bomber(player2, Color.RED);
            player2.setBomber(bomber2);
            player2.getBomber().setCurrentCell(board.getCell(xb2,yb2));
            board.getCell(xb2,yb2).setPiece(player2.getBomber());
            player2.getPieces().add(player2.getBomber());


            String bomberisdead2= scanner.next();
//            System.out.println(bomberisdead2);
            if(bomberisdead2.equals("TRUE")){
                player2.getBomber().setDead(true);
                //setdead
            }else{
                player2.getBomber().setDead(false);
                //setnot dead
            }
            String bomberison2= scanner.next();
//            System.out.println(bomberison2);
            if(bomberison2.equals("TRUE")){
                player2.getBomber().setOn(true);
                //seton
            }else{
                player2.getBomber().setOn(false);
                //setnot on
            }

            scanner.next();//healer
            int xh2= scanner.nextInt();
            int yh2= scanner.nextInt();
//            System.out.println(xh2+" "+yh2);
            Healer healer2=new Healer(player2, Color.GREEN);
            player2.setHealer(healer2);
            player2.getHealer().setCurrentCell(board.getCell(xh2,yh2));
            board.getCell(xh2,yh2).setPiece(player2.getHealer());
            player2.getPieces().add(player2.getHealer());


            String healerisdead2= scanner.next();
//            System.out.println(healerisdead2);
            if(healerisdead2.equals("TRUE")){
                player2.getHealer().setDead(true);
                //setdead
            }else{
                player2.getHealer().setDead(false);
                //setnot dead
            }
            String healerison2= scanner.next();
//            System.out.println(healerison2);
            if(healerison2.equals("TRUE")){
                player2.getHealer().setOn(true);
                //seton
            }else{
                player2.getHealer().setOn(false);
                //setnot on
            }

            scanner.next();//sniper
            int xs2= scanner.nextInt();
            int ys2= scanner.nextInt();
//            System.out.println(xs2+" "+ys2);
            Sniper sniper2=new Sniper(player2, Color.YELLOW);
            player2.setSniper(sniper2);
            player2.getSniper().setCurrentCell(board.getCell(xs2,ys2));
            board.getCell(xs2,ys2).setPiece(player2.getSniper());
            player2.getPieces().add(player2.getSniper());


            String sniperisdead2= scanner.next();
//            System.out.println(sniperisdead2);
            if(sniperisdead2.equals("TRUE")){
                player2.getSniper().setDead(true);
                //setdead
            }else{
                player2.getSniper().setDead(false);
                //setnot dead
            }
            String sniperison2= scanner.next();
//            System.out.println(sniperison2);
            if(sniperison2.equals("TRUE")){
                player2.getSniper().setOn(true);
                //seton
            }else{
                player2.getSniper().setOn(false);
                //setnot on
            }

            scanner.next();//thief
            int xt2= scanner.nextInt();
            int yt2= scanner.nextInt();
//            System.out.println(xt2+" "+yt2);
            Thief thief2=new Thief(player2, Color.BLUE);
            player2.setThief(thief2);
            player2.getThief().setCurrentCell(board.getCell(xt2,yt2));
            board.getCell(xt2,yt2).setPiece(player2.getThief());
            player2.getPieces().add(player2.getThief());



            String thiefisdead2= scanner.next();
//            System.out.println(thiefisdead2);
            if(thiefisdead2.equals("TRUE")){
                player2.getThief().setDead(true);
                //setdead
            }else{
                player2.getThief().setDead(false);
                //setnot dead
            }
            String thiefison2= scanner.next();
//            System.out.println(thiefison2);
            if(thiefison2.equals("TRUE")){
                player2.getThief().setOn(true);
                //seton
            }else{
                player2.getThief().setOn(false);
                //setnot on
            }

            String hasprize2=scanner.next();
//            System.out.println(hasprize2);
            if(hasprize2.equals("TRUE")){
                player2.getThief().setHasPrize(true);
                int prizepoint2= scanner.nextInt();
                player2.getThief().setPrizePoint(prizepoint2);
                //setprizepoint
                int prizechance2= scanner.nextInt();
                player2.getThief().setPrizeChance(prizechance2);
                //setprizechance
                int prizedicenumber2= scanner.nextInt();
                player2.getThief().setPrizeDiceNumber(prizedicenumber2);
                //setprizedicenumber

            }else{
                player2.getThief().setHasPrize(false);
//                System.out.println("no prize:v");
            }

            scanner.nextLine();
            player2.getDice().setChance(1,scanner.nextInt());
            player2.getDice().setChance(2,scanner.nextInt());
            player2.getDice().setChance(3,scanner.nextInt());
            player2.getDice().setChance(4,scanner.nextInt());
            player2.getDice().setChance(5,scanner.nextInt());
            player2.getDice().setChance(6,scanner.nextInt());


            //setdicechance



            if(selectedpiece2.equals("RED")){
                player2.setSelectedPiece(player2.getBomber());
                player2.getBomber().setSelected(true);
            }
            if(selectedpiece2.equals("BLUE")){
                player2.setSelectedPiece(player2.getThief());
                player2.getThief().setSelected(true);
            }
            if(selectedpiece2.equals("YELLOW")){
                player2.setSelectedPiece(player2.getSniper());
                player2.getSniper().setSelected(true);
            }
            if(selectedpiece2.equals("GREEN")){
                player2.setSelectedPiece(player2.getHealer());
                player2.getHealer().setSelected(true);
            }






            GameState gameState=new GameState(board,player1,player2);
            gameState.setTurn(turn);
//            gameState.

            return gameState;





        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



        return null;
    }




}
