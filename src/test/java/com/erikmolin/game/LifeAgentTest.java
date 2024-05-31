package com.erikmolin.game;

import static com.erikmolin.game.SquareState.ALIVE;
import static com.erikmolin.game.SquareState.DEAD;
import static junit.framework.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import com.erikmolin.game.board.Coordinate;
import com.erikmolin.game.board.Square;
import com.erikmolin.game.board.SquareBoard;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class LifeAgentTest {

  LifeAgent sut = Mockito.spy();
  SquareBoard testBoard = new SquareBoard(4, 4);

  @Test
  void cornerHasThreeNeighbours() {
    assertEquals(3, sut.getNeighbours(testBoard, (Coordinate.of(0, 0))).count());
  }

  @Test
  void edgeHasFiveNeighbours() {
    assertEquals(5, sut.getNeighbours(testBoard, (Coordinate.of(0, 1))).count());
  }

  @Test
  void middleHasEightNeighbours() {
    assertEquals(8, sut.getNeighbours(testBoard, (Coordinate.of(1, 1))).count());
  }

  @Test
  void testUpdatesOfAliveSquare() {
    testAliveSquareUpdate(5, DEAD);
    testAliveSquareUpdate(4, DEAD);
    testAliveSquareUpdate(3, ALIVE);
    testAliveSquareUpdate(2, ALIVE);
    testAliveSquareUpdate(1, DEAD);
  }

  @Test
  void testUpdatesOfDeadSquares() {
    testDeadSquareUpdate(0, DEAD);
    testDeadSquareUpdate(1, DEAD);
    testDeadSquareUpdate(2, DEAD);
    testDeadSquareUpdate(3, ALIVE);
    testDeadSquareUpdate(4, DEAD);
    testDeadSquareUpdate(5, DEAD);
  }

  @Test
  void updatesDifferentBoard() {
    SquareBoard board = new SquareBoard(3, 3);
    board.randomizeBoard();
    List<Square> originalSquares = board.getAllSquares().toList();
    SquareBoard newBoard = sut.makeMoves(board);
    assertNotEquals(board, newBoard);
    assertTrue(
        originalSquares.stream()
            .allMatch((square) -> board.getSquare(square.location()).state().equals(square.state()))
    );
  }

  private void testAliveSquareUpdate(int aliveNeighbours, SquareState expectedState) {
    var aliveSquare = new Square(Coordinate.of(1, 1), ALIVE);
    doReturn(
        Stream.generate(() -> new Square(Coordinate.of(0, 0), ALIVE)).limit(aliveNeighbours)
    ).when(sut).getNeighbours(any(), any());
    assertEquals(expectedState, sut.applyRules(aliveSquare, testBoard).state());
  }


  private void testDeadSquareUpdate(int aliveNeighbours, SquareState squareState) {
    var aliveSquare = new Square(Coordinate.of(1, 1), DEAD);
    doReturn(
        Stream.generate(() -> new Square(Coordinate.of(0, 0), ALIVE)).limit(aliveNeighbours)
    ).when(sut).getNeighbours(any(), any());
    assertEquals(squareState, sut.applyRules(aliveSquare, testBoard).state());
  }


}