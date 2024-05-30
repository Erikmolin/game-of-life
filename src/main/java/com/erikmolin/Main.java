package com.erikmolin;

import com.erikmolin.game.GameOfLife;
import com.erikmolin.game.board.SquareBoard;

/**
 * Hello world!
 */
public class Main {

  public static void main(String[] args){
    SquareBoard squareBoard = new SquareBoard(100, 100);
    GameOfLife gameOfLife = new GameOfLife(squareBoard);
    DisplayWindow displayWindow = new DisplayWindow(gameOfLife);
    gameOfLife.registerBoardListener(displayWindow);
  }

}
