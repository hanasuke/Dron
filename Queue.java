public class Queue {
  final int SIZE = 80;
  private Point[] values = new Point[SIZE+2];
  private int head = 0;
  private int tail = 0;
  
  boolean enqueue(int x, int y) {
    if (((tail + 1) % values.length) == head) {
      return false;
    }
    values[tail].x = x;
    values[tail++].y = y;
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
