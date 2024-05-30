package com.erikmolin.game;

import com.erikmolin.game.board.SquareBoard;
import java.awt.event.ActionEvent;

public interface Game {
    SquareBoard getCurrentBoard();

    void togglePaused(ActionEvent event);
}
