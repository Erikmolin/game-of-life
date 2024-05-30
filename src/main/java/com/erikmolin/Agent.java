package com.erikmolin;

import com.erikmolin.game.board.SquareBoard;

public interface Agent {

     SquareBoard makeMoves(SquareBoard currentBoard);
}
