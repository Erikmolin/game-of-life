package com.erikmolin.Game;

public record Coordinate(Integer x, Integer y) {

  public static Coordinate of(Integer x, Integer y) {
    return new Coordinate(x, y);
  }

}
