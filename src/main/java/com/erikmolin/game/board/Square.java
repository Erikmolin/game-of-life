package com.erikmolin.game.board;

import static com.erikmolin.game.SquareState.ALIVE;
import static com.erikmolin.game.SquareState.DEAD;

import com.erikmolin.game.SquareState;

public record Square(Coordinate location, SquareState state) {


  public Square withNewState(SquareState newState) {
    return this.state.equals(newState)
        ? this
        : new Square(this.location, newState);
  }

  public Square invertState() {
    return withNewState(this.state() == ALIVE ? DEAD : ALIVE);
  }

}
