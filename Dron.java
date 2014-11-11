import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JApplet;

public class Dron extends JApplet implements Runnable, KeyListener {
  private Color state[][];
  private int xSize, ySize;
  private int block;
  private int xL, yL, xR, yR;
  private int dxL, dyL, dxR, dyR;
  private boolean liveL, liveR;
  private int countL, countR;
  private Thread thread;
  private String message;
  private Font font;

  //-- 時間計測
  CountTime time = new CountTime();
  private int sec = 30;
  //time.setPriority(10);

  private Image img;     // オフスクリーンイメージ
  private Graphics offg; // オフスクリーン用のグラフィックス
  private int width, height;

  public Board board;
  public Player player1;
  public Player player2;

  @Override
  public void init() {
    board = new Board(1);
    xSize = board.xSize;
    ySize = board.ySize;
    player1 = new Player(true, board);
    player2 = new Player(false, board);
    block = 4;
    state = new Color[ySize][xSize];
    message = "Game started!";
    font = new Font("Monospaced", Font.PLAIN, 12);
    setFocusable(true);
    addKeyListener(this);
    Dimension size = getSize();
    width = size.width; height = size.height;
    img  = createImage(width, height);
    offg = img.getGraphics();
  }

  @Override
  public void start() {
    if (thread==null) {
      thread = new Thread(this);
      thread.start();
    }
  }

  @Override
  public void stop() {
    if (thread != null) {
      thread = null;
    }
  }

  @Override
  public void paint(Graphics g) {
    // 全体を背景色で塗りつぶす。
    offg.clearRect(0, 0, width, height);

    // 一旦、別の画像（オフスクリーン）に書き込む
    int i, j;
    for (i=0; i<ySize; i++) {
      for (j=0; j<xSize; j++) {
        offg.setColor(state[i][j]);
        offg.fillRect(j*block, i*block, block, block);
      }
    }
    offg.setFont(font);
    offg.setColor(Color.GREEN.darker());
    offg.drawString(message, 2*block, block*(ySize+3));
    offg.setColor(Color.RED.darker());
    offg.drawString("Left:  A(L), S(D), D(U), F(R)", 2*block, block*(ySize+6));
    offg.setColor(Color.BLUE.darker());
    offg.drawString("Right: H(L), J(D), K(U), L(R)", 2*block, block*(ySize+9));
    offg.drawString("Left: "+String.valueOf(player1.getNumOfWin()), 2*block, block*(ySize+12));
    offg.drawString("Right: "+String.valueOf(player2.getNumOfWin()), 2*block, block*(ySize+15));
    offg.drawString(sec+"秒" , 2*block, block*(ySize+18));

    g.drawImage(img, 0, 0, this);  // 一気に画面にコピー
  }

  public void run() {
    Thread thisThread = Thread.currentThread();
    while (thisThread==thread) {
      runInitialize();
      requestFocus();
      time.start();
      while (liveL&&liveR) {
        xL += dxL; yL += dyL;
        if (state[yL][xL]!=Color.WHITE) {
          liveL = false;
        } else {
          state[yL][xL] = Color.RED;
        }
        xR += dxR; yR += dyR;
        if (state[yR][xR]!=Color.WHITE) {
          liveR = false;
          if(xR==xL && yR==yL) {
            liveL = false;
            state[yL][xL] = Color.MAGENTA.darker();
          }
        } else {

          state[yR][xR] = Color.BLUE;
        }
        if (!liveL) {
          if (!liveR) {
            message = "Draw!";
            System.out.println(player1.getNumOfWin());
            System.out.println(player2.getNumOfWin());
          } else {
            countR++;
            message = "R won!";
            player2.increaseNumOfWin();
            System.out.println(player1.getNumOfWin());
            System.out.println(player2.getNumOfWin());
          }
        } else if (!liveR) {
          countL++;
          message = "L won!";
          player1.increaseNumOfWin();
          System.out.println(player1.getNumOfWin());
          System.out.println(player2.getNumOfWin());
        }
        // if ( time.isAlive() ) { System.out.print("!!"); }    // timeスレッドが生きているか
        sec = time.gettime();    // 残り秒数の取得
        if ( sec < 0 ) { stop(); break; }
        repaint();
        try{
          Thread.sleep(250);
        } catch(InterruptedException e) {}
      }
      time.stoprun(-1);
      stop();
      try{
        Thread.sleep(1750);
      } catch(InterruptedException e) {}
    }
  }

  public void keyPressed(KeyEvent e) {
    int key = e.getKeyCode();
    switch (key) {
    case 'A':  dxL =-1; dyL = 0; break;
    case 'S':  dxL = 0; dyL = 1; break;
    case 'D':  dxL = 0; dyL =-1; break;
    case 'F':  dxL = 1; dyL = 0; break;
    case 'H':  dxR =-1; dyR = 0; break;
    case 'J':  dxR = 0; dyR = 1; break;
    case 'K':  dxR = 0; dyR =-1; break;
    case 'L':  dxR = 1; dyR = 0; break;
    }
  }

  public void keyReleased(KeyEvent e) {}
  public void keyTyped(KeyEvent e) {}

  private void runInitialize() {
    int i,j;
    for(j=0; j<xSize; j++) {
      state[0][j] = state[ySize-1][j] = Color.BLACK;
    }
    for (i=1;i<ySize-1;i++) {
      state[i][0] = state[i][xSize-1] = Color.BLACK;
      for (j=1;j<xSize-1;j++) {
        state[i][j] = Color.WHITE;
      }
    }
    xL = yL = 2;
    xR = xSize-3; yR = ySize-3;
    dxL = dxR = 0;
    dyL = 1; dyR = -1;
    liveL = liveR = true;
  }
}
