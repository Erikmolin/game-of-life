package com.erikmolin.game.board;

import com.erikmolin.game.GameOfLifeSquareState;

public record Square(Coordinate location, GameOfLifeSquareState state) {


  public Square withNewState(GameOfLifeSquareState newState) {
    return this.state.equals(newState)
        ? this
        : new Square(this.location, newState);
  }

}
