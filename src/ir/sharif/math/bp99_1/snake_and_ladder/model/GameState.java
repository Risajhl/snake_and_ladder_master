package ir.sharif.math.bp99_1.snake_and_ladder.model;

import ir.sharif.math.bp99_1.snake_and_ladder.Save;
import ir.sharif.math.bp99_1.snake_and_ladder.util.Config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class GameState implements Save {
    private final Board board;
    private final Player player1;
    private final Player player2;
    private int turn;


    public GameState(Board board, Player player1, Player player2) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        turn = 0;
    }

    public Board getBoard() {
        return board;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Player getPlayer(int i) {
        if (i == 1) return player1;
        else if (i == 2) return player2;
        else return null;
    }

    public boolean isStarted() {
        return turn != 0;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {this.turn=turn;}


    /**
     * return null if game is not started.
     * else return a player who's turn is now.
     */
    public Player getCurrentPlayer() {


        if(this.turn==0){
            return null;
        }else{
            if(this.turn%2!=0){
                return player1;
            }
            if(this.turn%2==0){
                return player2;
            }
        }



        return null;
    }


    /**
     * finish current player's turn and update some fields of this class;
     * you can use method "endTurn" in class "Player" (not necessary, but recommanded)
     */
    public void nextTurn() {
        if(turn==0){
            turn++;
        }else {
            getCurrentPlayer().endTurn();
            turn++;
        }

    }



    @Override
    public String toString() {
        return "GameState{" +
                "board=" + board +
                ", playerOne=" + player1 +
                ", playerTwo=" + player2 +
                ", turn=" + turn +
                '}';
    }

    @Override
    public String save() {
        String turn="turn: ";
        turn=turn+""+this.turn+"\n";
        return turn;
    }
}
