package ir.sharif.math.bp99_1.snake_and_ladder.logic;


import ir.sharif.math.bp99_1.snake_and_ladder.graphic.GraphicalAgent;
import ir.sharif.math.bp99_1.snake_and_ladder.model.*;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;
import ir.sharif.math.bp99_1.snake_and_ladder.model.prizes.Prize;

/**
 * This class is an interface between logic and graphic.
 * some methods of this class, is called from graphic.
 * DO NOT CHANGE ANY PART WHICH WE MENTION.
 */
public class LogicalAgent {
    private final ModelLoader modelLoader;
    private final GraphicalAgent graphicalAgent;
    private final GameState gameState;

    /**
     * DO NOT CHANGE CONSTRUCTOR.
     */
    public LogicalAgent() {
        this.graphicalAgent = new GraphicalAgent(this);
        this.modelLoader = new ModelLoader();
        this.gameState = loadGameState();
    }


    /**
     * NO CHANGES NEEDED.
     */
    private GameState loadGameState() {
        Board board = modelLoader.loadBord();
        Player player1 = modelLoader.loadPlayer(graphicalAgent.getPlayerNames(1), 1);
        Player player2;
        do {
            player2 = modelLoader.loadPlayer(graphicalAgent.getPlayerNames(2), 2);
        } while (player1.equals(player2));
        player1.setRival(player2);
        player2.setRival(player1);

        String name=""+player1.getName()+".."+player2.getName();
        if(modelLoader.gameExists(name)){

            return modelLoader.load(name,player1,player2);

        }
        return new GameState(board, player1, player2);


    }

    /**
     * NO CHANGES NEEDED.
     */
    public void initialize() {
        graphicalAgent.initialize(gameState);
        graphicalAgent.update(gameState);

    }

    /**
     * Give a number from graphic,( which is the playerNumber of a player
     * who clicks "ReadyButton".) you should somehow change that player state.
     * if both players are ready. then start the game.
     */
    public void readyPlayer(int playerNumber) {
        if(playerNumber==1){
            gameState.getPlayer(1).setReady(!gameState.getPlayer(1).isReady());
        }
        if(playerNumber==2){
            gameState.getPlayer(2).setReady(!gameState.getPlayer(2).isReady());
        }
        //how to start the game?
        if(gameState.getPlayer1().isReady()&&gameState.getPlayer2().isReady()){

            for (Cell cell:
                 gameState.getBoard().getCells()) {
//                System.out.println(cell);
                if(gameState.getBoard().getStartingCells().containsKey(cell)){


                    if(cell.getColor().equals(Color.RED)){
                        gameState.getPlayer(gameState.getBoard().getStartingCells().get(cell)).getPieces().get(0).setCurrentCell(cell);
                        gameState.getBoard().getCell(cell.getX(),cell.getY()).setPiece(gameState.getPlayer(gameState.getBoard().getStartingCells().get(cell)).getPieces().get(0));
                    }
                    if(cell.getColor().equals(Color.BLUE)){
                        gameState.getPlayer(gameState.getBoard().getStartingCells().get(cell)).getPieces().get(1).setCurrentCell(cell);
                        gameState.getBoard().getCell(cell.getX(),cell.getY()).setPiece(gameState.getPlayer(gameState.getBoard().getStartingCells().get(cell)).getPieces().get(1));
                    }
                    if(cell.getColor().equals(Color.GREEN)){
                        gameState.getPlayer(gameState.getBoard().getStartingCells().get(cell)).getPieces().get(2).setCurrentCell(cell);
                        gameState.getBoard().getCell(cell.getX(),cell.getY()).setPiece(gameState.getPlayer(gameState.getBoard().getStartingCells().get(cell)).getPieces().get(2));
                    }
                    if(cell.getColor().equals(Color.YELLOW)){
                        gameState.getPlayer(gameState.getBoard().getStartingCells().get(cell)).getPieces().get(3).setCurrentCell(cell);
                        gameState.getBoard().getCell(cell.getX(),cell.getY()).setPiece(gameState.getPlayer(gameState.getBoard().getStartingCells().get(cell)).getPieces().get(3));
                    }







                }
            }

            gameState.nextTurn();
            
        }

        // dont touch this line
        graphicalAgent.update(gameState);
    }

