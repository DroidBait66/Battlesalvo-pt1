package cs3500.pa03.controller;

import cs3500.pa03.model.AiPlayer;
import cs3500.pa03.model.CellStatus;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.ManualPlayer;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import cs3500.pa03.model.Shots;
import cs3500.pa03.model.ShotsAi;
import cs3500.pa03.view.PlayGame;
import cs3500.pa03.view.PlayGameImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ControllerImpl implements Controller {

  private HashMap<ShipType, Integer> specs = new HashMap<>();
  private ManualPlayer player1;
  private AiPlayer player2;

  private int width = 0;
  private int height = 0;
  private int playerShipsRemaining;
  private int aiShipsRemaining;
  private List<Ship> playerShips;
  private List<Ship> aiShips;
  private Shots playerSalvos = new Shots();
  private ShotsAi aiSalvos = new ShotsAi();
  Scanner scanner = new Scanner(System.in);

  private List<Coord> playerShots;
  private List<Coord> aiShots;




  /**
   * first method that should be ran. Sets the variables width and height equal the w and h of the
   * game
   */
  @Override
  public void start() {
    PlayGame playGame = new PlayGameImpl(System.out);
    playGame.introDisplay();


    int preHeight = scanner.nextInt();
    int preWidth = scanner.nextInt();

    if (Math.min(preHeight, preWidth) < 6 || Math.max(preHeight, preWidth) > 15) {
      new PlayGameImpl(System.out).invalidDisplay();
      start();
    } else {
      height = preHeight;
      width = preWidth;
    }
  }

  /**
   * used to create the specs hashmap. player selects fleet
   *
   * @param maxShips max number of ships avaliable
   */
  @Override
  public void fleetSelection(int maxShips) {
    PlayGame pickFleet = new PlayGameImpl(System.out);
    pickFleet.fleetSelection(maxShips);


    clearSpecs();
    int count = 0;
    int[] numShips =  new int[4];
    while (scanner.hasNextInt() && count < 4) {
      numShips[count] = scanner.nextInt();
      count += 1;
    }
    playerShipsRemaining = numShips[0] + numShips[1] + numShips[2] + numShips[3];
    if (playerShipsRemaining > maxShips ||
        numShips[0] == 0 || numShips[1] == 0 || numShips[2] == 0 || numShips[3] == 0) {
      new PlayGameImpl(System.out).invalidFleet();
      fleetSelection(maxShips);
    } else {
      specs.put(ShipType.CARRIER, numShips[0]);
      specs.put(ShipType.BATTLESHIP, numShips[1]);
      specs.put(ShipType.DESTROYER, numShips[2]);
      specs.put(ShipType.SUBMARINE, numShips[3]);
      aiShipsRemaining = playerShipsRemaining;

    }

  }

  /**
   * Clears the specs hashmap, helper for fleet selection
   */
  private void clearSpecs() {
    specs.put(ShipType.CARRIER, 0);
    specs.put(ShipType.BATTLESHIP, 0);
    specs.put(ShipType.DESTROYER, 0);
    specs.put(ShipType.SUBMARINE, 0);
  }

  /**
   * Gets the max number of ships allowed. needed to make fleetSelection work
   */
  public int getMaxShips() {
    return Math.min(height, width);
  }

  /**
   * creates the initial board for the game
   */
  @Override
  public void boardCreation() {
    player1 = new ManualPlayer("Player", playerShipsRemaining, new Random(), playerSalvos);
    player2 = new AiPlayer("Ai", aiShipsRemaining, new Random(), aiSalvos);

    playerShips = player1.setup(height, width, specs);
    aiShips = player2.setup(height, width, specs);

    PlayGame playGame = new PlayGameImpl(System.out);
    playGame.displayGameBoard(playerSalvos.boardGetter(), aiSalvos.boardGetter());

  }

  @Override
  public void getPlayerSalvo() {
    playerSalvos.setOpponentEmpty(aiSalvos.boardGetter());
    int limit = playerSalvos.limitShots();
    PlayGame playGame = new PlayGameImpl(System.out);
    playGame.askForSalvo(limit);

    int[][] salvoInput = new int[limit][2];
    int count = 0;
    int row = 0;
    while (scanner.hasNextInt() && count < limit * 2) {
      salvoInput[row][0] = scanner.nextInt();
      count += 1;
      salvoInput[row][1] = scanner.nextInt();
      count += 1;
      row += 1;
    }


    if (count < limit * 2 - 1) {
      new PlayGameImpl(System.out).salvoFail();
      getPlayerSalvo();
    } else {
      playerSalvos.setSalvo(salvoInput);
      playerShots = player1.takeShots();
      aiSalvos.setOpponentEmpty(playerSalvos.boardGetter());
      aiShots = player2.takeShots();
      for (Coord c : playerShots) {
        System.out.println(c.getX() + " " + c.getY());
      }
      if (!validHits(playerShots)) {
        new PlayGameImpl(System.out).salvoFail();
        getPlayerSalvo();
      }
    }
  }

  private boolean validHits(List<Coord> list) {
    for (Coord c : list) {
      if (c.getStatus().equals(CellStatus.HIT_) || c.getStatus().equals(CellStatus.MISS)) {
        return false;
      }
    }
    for (int i = 1; i < list.size(); i += 1) {
      for (int j = 0; j < i; j += 1) {
        if (list.get(i).getX() == (list.get(j).getX())
            && list.get(i).getY() == (list.get(j).getY())) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public void printSalvos() {
    PlayGame playGame = new PlayGameImpl(System.out);
    playGame.displayShots(player2.reportDamage(playerShots),aiSalvos.getMissedShots(),
        player1.reportDamage(aiShots));
    playGame.displayGameBoard(playerSalvos.boardGetter(), aiSalvos.boardGetter());

    playerShipsRemaining = playerSalvos.getRemainingShips();
    aiShipsRemaining = aiSalvos.getRemainingShips();




  }

  @Override
  public void gameResult() {
    PlayGame playGame = new PlayGameImpl(System.out);
    if (playerShipsRemaining <= 0 && aiShipsRemaining >= 0) {
      playGame.didPlayerWinDisplay(GameResult.LOST);
    } else if (playerShipsRemaining >= 0 && aiShipsRemaining <= 0) {
      playGame.didPlayerWinDisplay(GameResult.WON);
    } else {
      playGame.didPlayerWinDisplay(GameResult.TIED);
    }
  }



  /**
   * Checks if either the Ai or the player still have shots left
   *
   * @return boolean true or false
   */
  @Override
  public boolean isGameOver() {

    return playerSalvos.getRemainingShips() <= 0 || aiSalvos.getRemainingShips() <= 0;
  }
}
