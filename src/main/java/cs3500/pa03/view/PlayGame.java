package cs3500.pa03.view;

import cs3500.pa03.model.Board;

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
   * @param msg reason for fail
   */
  void invalidFleet(String msg);


  public void displayGameActual(Board player, Board ai);

}