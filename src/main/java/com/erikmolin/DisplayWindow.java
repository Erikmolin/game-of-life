package com.erikmolin;

import static com.erikmolin.game.SquareState.ALIVE;

import com.erikmolin.game.GameOfLife;
import com.erikmolin.game.board.BoardListener;
import com.erikmolin.game.board.Coordinate;
import com.erikmolin.game.board.Square;
import com.erikmolin.game.board.SquareBoard;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DisplayWindow implements BoardListener, MouseListener {

  JFrame frame;

  JPanel boardPanel;
  JPanel controlPanel;

  BiMap<Coordinate, JPanel> squares;

  GameOfLife game;
  private boolean pressed;

  public DisplayWindow(GameOfLife game) {
    this.game = game;
    squares = HashBiMap.create(
        game.getCurrentBoard().getBoardSize().x() * game.getCurrentBoard().getBoardSize().y());
    frame = new JFrame();
    frame.setLayout(new FlowLayout(FlowLayout.LEFT));
    frame.setTitle("Game of Life");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    setupControlPanel(game);
    setupBoardPanel(game);

    frame.pack();
    frame.setVisible(true);
  }

  private void setupBoardPanel(GameOfLife game) {
    boardPanel = new JPanel();
    boardPanel.setLayout(new GridLayout(game.getCurrentBoard().getBoardSize().x(),
        game.getCurrentBoard().getBoardSize().y()));

    game.getCurrentBoard().getAllSquares().forEach((square) -> {
      JPanel squarePanel = new JPanel();
      squarePanel.setBackground(square.state().equals(ALIVE) ? Color.black : Color.gray);
      squares.put(square.location(), squarePanel);
      squarePanel.addMouseListener(this);
      boardPanel.add(squarePanel);
    });
    frame.add(boardPanel);
    
  }

  private void setupControlPanel(GameOfLife game) {
    if (this.game.getGameControls().isEmpty()) {
      return;
    }
    this.controlPanel = new JPanel();
    controlPanel.setSize(100, 100);
    controlPanel.setLayout(new GridLayout(game.getGameControls().size(), 1));

    game.getGameControls().forEach((gameControl) -> {
      JButton button = new JButton(gameControl.label());
      button.addActionListener((event) -> {
        gameControl.onClick().run();
      });
      controlPanel.add(button);
    });
    frame.add(controlPanel);

  }


  public void onBoardUpdate(SquareBoard newBoard) {
    newBoard.getAllSquares().forEach(this::setBackgroundFromState);
    boardPanel.validate();
    boardPanel.repaint();
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    Coordinate coordinate = squares.inverse().get(e.getSource());
    Square newSquare = this.game.getCurrentBoard().toggleSquareAt(coordinate);
    setBackgroundFromState(newSquare);
  }

  @Override
  public void mousePressed(MouseEvent e) {
    this.pressed = true;
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    this.pressed = false;
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    if (this.pressed) {
      Coordinate coordinate = squares.inverse().get(e.getSource());
      Square newSquare = this.game.getCurrentBoard().toggleSquareAt(coordinate);
      setBackgroundFromState(newSquare);
    }
  }

  @Override
  public void mouseExited(MouseEvent e) {

  }

  private void setBackgroundFromState(Square square) {
    JPanel currentSquare = squares.get(square.location());
    currentSquare.setBackground(square.state().equals(ALIVE) ? Color.black : Color.gray);
  }
}
