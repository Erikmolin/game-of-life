package com.erikmolin.game.board;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;

import org.junit.jupiter.api.Test;

class SquareBoardTest {

  SquareBoard sut = new SquareBoard(5, 5);

  @Test
  void outOfboundsSubBoardReturnsFullBoard() {
    assertEquals(25, sut.getSubBoard(Coordinate.of(-1, -1), Coordinate.of(10, 10)).count());
  }

  @Test
  void inBoundsSubBoardReturnsSubBoard() {
    assertEquals(9, sut.getSubBoard(Coordinate.of(1, 1), Coordinate.of(3, 3)).count());
  }

  @Test
  void toggleSquareAtTogglesSquare() {
    sut.randomizeBoard();
    var location = Coordinate.of(2, 2);
    SquareState oldState = sut.getSquareAt(location).state();
    sut.toggleSquareAt(location);
    assertNotSame(oldState, sut.getSquareAt(location).state());
  }
}