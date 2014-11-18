import java.util.Arrays;

public class Board {
  public int xSize, ySize;
  private int[][] state;

  Board (int difficulty) {
    ySize = 80;
    if ( difficulty == 3 ) {
      xSize = 120;
    } else if ( difficulty == 2 ) {
      xSize = 120;
    } else {
      xSize = 80;
    }
    state = new int[ySize][xSize];
  }

  public int getState(Point point) {
    if ( point.x < 0 || point.x > xSize ) {
      return -1;
    } else if ( point.y < 0 || point.y > ySize ) {
      return -1;
    } else {
      return state[point.y][point.x];
    }
  }

}