    /**
     * give x,y (coordinates of a cell) :
     * you should handle if user want to select a piece
     * or already selected a piece and now want to move it to a new cell
     */
    // ***
    public void selectCell(int x, int y) {
        if(gameState.getCurrentPlayer().isDicePlayedThisTurn()) {

            if (gameState.getBoard().getCell(x, y).getPiece() != null) {
                if (gameState.getCurrentPlayer().getSelectedPiece() == null) {
                    if(!gameState.getBoard().getCell(x,y).getPiece().isDead()) {
                        if (gameState.getCurrentPlayer().getPlayerNumber() == gameState.getBoard().getCell(x, y).getPiece().getPlayer().getPlayerNumber()) {
                            gameState.getBoard().getCell(x, y).getPiece().setSelected(true);
                            gameState.getCurrentPlayer().setSelectedPiece(gameState.getBoard().getCell(x, y).getPiece());
                            modelLoader.saveThisGame(gameState);
                        }
                    }
                }else if(gameState.getCurrentPlayer().getSelectedPiece()!=null){
                    if(gameState.getCurrentPlayer().getSelectedPiece().getColor().equals(Color.GREEN)) {
                        if (gameState.getCurrentPlayer().getSelectedPiece().equals(gameState.getBoard().getCell(x, y).getPiece())) {
                            gameState.getBoard().getCell(x, y).getPiece().setSelected(false);
                            gameState.getCurrentPlayer().setSelectedPiece(null);
                            modelLoader.saveThisGame(gameState);
                        } else {
//                            System.out.println(gameState.getCurrentPlayer().getHealer().isOn());
                            if (gameState.getCurrentPlayer().getHealer().isOn()) {
                                System.out.println("u can heal");
                                if (gameState.getCurrentPlayer().getHealer().canHeal(gameState.getBoard().getCell(x, y).getPiece(), gameState.getCurrentPlayer().getMoveLeft())) {
                                    gameState.getCurrentPlayer().getHealer().heal(gameState.getBoard().getCell(x, y).getPiece());
                                    System.out.println("the healer heals");
                                    gameState.nextTurn();
                                    modelLoader.saveThisGame(gameState);
                                }
                            }
                        }
                    }

                    else if(gameState.getCurrentPlayer().getSelectedPiece().getColor().equals(Color.YELLOW)) {
                        if (gameState.getCurrentPlayer().getSelectedPiece().equals(gameState.getBoard().getCell(x, y).getPiece())) {
                            gameState.getBoard().getCell(x, y).getPiece().setSelected(false);
                            gameState.getCurrentPlayer().setSelectedPiece(null);
                            modelLoader.saveThisGame(gameState);
                        } else {
                            if (gameState.getCurrentPlayer().getSniper().isOn()) {
                                System.out.println("u can kill");
                                if (gameState.getCurrentPlayer().getSniper().canKill(gameState.getBoard().getCell(x, y).getPiece(), gameState.getCurrentPlayer().getMoveLeft())) {
                                    gameState.getCurrentPlayer().getSniper().kill(gameState.getBoard().getCell(x, y).getPiece());
                                    System.out.println("the killer kills");
                                    gameState.nextTurn();
                                    modelLoader.saveThisGame(gameState);
                                }
                            }
                        }
                    }else if(gameState.getCurrentPlayer().getSelectedPiece().getColor().equals(Color.RED)){
                        if (gameState.getCurrentPlayer().getSelectedPiece().equals(gameState.getBoard().getCell(x, y).getPiece())){
                            if(gameState.getCurrentPlayer().getBomber().isOn()) {
                                System.out.println("boom boom");
                                gameState.getCurrentPlayer().getBomber().blowUp();
                                gameState.getBoard().getCell(x, y).getPiece().setSelected(false);
                                gameState.getCurrentPlayer().setSelectedPiece(null);
                                gameState.nextTurn();
                                modelLoader.saveThisGame(gameState);

                            }
                            else{
                                gameState.getBoard().getCell(x, y).getPiece().setSelected(false);
                                modelLoader.saveThisGame(gameState);
                                gameState.getCurrentPlayer().setSelectedPiece(null);
                                modelLoader.saveThisGame(gameState);
                            }
                        }
                    }
                    else if(gameState.getCurrentPlayer().getSelectedPiece().getColor().equals(Color.BLUE)){
                        if (gameState.getCurrentPlayer().getSelectedPiece().equals(gameState.getBoard().getCell(x, y).getPiece())){
                            if(gameState.getCurrentPlayer().getSelectedPiece().getCurrentCell().getPrize()!=null && !gameState.getCurrentPlayer().getThief().isHasPrize()){
                                System.out.println("stealing this prize");
                                gameState.getCurrentPlayer().getThief().stealPrize(gameState.getCurrentPlayer().getSelectedPiece().getCurrentCell());
                                gameState.getCurrentPlayer().getSelectedPiece().getCurrentCell().setPrize(null);
                                gameState.getBoard().getCell(x, y).getPiece().setSelected(false);
                                gameState.getCurrentPlayer().setSelectedPiece(null);
                                gameState.nextTurn();
                                modelLoader.saveThisGame(gameState);
                            }
                            else if(gameState.getCurrentPlayer().getSelectedPiece().getCurrentCell().getPrize()==null && gameState.getCurrentPlayer().getThief().isHasPrize()){
                                System.out.println("we gonna leave the prize here");


                                gameState.getCurrentPlayer().getThief().leavePrize(gameState.getCurrentPlayer().getSelectedPiece().getCurrentCell());
                                gameState.getBoard().getCell(x, y).getPiece().setSelected(false);
                                gameState.getCurrentPlayer().setSelectedPiece(null);
                                gameState.nextTurn();
                                modelLoader.saveThisGame(gameState);

                            }
                            else {
                                gameState.getBoard().getCell(x, y).getPiece().setSelected(false);
                                gameState.getCurrentPlayer().setSelectedPiece(null);
                                modelLoader.saveThisGame(gameState);
                            }
                        }

                    }

                    else if (gameState.getCurrentPlayer().getSelectedPiece().equals(gameState.getBoard().getCell(x, y).getPiece())) {

                        gameState.getBoard().getCell(x, y).getPiece().setSelected(false);
                        gameState.getCurrentPlayer().setSelectedPiece(null);
                        modelLoader.saveThisGame(gameState);
                    }

                }


            } else {
                if (gameState.getCurrentPlayer().getSelectedPiece() != null) {
                    if (!gameState.getCurrentPlayer().getSelectedPiece().getColor().equals(Color.BLUE)) {
//                        System.out.println("selected piece aint blue");
//                        System.out.println("u chose the cell"+x+" "+y);
//                        System.out.println("ur in"+gameState.getCurrentPlayer().getSelectedPiece().getCurrentCell().getX()+" "+gameState.getCurrentPlayer().getSelectedPiece().getCurrentCell().getY());
                        if (gameState.getCurrentPlayer().getSelectedPiece().isValidMove(gameState.getBoard().getCell(x, y), gameState.getCurrentPlayer().getMoveLeft())) {
                            gameState.getCurrentPlayer().getSelectedPiece().moveTo(gameState.getBoard().getCell(x, y));
                            gameState.nextTurn();
                            modelLoader.saveThisGame(gameState);
                        }
                    } else if (gameState.getCurrentPlayer().getSelectedPiece().getColor().equals(Color.BLUE)) {
//                        System.out.println("we gonna move blue");
                        int q = gameState.getCurrentPlayer().getMoveLeft();
//                        System.out.println(q);
//                        System.out.println("u chose the cell"+x+" "+y);
//                        System.out.println("ur in"+gameState.getCurrentPlayer().getSelectedPiece().getCurrentCell().getX()+" "+gameState.getCurrentPlayer().getSelectedPiece().getCurrentCell().getY());
                        if ((gameState.getCurrentPlayer().getSelectedPiece().getCurrentCell().getX() == x && gameState.getCurrentPlayer().getSelectedPiece().getCurrentCell().getY() - y == q)
                                || (gameState.getCurrentPlayer().getSelectedPiece().getCurrentCell().getX() == x && y - gameState.getCurrentPlayer().getSelectedPiece().getCurrentCell().getY() == q)
                                || (gameState.getCurrentPlayer().getSelectedPiece().getCurrentCell().getY() == y && gameState.getCurrentPlayer().getSelectedPiece().getCurrentCell().getX() - x == q)
                                || (gameState.getCurrentPlayer().getSelectedPiece().getCurrentCell().getY() == y && x - gameState.getCurrentPlayer().getSelectedPiece().getCurrentCell().getX() == q)) {


                            gameState.getCurrentPlayer().getSelectedPiece().moveTo(gameState.getBoard().getCell(x, y));
                            modelLoader.saveThisGame(gameState);
                            gameState.nextTurn();
                            modelLoader.saveThisGame(gameState);
                        }

                    }
                }
            }


        }

        // dont touch this line
        graphicalAgent.update(gameState);
        checkForEndGame();
    }

