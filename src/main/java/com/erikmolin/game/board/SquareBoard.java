package com.erikmolin.game.board;

import static com.erikmolin.game.SquareState.ALIVE;
import static com.erikmolin.game.SquareState.DEAD;
import static java.lang.Math.min;

import com.erikmolin.game.SquareState;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.stream.Stream;


public class SquareBoard {

  private static final Random random = new Random();
  private final Coordinate boardSize;

  private final ArrayList<ArrayList<Square>> squares;

  public SquareBoard(Integer sizeX, Integer sizeY) {
    this.boardSize = Coordinate.of(sizeX, sizeY);
    squares = initializeSquares(this.boardSize);
  }

  public static <T extends Enum<?>> T randomEnum(Class<T> clazz) {
    int x = random.nextInt(clazz.getEnumConstants().length);
    return clazz.getEnumConstants()[x];
  }

  public Coordinate getBoardSize() {
    return boardSize;
  }

  private ArrayList<ArrayList<Square>> initializeSquares(
      Coordinate upperBound) {
    final ArrayList<ArrayList<Square>> squares = new ArrayList<>();
    for (int ix = 0; ix < upperBound.x(); ix++) {
      squares.add(new ArrayList<>());
      for (int iy = 0; iy < upperBound.y(); iy++) {
        {
          Coordinate coordinate = Coordinate.of(ix, iy);
          squares.get(ix).add(new Square(coordinate, DEAD));
        }
      }
    }
    return squares;
  }

  public void clearBoard() {
    this.squares.forEach((row) -> row.replaceAll((square) -> square.withNewState(DEAD)));
  }

  public void randomizeBoard() {
    this.squares.forEach((row) -> row.replaceAll(
        (square) -> square.withNewState(randomEnum(SquareState.class))));

  }


  public void setSquare(Square newSquare) {
    this.squares.get(newSquare.location().x())
        .set(newSquare.location().y(), newSquare);
  }


  public Stream<Square> getSubBoard(Coordinate lowerBound, Coordinate upperBound) {
    return squares.subList(lowerBound.x(), min(upperBound.x() + 1, this.boardSize.x()))
        .stream().flatMap(
            (y) -> y.subList(lowerBound.y(), min(upperBound.y() + 1, this.boardSize.y())).stream()
        );
  }

  public Stream<Square> getAllSquares() {
    return squares.stream().flatMap(Collection::stream);
  }

  public SquareBoard emptyBoard() {
    return new SquareBoard(this.boardSize.x(), this.boardSize.y());
  }

  public Square getSquare(Coordinate positionToGet) {
    return squares.get(positionToGet.x()).get(positionToGet.y());
  }

  public Square toggleSquareAt(Coordinate coordinate) {
    Square newSquare = getSquare(coordinate).invertState();
    setSquare(newSquare);
    return newSquare;
  }
}
