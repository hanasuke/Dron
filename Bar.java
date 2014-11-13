public class Bar {
  
  public int lengthBar;
  public Queue queue = new Queue();
    
  Bar (int difficulty, Board board) {
    lengthBar = board.ySize / 2;
  }

  Point barRepaint (Point pos) {
    Point t = new Point();
    
    
    // dequeueしてtに格納
    t = queue.dequeue();
    // enqueue    
    queue.enqueue(pos);
    
    return t;
  }
}