public class Player {
  private int x;
  private int y;
  private boolean live;
  private int nWin; // 勝利数

  Player (boolean playerSide, int xSize, int ySize) {
    if ( playerSide ) {
      x = 2;
      y = 2;
    } else {
      x = xSize-3;
      y = ySize-3;
    }
    live = true;
    nWin = 0;
  }

  public void increaseNumOfWin() {
    nWin++;
  }

  public int getNumOfWin() {
    return nWin;
  }

}
