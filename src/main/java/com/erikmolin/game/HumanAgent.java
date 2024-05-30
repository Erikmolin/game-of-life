package com.erikmolin.game;

import com.erikmolin.Agent;
import com.erikmolin.game.board.SquareBoard;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class HumanAgent implements Agent {

    public HumanAgent() {
    }

    @Override
    public SquareBoard makeMoves(SquareBoard currentBoard) {
        return currentBoard;
    }
}
