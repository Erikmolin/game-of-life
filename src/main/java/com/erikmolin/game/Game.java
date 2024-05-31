package com.erikmolin.game;

import com.erikmolin.game.board.BoardListener;
import com.erikmolin.game.board.SquareBoard;
import java.awt.event.ActionEvent;
import java.util.List;

public interface Game {

  SquareBoard getCurrentBoard();

  List<GameControl> getGameControls();

  void registerBoardListener(BoardListener boardListener);
}
