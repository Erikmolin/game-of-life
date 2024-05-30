package com.erikmolin.game.board;

import static com.erikmolin.game.GameOfLifeSquareState.DEAD;

import com.erikmolin.game.GameOfLifeSquareState;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.stream.Stream;


public class SquareBoard {

  private static final Random random = new Random();
  private final Coordinate upperBound;
  private final LinkedHashMap<Coordinate, Square> squares;

  public SquareBoard(Integer sizeX, Integer sizeY) {
    this.upperBound = Coordinate.of(sizeX, sizeY);
    squares = initializeSquares(this.upperBound);
  }

  public static <T extends Enum<?>> T randomEnum(Class<T> clazz) {
    int x = random.nextInt(clazz.getEnumConstants().length);
    return clazz.getEnumConstants()[x];
  }

  public Coordinate getUpperBound() {
    return upperBound;
  }

  private LinkedHashMap<Coordinate, Square> initializeSquares(
      Coordinate upperBound) {
    final LinkedHashMap<Coordinate, Square> squares = LinkedHashMap.newLinkedHashMap(
        upperBound.x() * upperBound.y());
    for (int ix = 0; ix < upperBound.x(); ix++) {
      for (int iy = 0; iy < upperBound.y(); iy++) {
        {
          Coordinate coordinate = Coordinate.of(ix, iy);
          squares.put(coordinate, new Square(coordinate, DEAD));
        }
      }
    }
    return squares;
  }

  public void clearBoard() {
    this.squares.replaceAll((coordinate, square) -> new Square(coordinate,
        DEAD));
  }

  public void randomizeBoard() {
    this.squares.replaceAll((coordinate, square) -> new Square(coordinate,
        randomEnum(GameOfLifeSquareState.class)));
  }


  public void setSquare(Square newSquare) {
    this.squares.put(newSquare.location(), newSquare);
  }


  public Stream<Square> getAllSquares() {
    return squares.values().stream();
  }

  public SquareBoard emptyBoard() {
    return new SquareBoard(this.upperBound.x(), this.upperBound.y());
  }

  public Square getSquare(Coordinate positionToGet) {
    return squares.get(positionToGet);
  }
}
