public class Queue {
  final static int queueSize = 50;
  private Point[] values = new Point[queueSize];
  private Point t = new Point();
  private Point returnPoint;
  private int head;
  private int tail;

  Queue() {
    for ( int i = 0; i < queueSize; i++ ) {
      values[i] = new Point();
      values[i].x = 0;
      values[i].y = 0;
    }
    init();
  }

  void init() {
    head = 0;
    tail = 0;
  }

  private int next(int i) {
    return (i+1) % (queueSize);
  }

  Point watchHead() {
    return values[head];
  }

  Point enqueue(Point pos) {
    debug();
    if ( isFull() ) {
      returnPoint = dequeue();
    } else {
      returnPoint = null;
    }
    values[tail] = pos;
    tail = next(tail);
    return returnPoint;
  }

  Point dequeue() {
    t = values[head];
    head = next(head);

    return t;
  }

  boolean isEmpty() {
    return (tail == head);
  }

  boolean isFull() {
    return (head == next(tail));
  }

  void debug() {
    System.out.println("\n");
    for ( int i = 0; i < queueSize; i++ ) {
      System.out.println(values[i].x);
      System.out.println(values[i].y);
    }
    System.out.println("\n");
  }
}
