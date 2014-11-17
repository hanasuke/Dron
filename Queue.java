public class Queue {
  final int SIZE = 50;
  private Point[] values = new Point[SIZE+1];
  private Point t = new Point();
  private int head;
  private int tail;
  
  Queue() {
    for ( int i = 0; i < SIZE+1; i++ ) {
      values[i] = new Point();
      values[i].x = 0;       
      values[i].y = 0;
    } 
    head = tail = 0;
  }
  
  void init() {
    head = 0;
    tail = 0;
  }
  
  Point watchhead() {
    return values[head];   
  }
  
  void enqueue(Point pos) {
    values[tail++] = pos;
    tail = tail % (SIZE+1);
  }
  
  Point dequeue() {
    t = values[head++];      
    head = head % (SIZE+1);
    
    return t;
  }
  
  boolean isEmpty() {
    return (tail == head);
  }
} 
