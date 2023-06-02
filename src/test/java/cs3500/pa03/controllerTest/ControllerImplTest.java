package cs3500.pa03.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.controller.Controller;
import cs3500.pa03.controller.ControllerImpl;
import java.io.ByteArrayInputStream;
import org.junit.jupiter.api.Test;

public class ControllerImplTest {


  /**
   * Tests the getMaxShips helper method
   */
  @Test
  public void testGetMaxShips() {

    String userInput = "1 22 7 9";
    ByteArrayInputStream inputAsByte = new ByteArrayInputStream(userInput.getBytes());
    System.setIn(inputAsByte);
    Controller cont = new ControllerImpl();

    cont.start();

    assertEquals(7, cont.getMaxShips());

  }

  // figure out how to test this portion
  @Test
  public void testFleetSelection() {
    /*
    1 incorrect size
    2 correct size
    3 and 4: fleet selction fail
    5 correct fleet
    6 + 7 player salvo
     */
    String wrongSalvo = "1 1 1 1 1 3 1 4 1 5 1 6 1 7 0 0";
    String rightSalvo = "1 1 1 2 1 3 1 4 1 5 1 6 1 7 0 0";
    String userInput2 = String.format("1 22%s 8 9%s 2 2 2 0%s 9 23 1 3 %s 3 2 2 1"
            + "%s" + wrongSalvo + "%s" + rightSalvo,
        System.lineSeparator(),
        System.lineSeparator(),
        System.lineSeparator(),
        System.lineSeparator(),
        System.lineSeparator(),
        System.lineSeparator());
    ByteArrayInputStream inputAsByte2 = new ByteArrayInputStream(userInput2.getBytes());
    System.setIn(inputAsByte2);
    Controller cont = new ControllerImpl();
    cont.start();
    cont.fleetSelection(8);
    cont.boardCreation();
    cont.getPlayerSalvo();

  }




}
