package com.erikmolin;

import static org.junit.jupiter.api.Assertions.*;

import com.erikmolin.game.GameOfLife;
import com.erikmolin.game.board.SquareBoard;
import org.junit.jupiter.api.Test;

class DisplayWindowTest {


  @Test
  public void doesntCrash() {
    new DisplayWindow(new GameOfLife(new SquareBoard(1, 1)));
  }
}