package com.erikmolin.game;

import com.erikmolin.Agent;
import com.erikmolin.game.board.SquareBoard;

public class HumanAgent implements Agent {

    public HumanAgent() {
    }

    @Override
    public SquareBoard makeMoves(SquareBoard currentBoard) {
        return currentBoard;
    }
}
