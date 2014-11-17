public class Player {
  private int x;
  private int y;
  private boolean live;
  public int score; // 勝利判定で用いるため
  private int scoreBonus; //ボーナス点的なもの
  private int nWin; // 勝利数
  private Point currentPos = new Point();
  private Point directMove = new Point();

  Player (int playerSide, Board board) {
    setStartPosition(playerSide, board);
    setStartDirection(playerSide);
    born();
    nWin = 0;
    initOfScore();
    initOfScoreBonus();
  }

  public void setStartPosition(int playerSide, Board board) {
    if ( playerSide == Define.PLAYER1 ) {
      currentPos.x = 2;
      currentPos.y = 2;
    } else {
      currentPos.x = board.xSize-3;
      currentPos.y = board.ySize-3;
    }
  }

  public void setStartDirection(int playerSide) {
    if ( playerSide == Define.PLAYER1 ) {
      directMove.x = 0;
      directMove.y = 1;
    } else {
      directMove.x = 0;
      directMove.y = -1;
    }
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

  public void born() {
    live = true;
  }

  public void die() {
    live = false;
  }

  public boolean getLiveStatus() {
    return live;
  }

  public void increaseOfScore() {
    score = score + scoreBonus;
    scoreBonus = scoreBonus + 2;
  }

  public void initOfScore() { 
    score = 0;
  }

  public void initOfScoreBonus() { 
    scoreBonus = 1;
  }

  public int getScore() { 
    return score;
  }

  public void addOfScore(double dBonus) {
    score = score + (int)( 10000 * dBonus );
  }

  public void subOfScore(double dBonus) {
    score = score - (int)( 15000 * dBonus );
  }

  public void addOfScoreBonus(double dBonus) {
    scoreBonus = scoreBonus + (int)( 700 * dBonus );
  }


}


