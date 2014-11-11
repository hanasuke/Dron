import java.util.Arrays;

public class Bar {
  
  public int lengthBar;
  private static int k = 0;
  // キュー未作成
    
  Bar (int difficulty, Board board) {
    lengthBar = board.ySize / 2;
  }

  public void barRepaint (int n, Point point) {
   
    if ( k > n ) {
      // キューに格納してある、kの移動軌跡を削除

    }
    // キューに新たに座標を格納    
    k++; // kを更新  
  }
}