public class Queue {
  final static int queueSize = 50;
  private Point[] values = new Point[queueSize+1];
  private Point t = new Point();
  private Point returnPoint;
  private int head;
  private int tail;

  Queue() {
    for ( int i = 0; i < queueSize+1; i++ ) {
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
    return (i+1) % (queueSize + 1);
  }

  Point watchHead() {
    return values[head];
  }

  Point enqueue(Point pos) {
    if ( isEmpty() ) {
      values[tail] = pos;
      tail = next(tail);
      return null;
    } else if ( isFull() ) {
      returnPoint = dequeue();
      values[tail] = pos;
      tail = next(tail);
      System.out.println(returnPoint);
      return returnPoint;
    } else {
      values[tail] = pos;
      tail = next(tail);
      return null;
    }
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

}
