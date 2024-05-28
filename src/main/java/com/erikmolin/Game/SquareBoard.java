package com.erikmolin.Game;

import static com.erikmolin.Game.GameOfLifeSquareState.ALIVE;
import static com.erikmolin.Game.GameOfLifeSquareState.DEAD;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class SquareBoard {

  private static Random random = new Random();

  public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
    int x = random.nextInt(clazz.getEnumConstants().length);
    return clazz.getEnumConstants()[x];
  }
  private final Coordinate upperBound;
  private final Map<Coordinate, Square> squares;

  public SquareBoard(Integer sizeX, Integer sizeY) {
    this.upperBound = Coordinate.of(sizeX, sizeY);
    squares = initializeDefaultSquares(this.upperBound);
  }

  private SquareBoard(Coordinate upperBound, Map<Coordinate, Square> squares) {
    this.squares = squares;
    this.upperBound = upperBound;
  }

  public SquareBoard(Coordinate upperBound) {
    this.upperBound = upperBound;
    this.squares = initializeDefaultSquares(upperBound);
  }

  private Map<Coordinate, Square> initializeDefaultSquares(Coordinate upperBound) {
    final Map<Coordinate, Square> squares;
    squares = HashMap.newHashMap(upperBound.x() * upperBound.y());
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

  public SquareBoard merge(SquareBoard boardToAdd) {
    Map<Coordinate, Square> newSquares = this.getAllSquares().map((square) ->
            square.state().equals(ALIVE) || boardToAdd.getSquare(square.location()).state()
                .equals(ALIVE) ? square.withNewState(ALIVE) : square.withNewState(DEAD))
        .collect(Collectors.toMap(Square::location, Function.identity()));
    return new SquareBoard(this.upperBound, newSquares);
  }

  private Square getSquare(Coordinate location) {
    return squares.get(location);
  }


  public SquareBoard emptyBoard() {
    return new SquareBoard(this.upperBound);
  }

  public Stream<Square> getSubBoard(Coordinate c1, Coordinate c2) {
    Coordinate upperBoxBound = Coordinate.of(Integer.max(c1.x(), c2.x()), Integer.max(c1.y(), c2.y()));
    Coordinate lowerBoxBound = Coordinate.of(Integer.min(c1.x(), c2.x()), Integer.min(c1.y(), c2.y()));
    return this.getAllSquares().filter((square) -> square.location().x() > lowerBoxBound.x()
        && square.location().x() < upperBoxBound.x() && square.location().y() < upperBound.y()
        && square.location().y() > lowerBoxBound.y());
  }

  public void setSquare(Square newSquare) {
    this.squares.put(newSquare.location(), newSquare);
  }


  public Stream<Square> getAllSquares() {
    return squares.values().stream().sorted();
  }
}
