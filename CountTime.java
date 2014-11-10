public class CountTime extends Thread {
  private int sec = 30;    // お試しで30s
  int k;

  public void run() {
    for ( k = sec; k >= 0; k-- ) {
      try{
        Thread.sleep(1000);
      } catch(InterruptedException e) {}
    }
  }

  public int gettime() {
    return k;
  }


}