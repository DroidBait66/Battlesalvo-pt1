package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Ai Player class
 */
public class AiPlayer implements Player {
  String playerName;
  int shipsRemaining;
  public Board gameBoard;
  private ArrayList<ArrayList<CellStatus>> opBoard;
  private List<Ship> playerShips;
  Random rand;

  /**
   * Constructor for Ai Player
   *
   * @param playerName name of player (for later impl)
   * @param shipsRemaining how many ships are remaining
   * @param rand random variable, used to set seed in testing
   */
  public AiPlayer(String playerName, int shipsRemaining, Random rand) {
    this.playerName = playerName;
    this.shipsRemaining = shipsRemaining;
    this.rand = rand;
  }


  /**
   * Get the player's name.
   *
   * @return the player's name
   */
  @Override
  public String name() {
    return playerName;
  }

  /**
   * @param height         the height of the board, range: [6, 15] inclusive
   * @param width          the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return the placements of each ship on the board
   */
  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    List<Ship> result = new ArrayList<>();
    ArrayList<ArrayList<CellStatus>> tempBoard = createBoard(height, width);

    for (int i = 0; i < specifications.get(ShipType.CARRIER); i += 1) {
      Ship newShip = placeShip(6, tempBoard);
      result.add(newShip);
      List<Coord> newLocation = newShip.location;
      for (Coord coord : newLocation) {
        tempBoard.get(coord.getY()).set(coord.getX(), CellStatus.SHIP);
      }
    }
    for (int i = 0; i < specifications.get(ShipType.BATTLESHIP); i += 1) {
      Ship newShip = placeShip(5, tempBoard);
      result.add(newShip);
      List<Coord> newLocation = newShip.location;
      for (Coord coord : newLocation) {
        tempBoard.get(coord.getY()).set(coord.getX(), CellStatus.SHIP);
      }
    }
    for (int i = 0; i < specifications.get(ShipType.DESTROYER); i += 1) {
      Ship newShip = placeShip(4, tempBoard);
      result.add(newShip);
      List<Coord> newLocation = newShip.location;
      for (Coord coord : newLocation) {
        tempBoard.get(coord.getY()).set(coord.getX(), CellStatus.SHIP);
      }
    }
    for (int i = 0; i < specifications.get(ShipType.SUBMARINE); i += 1) {
      Ship newShip = placeShip(3, tempBoard);
      result.add(newShip);
      List<Coord> newLocation = newShip.location;
      for (Coord coord : newLocation) {
        tempBoard.get(coord.getY()).set(coord.getX(), CellStatus.SHIP);
      }
    }
    gameBoard = new Board(tempBoard);
    playerShips = result;
    return result;
  }

  /**
   * used to create a blank board
   *
   * @param height given height for board
   * @param width given width for board
   * @return 2d arraylist of cellStatus
   */
  private ArrayList<ArrayList<CellStatus>> createBoard(int height, int width) {
    ArrayList<ArrayList<CellStatus>> tempBoard = new ArrayList<>();
    for (int rows = 0; rows < height; rows += 1) {
      ArrayList<CellStatus> tempRow = new ArrayList<>();
      for (int cols = 0; cols < width; cols += 1) {
        tempRow.add(CellStatus.EMPT);
      }
      tempBoard.add(tempRow);
    }
    return tempBoard;
  }

  /**
   * Sets a given size to its equivilent ShipType
   *
   * @param size of ship
   * @return a ShipType
   */
  private ShipType sizeToShipType(int size) {
    return switch (size) {
      case 6 -> ShipType.CARRIER;
      case 5 -> ShipType.BATTLESHIP;
      case 4 -> ShipType.DESTROYER;
      default -> ShipType.SUBMARINE;
    };
  }


  /**
   * places a ship on the board
   *
   * @param size size of the ship
   * @param board current version of the board
   * @return a Ship
   */
  private Ship placeShip(int size, ArrayList<ArrayList<CellStatus>> board) {
    List<Coord> tempLocation = new ArrayList<>();
    List<CellStatus> tempStatus = new ArrayList<>();
    int orientation = rand.nextInt(2);
    if (orientation == 0) {
      int vertStart = rand.nextInt(board.size() - size + 1);
      int horizStart = rand.nextInt(board.get(0).size());
      for (int i = 0; i < size; i += 1) {
        tempLocation.add(new Coord(horizStart, vertStart + i,
            CellStatus.SHIP));
        tempStatus.add(board.get(vertStart + i).get(horizStart));
      }
      if (tempStatus.contains(CellStatus.SHIP)) {
        return placeShip(size, board);
      }
    } else {
      int vertStart = rand.nextInt(board.size());
      int horizStart = rand.nextInt(board.get(0).size() - size + 1);
      for (int i = 0; i < size; i += 1) {
        tempLocation.add(new Coord(horizStart + i, vertStart,
            CellStatus.SHIP));
        tempStatus.add(board.get(vertStart).get(horizStart + i));
      }
      if (tempStatus.contains(CellStatus.SHIP)) {
        return placeShip(size, board);
      }
    }
    return new Ship(sizeToShipType(size), tempLocation);
  }



  /**
   * @return
   */
  @Override
  public List<Coord> takeShots() {
    return null;
  }

  /**
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return
   */
  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    return null;
  }

  /**
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {

  }

  /**
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) {

  }
}