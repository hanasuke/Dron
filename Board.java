import java.util.Arrays;

public class Board {
  private int xSize, ySize;
  private int[][] state;

  Board (int difficulty) {
    ySize = 80;
    if ( difficulty == 1 ) {
      xSize = 80;
    } else if ( difficulty == 2 ) {
      xSize = 120;
    } else {
      xSize = 160;
    }
    state = new int[ySize][xSize];
    Arrays.fill(state, 0);
    System.out.println(Arrays.toString(state));
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
