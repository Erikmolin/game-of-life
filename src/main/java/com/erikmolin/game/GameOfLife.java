package com.erikmolin.game;

import com.erikmolin.Agent;
import com.erikmolin.game.board.BoardListener;
import com.erikmolin.game.board.SquareBoard;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class GameOfLife implements Game {

  private Timer timer;
  private SquareBoard board;
  private final List<Agent> agents;
  private Agent currentAgent;

  private final Set<BoardListener> boardListeners = new HashSet<>();

  public GameOfLife(SquareBoard board) {
    this.board = board;
    this.agents = List.of(
        new HumanAgent(),
        new LifeAgent()
    );
    this.currentAgent = this.agents.get(0);

  }
  public void nextTurn() {
    this.board = currentAgent.makeMoves(board);
    int nextIndex = (agents.indexOf(currentAgent) + 1) % agents.size();
    this.currentAgent = agents.get(nextIndex);
    notifyBoardListeners();
  }

  @Override
  public SquareBoard getCurrentBoard() {
    return board;
  }

  public void togglePaused() {

    if(this.timer != null) {
      this.timer.cancel();
      this.timer = null;
    } else {
      this.timer = new Timer();
      timer.schedule(
          new TimerTask() {
            @Override
            public void run() {
              nextTurn();
            }
          },
          0,
          100
      );
    }
  }

  @Override
  public List<GameControl> getGameControls() {
    return List.of(
        new GameControl(this::clearBoard, "Clear board"),
        new GameControl(this::randomizeBoard, "Randomize board"),
        new GameControl(this::togglePaused, "Play, pause")
    );
  }

  public void registerBoardListener(BoardListener boardListener) {
    boardListeners.add(boardListener);
  }

  public void randomizeBoard() {
    this.board.randomizeBoard();
    notifyBoardListeners();
  }
  public void clearBoard() {
    this.board.clearBoard();
    notifyBoardListeners();
  }

  private void notifyBoardListeners() {
    this.boardListeners.forEach((boardListener -> boardListener.onBoardUpdate(this.board)));

  }
}
