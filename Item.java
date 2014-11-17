import java.awt.Color;

public class Item {
  private int x;
  private int y;
  private boolean flag; // プレイヤーのラインとアイテムが衝突しないためのフラグ
  private int xMax; // 出現できるx軸の最大値
  private int yMax; // 出現できるy軸の最大値
  public int nItem; // 現在出現しているアイテム数
  private int cItem; // アイテムの出現間隔の引き伸ばし 
  //private Random rand = new Random();
  private double randTmp; // 乱数の一時変数
  private int iDifficulty;  //難易度の格納 
  public double difficultyBonus; // 難易度によるボーナス
  public double difficultyOnus;  // 難易度によるアイテムの出現率の調整
  public Point itemPos = new Point();  // 出現するアイテムの場所

  Item (int difficulty, Board board) {
    iDifficulty = difficulty;
    difficultyBonus = 1 + ( (double)difficulty / 10 ) * 2; // 1.2~1.6に
    difficultyOnus = 1 - ( (double)difficulty / 10 ) * 2;  // 0.8~0.4に
    xMax = board.xSize-2;
    yMax = board.ySize-2;
    initOfItem();
  }

  public void getOfItemPos (Color[][] state) {
    randTmp = Math.random();
    itemPos.x = 0;
    itemPos.y = 0;
    flag = false;
    cItem++;

    if ( randTmp <= difficultyOnus && nItem <= 30 && cItem % iDifficulty == 0 ) {
      while ( flag == false ) {
        itemPos.x = (int)( Math.random() * xMax + 1 );
        itemPos.y = (int)(Math.random() * yMax + 1 );
        if ( state[itemPos.y][itemPos.x] == Color.WHITE ) { 
          nItem = nItem + 1;
          flag = true;
        }
      }
    }
  }

  public Color getOfItemType () { // 出現するアイテムの種類の取得
    randTmp = Math.random();

    if ( randTmp >= 0.4 ) {  return Color.GREEN; }
    if ( randTmp >= 0.2 && iDifficulty == 3 ) {  return Color.GRAY; }
    return Color.ORANGE;
  }

  public void initOfItem() {
    nItem = 0;
    cItem = 0;
  }
}


