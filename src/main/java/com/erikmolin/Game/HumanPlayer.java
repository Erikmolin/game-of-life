package com.erikmolin.Game;

import com.erikmolin.Player;

public class HumanPlayer implements Player {

    public HumanPlayer(SquareBoard desiredBoard) {
        this.desiredBoard = desiredBoard;
    }

    private SquareBoard desiredBoard;
    @Override
    public SquareBoard makeMoves(SquareBoard currentBoard) {
        SquareBoard newBoard = currentBoard.merge(desiredBoard);
        desiredBoard = desiredBoard.emptyBoard();
        return newBoard;
    }
}
