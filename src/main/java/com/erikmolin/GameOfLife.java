package com.erikmolin;

import com.erikmolin.Game.HumanPlayer;
import com.erikmolin.Game.LifePlayer;
import com.erikmolin.Game.SquareBoard;
import com.erikmolin.Game.Game;

import java.util.List;

public class GameOfLife implements Game {

    private boolean paused = true;
    private SquareBoard board;
    private List<Player> players;
    private Player currentPlayer;
    public void nextTurn(){
        this.board = currentPlayer.makeMoves(board);
        int nextIndex = (players.indexOf(currentPlayer) + 1) % players.size();
        this.currentPlayer = players.get(nextIndex);
    }

    public GameOfLife(SquareBoard board) {
        this.board = board;
        this.players = List.of(
            new HumanPlayer(board.emptyBoard()),
            new LifePlayer()
        );
        this.currentPlayer = this.players.get(0);
    }

    @Override
    public SquareBoard getCurrentBoard() {
        return board;
    }


    @Override
    public void setPaused(boolean paused) {
        this.paused = paused;
    }
}
