package cs3500.pa03.model;

/**
 * Class for new object Coord
 * Used as coordinates on the board
 */
public class Coord {

  private final int xcoord;
  private final int ycoord;
  private CellStatus status;

  /**
   * Constructor for Coord class
   *
   * @param x is the X coord of the cell
   * @param y is the Y coord of the cell
   * @param status is the status of the coord
   */
  public Coord(int x, int y, CellStatus status) {
    this.xcoord = x;
    this.ycoord = y;
    this.status = status;
  }

  /**
   * getter for the X
   *
   * @return x coord of cell
   */
  public int getX() {
    return this.xcoord;
  }

  /**
   * getter for the Y
   *
   * @return y coord of cell
   */
  public int getY() {
    return this.ycoord;
  }

  /**
   * getter for the status of a given cell
   *
   * @return the CellStatus
   */
  public CellStatus getStatus() {
    return this.status;
  }

  /**
   * Used to change the status of a ship
   *
   * @param c status to be changed
   */
  public void changeStatus(CellStatus c) {
    status = c;
  }

}
