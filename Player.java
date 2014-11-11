public class Player {
  private int x;
  private int y;
  private boolean live;
  private int nWin; // 勝利数
  private Point currentPos = new Point();
  private Point directMove = new Point();

  Player (int playerSide, Board board) {
    if ( playerSide == Define.PLAYER1 ) {
      currentPos.x = 2;
      currentPos.y = 2;
      directMove.x = 0;
      directMove.y = 1;
    } else {
      currentPos.x = board.xSize-3;
      currentPos.y = board.ySize-3;
      directMove.x = 0;
      directMove.y = -1;
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

  public void decideMoveDirection(int direction) {
    if ( direction == Define.LEFT ) {
      directMove.x = -1;
      directMove.y = 0;
    } else if ( direction == Define.UP ) {
      directMove.x = 0;
      directMove.y = -1;
    } else if ( direction == Define.RIGHT ) {
      directMove.x = 1;
      directMove.y = 0;
    } else if ( direction == Define.DOWN ) {
      directMove.x = 0;
      directMove.y = 1;
    }
  }

  public Point getCurrentPosition() {
    return currentPos;
  }

  public void move(){
    currentPos.x += directMove.x;
    currentPos.y += directMove.y;
  }
}
