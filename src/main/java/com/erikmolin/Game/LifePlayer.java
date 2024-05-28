package com.erikmolin.Game;

import static com.erikmolin.Game.GameOfLifeSquareState.ALIVE;
import static com.erikmolin.Game.GameOfLifeSquareState.DEAD;

import com.erikmolin.Player;
import java.util.stream.Stream;

public class LifePlayer implements Player {

  @Override
  public SquareBoard makeMoves(SquareBoard currentBoard) {
    var newBoard = currentBoard.emptyBoard();
    currentBoard
        .getAllSquares()
        .map(
            (square) -> applyRules(square, currentBoard))
        .forEach(newBoard::setSquare);
    return newBoard;
  }

  private Square applyRules(Square square, SquareBoard board) {
    Stream<Square> subBoard = board.getSubBoard(
        Coordinate.of(square.location().x() - 1, square.location().y() - 1),
        Coordinate.of(square.location().x() + 1, square.location().y() + 1));

    long aliveNeighbours = subBoard
        .filter(
            (sq -> sq.state().equals(ALIVE)))
        .count();

    if (square.state() == ALIVE) {
      return square.withNewState(aliveNeighbours < 2 || aliveNeighbours > 3 ? DEAD : ALIVE);
    } else {
      return square.withNewState(aliveNeighbours == 3 ? ALIVE : DEAD);
    }
  }
}
