package cs3500.pa03.view;

import cs3500.pa03.model.Board;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameResult;
import java.util.ArrayList;
import java.util.List;

public interface PlayGame {

  /**
   * Displays the intro and asks for size
   */
  void introDisplay();

  /**
   * display if the size is invalid
   */
  void invalidDisplay();

  /**
   * asks for fleet selection
   */
  void fleetSelection(int max);

  /**
   * called when fleetSlection failed
   *
   */
  void invalidFleet();


  public void displayGameBoard(Board player, Board ai);

  public void askForSalvo(int shots);

  void salvoFail();

  void displayShots(List<Coord> playerHit, ArrayList<Coord> playerMiss, List<Coord> aiHits);

  void didPlayerWinDisplay(GameResult result);

  //public void displaySalvoResults(List<Coord> player)

}
