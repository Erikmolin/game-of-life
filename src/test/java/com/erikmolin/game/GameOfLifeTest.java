package com.erikmolin.game;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.erikmolin.game.board.BoardListener;
import com.erikmolin.game.board.SquareBoard;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


class GameOfLifeTest implements BoardListener {

  GameOfLife sut = new GameOfLife(new SquareBoard(10, 10));

  @Test
  public void listenerCalledOnUpdates() {
    GameOfLifeTest spy = Mockito.spy(this);
    sut.registerBoardListener(spy);
    sut.nextTurn();
    verify(spy, times(1)).onBoardUpdate(any());
    sut.randomizeBoard();
    verify(spy, times(2)).onBoardUpdate(any());
    sut.clearBoard();
    verify(spy, times(3)).onBoardUpdate(any());
  }

  @Override
  public void onBoardUpdate(SquareBoard newBoard) {

  }
}