public class CountTime extends Thread {
  private int sec = 30;    // 秒数
  int k;

  public void run() {
    for ( k = sec; k >= 0; k-- ) {
      try{
        Thread.sleep(1000);
     } catch(InterruptedException e) {}
    }
  }

  public int getTime() {
    return k;
  }

  public void stopRun(int t) {
    k = t;
  }
}