    /**
     * check for endgame and specify winner
     * if player one in winner set winner variable to 1
     * if player two in winner set winner variable to 2
     * If the game is a draw set winner variable to 3
     */
    private void checkForEndGame() {
        if (gameState.getTurn()==40+1) {
            System.out.println("game ends");

            String name=gameState.getPlayer1().getName()+".."+gameState.getPlayer2().getName();
                                    if(gameState.getTurn()==41)modelLoader.deleteGameFile(name);

            // game ends

            int winner = 1;
            if(gameState.getPlayer(1).getScore()<gameState.getPlayer(2).getScore()){
                winner=2;
            }
            if(gameState.getPlayer(1).getScore()==gameState.getPlayer(2).getScore()){
                winner=3;
            }



            // your code


            // dont touch it
            graphicalAgent.playerWin(winner);
            /* save players*/
            modelLoader.savePlayer(gameState.getPlayer1());
            modelLoader.savePlayer(gameState.getPlayer2());
            modelLoader.archive(gameState.getPlayer1(), gameState.getPlayer2());
            LogicalAgent logicalAgent = new LogicalAgent();
            logicalAgent.initialize();
        }
    }


    /**
     * Give a number from graphic,( which is the playerNumber of a player
     * who left clicks "dice button".) you should roll his/her dice
     * and update *****************
     */
    public void rollDice(int playerNumber) {
        if(gameState.isStarted()) {
            if (!(gameState.getPlayer(playerNumber).isDicePlayedThisTurn())) {
                if((playerNumber==1 && gameState.getTurn()%2==1)||(playerNumber==2 && gameState.getTurn()%2==0)) {

                    int q = gameState.getPlayer(playerNumber).getDice().roll();
                    gameState.getPlayer(playerNumber).setMoveLeft(q);
                    modelLoader.saveThisGame(gameState);
                    if(q==2){
                        for(int i=1;i<=6;i++){
                        gameState.getPlayer(playerNumber).getDice().setChance(i,1);
                        }
                        modelLoader.saveThisGame(gameState);
                    }

                    gameState.getPlayer(playerNumber).getDiceNums().add(q);
                    if(gameState.getPlayer(playerNumber).getDiceNums().size()==2){
                        if(gameState.getPlayer(playerNumber).getDiceNums().get(0)==gameState.getPlayer(playerNumber).getDiceNums().get(1)){
                            gameState.getPlayer(playerNumber).getDice().addChance(q,1);
                        }
                        gameState.getPlayer(playerNumber).getDiceNums().remove(0);
                        gameState.getPlayer(playerNumber).getDiceNums().remove(0);
                        modelLoader.saveThisGame(gameState);
                    }



                    if(q==1) {
                        if (!gameState.getPlayer(playerNumber).getPieces().get(2).isDead()) {
                            System.out.println("healer is on");
                            gameState.getPlayer(playerNumber).getPieces().get(2).setOn(true);
                            modelLoader.saveThisGame(gameState);
                        }
                    }
                    if(q==5) {
                        if (!gameState.getPlayer(playerNumber).getPieces().get(3).isDead()) {
                            System.out.println("sniper is on");
                            gameState.getPlayer(playerNumber).getPieces().get(3).setOn(true);
                            modelLoader.saveThisGame(gameState);
                        }
                    }
                    if(q==3) {
                        if (!gameState.getPlayer(playerNumber).getPieces().get(0).isDead()) {
                            System.out.println("bomber is on");
                            gameState.getPlayer(playerNumber).getPieces().get(0).setOn(true);
                            modelLoader.saveThisGame(gameState);
                        }
                    }




                    gameState.getPlayer(playerNumber).setDicePlayedThisTurn(true);
                    modelLoader.saveThisGame(gameState);

                    if (gameState.getPlayer(playerNumber).getMoveLeft() == 6) {

                        gameState.getPlayer(playerNumber).applyOnScore(4);
                        modelLoader.saveThisGame(gameState);
                    }

                    if(!gameState.getCurrentPlayer().hasMove(gameState.getBoard(),gameState.getCurrentPlayer().getMoveLeft())){
                        gameState.getCurrentPlayer().applyOnScore(-3);
                        gameState.nextTurn();
                        modelLoader.saveThisGame(gameState);
                    }


                }

            }
        }

        // dont touch this line
        graphicalAgent.update(gameState);
    }


    /**
     * Give a number from graphic,( which is the playerNumber of a player
     * who right clicks "dice button".) you should return the dice detail of that player.
     * you can use method "getDetails" in class "Dice"(not necessary, but recommended )
     */
    public String getDiceDetail(int playerNumber) {

       return gameState.getPlayer(playerNumber).getDice().getDetails();

    }
}
