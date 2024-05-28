package com.erikmolin.Game;

public record Square(Coordinate location, GameOfLifeSquareState state) implements Comparable<Square> {

  public static Square of(Coordinate location, GameOfLifeSquareState state) {
    return new Square(location, state);
  }
  public Square withNewState(GameOfLifeSquareState newState) {
    return new Square(this.location, newState);
  }


  @Override
  public int compareTo(Square o) {
    int xDiff = this.location().x() - o.location().x();
    int yDiff = this.location().y() - o.location().y();

    return xDiff != 0 ? xDiff : yDiff;
  }
}
