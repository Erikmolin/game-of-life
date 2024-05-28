package com.erikmolin;

import static com.erikmolin.Game.GameOfLifeSquareState.ALIVE;

import com.erikmolin.Game.Coordinate;
import com.erikmolin.Game.Square;
import com.erikmolin.Game.SquareBoard;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Hello world!
 */
public class Main {

  public static void main(String[] args) throws InterruptedException {
    SquareBoard squareBoard = new SquareBoard(10, 10);
    squareBoard.setSquare(Square.of(Coordinate.of(0, 0), ALIVE));
    squareBoard.setSquare(Square.of(Coordinate.of(1, 0), ALIVE));
    squareBoard.setSquare(Square.of(Coordinate.of(0, 1), ALIVE));
    GameOfLife gameOfLife = new GameOfLife(squareBoard);
    while (true){
      TimeUnit.MILLISECONDS.sleep(500);
      gameOfLife.nextTurn();
      gameOfLife.nextTurn();
      printBoard(gameOfLife);
    }

  }

  private static void printBoard(GameOfLife gameOfLife) {
    String string = gameOfLife.getCurrentBoard().getAllSquares().map(
        (square) -> {

          if (square.location().y().equals(0)) {
            return "\n".concat(square.state().equals(ALIVE) ? "X" : "0");
          } else {
            return square.state().equals(ALIVE) ? "X" : "0";
          }
        }
    ).collect(Collectors.joining());

    System.out.println(string);
  }
}
