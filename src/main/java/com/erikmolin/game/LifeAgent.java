package com.erikmolin.game;

import static com.erikmolin.game.GameOfLifeSquareState.ALIVE;
import static com.erikmolin.game.GameOfLifeSquareState.DEAD;

import com.erikmolin.Agent;
import com.erikmolin.game.board.Coordinate;
import com.erikmolin.game.board.Square;
import com.erikmolin.game.board.SquareBoard;
import java.util.stream.Stream;

public class LifeAgent implements Agent {

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

  Square applyRules(Square square, SquareBoard board) {

    long aliveNeighbours = getNeighbours(board, square.location()).filter(
        (sq -> sq.state().equals(ALIVE))).count();

    if (square.state() == ALIVE) {
      return square.withNewState(aliveNeighbours < 2 || aliveNeighbours > 3 ? DEAD : ALIVE);
    } else {
      return square.withNewState(aliveNeighbours == 3 ? ALIVE : DEAD);
    }
  }


  Stream<Square> getNeighbours(SquareBoard board, Coordinate c) {
    Coordinate upperBoxBound = Coordinate.of(
        Integer.min(c.x() + 1, board.getBoardSize().x()),
        Integer.min(c.y() + 1, board.getBoardSize().y()));
    Coordinate lowerBoxBound = Coordinate.of(
        Integer.max(0, c.x() - 1),
        Integer.max(0, c.y() - 1));

    return board.getSubBoard(lowerBoxBound,upperBoxBound).filter(
        (square) ->  !square.location().equals(c));
  }
}
