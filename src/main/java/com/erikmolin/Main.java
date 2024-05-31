package com.erikmolin;

import com.erikmolin.game.GameOfLife;
import com.erikmolin.game.board.SquareBoard;

/**
 * Hello world!
 */
public class Main {

  public static void main(String[] args){
    GameOfLife gameOfLife = new GameOfLife(new SquareBoard(100, 100));
    new DisplayWindow(gameOfLife);
  }

}
