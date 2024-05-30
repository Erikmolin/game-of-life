package com.erikmolin;

import static com.erikmolin.game.GameOfLifeSquareState.ALIVE;

import com.erikmolin.game.GameOfLife;
import com.erikmolin.game.board.BoardListener;
import com.erikmolin.game.board.Coordinate;
import com.erikmolin.game.board.SquareBoard;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DisplayWindow implements BoardListener {

  JPanel boardPanel;
  JFrame frame;

  HashMap<Coordinate, JPanel> squares;

  public DisplayWindow(GameOfLife game) {
    squares = HashMap.newHashMap(game.getCurrentBoard().getUpperBound().x()*game.getCurrentBoard().getUpperBound().y());
        frame = new JFrame();
    frame.setLayout(new FlowLayout(FlowLayout.LEFT));
    frame.setTitle("Game of Life");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    boardPanel = new JPanel();
    boardPanel.setLayout(new GridLayout(game.getCurrentBoard().getUpperBound().x(),
        game.getCurrentBoard().getUpperBound().y()));

    JPanel controlPanel = new JPanel();
    controlPanel.setSize(100, 100);
    controlPanel.setLayout(new GridLayout(2, 1));

    JButton randomizeBoard = new JButton("Randomize Board");
    randomizeBoard.addActionListener(event -> game.randomizeBoard());
    JButton clearBoard = new JButton("Clear Board");
    clearBoard.addActionListener(event -> game.clearBoard());
    JButton pause = new JButton("Pause / Play");
    pause.addActionListener(game::togglePaused);

    controlPanel.add(clearBoard);
    controlPanel.add(randomizeBoard);
    controlPanel.add(pause);
    game.getCurrentBoard().getAllSquares().forEach(
        (square) -> {
          JPanel squarePanel = new JPanel();
          squarePanel.setBackground(
              square.state().equals(ALIVE)
                  ? Color.black
                  : Color.gray);
          squares.put(square.location(), squarePanel);
          boardPanel.add(squarePanel
          );
        }
    );

    frame.add(boardPanel);
    frame.add(controlPanel);
    frame.pack();
    frame.setVisible(true);

  }


  public void onBoardUpdate(SquareBoard newBoard) {
    newBoard.getAllSquares().forEach(
        (square) -> {
          JPanel currentSquare = squares.get(square.location());
          currentSquare.setBackground(
              square.state().equals(ALIVE)
              ? Color.black
                  :Color.gray
          );
        }

    );
    boardPanel.validate();
    boardPanel.repaint();
  }
}
