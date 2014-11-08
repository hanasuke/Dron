public class Player {
  private int x;
  private int y;
  private boolean live;
  private int nWin; // 勝利数

  Player (boolean playerSide, Board board) {
    if ( playerSide ) {
      x = 2;
      y = 2;
    } else {
      x = board.xSize-3;
      y = board.ySize-3;
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
