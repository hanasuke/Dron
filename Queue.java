public class Queue {
  final int SIZE = 40;
  private Point[] values = new Point[41];
  private int head = 0;
  private int tail = 0;
  
  boolean enqueue(Point pos) {
    values[tail].x = pos.x;
    values[tail++].y = pos.y;
    tail = tail % values.length;
    return true;
  }
  Point dequeue() {
    Point t = new Point();
    if (tail != head) {
      t.x = values[head].x;
      t.y = values[head++].y;
      
      head = head % values.length;
    }
    return t;
  }
  
  boolean isEmpty() {
    return (tail == head);
  }
} 
