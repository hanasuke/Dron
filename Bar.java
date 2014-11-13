public class Bar {
  
  public int lengthBar;
  public Queue queue;
    
  Bar (int difficulty, Board board) {
    lengthBar = board.ySize / 2;
  }

  Point barRepaint (int x, int y) {
    Point t = new Point();
    
    // dequeueしてtに格納
    t = queue.dequeue();
    // enqueue    
    queue.enqueue(y, x);
    
    return t;
  }
}