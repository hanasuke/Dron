public class Player {
  private int x;
  private int y;
  private boolean live;
  private void initialize(boolean player, int xSize, int ySize) {
    switch (player) {
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
  }

}
