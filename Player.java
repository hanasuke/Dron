public class Player {
  private int x;
  private int y;
  private boolean live;
  private int nWin; // 勝利数

  private void initialize(boolean playerSide, int xSize, int ySize) {
    switch (playerSide ) {
    case true:    // 1P側の話
      x = 2;
      y = 2;
      break;    // 2P側の話
    case false:
      x = xSize-3;
      y = ySize-3;
      break;
    }
    live = true;
    nWin = 0;
  }

  public void increaseNumOfWin(void) {
    nWin++;
  }

  public int getNumOfWin(void) {
    return nWin
  }

}
