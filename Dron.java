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

    g.drawImage(img, 0, 0, this);  // 一気に画面にコピー
  }

  public void run() {
    Thread thisThread = Thread.currentThread();
    Point currentPoint1 = new Point();
    Point currentPoint2 = new Point();
    while (thisThread==thread) {
      runInitialize();
      requestFocus();
      while (liveL&&liveR) {
        player1.move();
        currentPoint1 = player1.getCurrentPosition();
        if (state[currentPoint1.y][currentPoint1.x]!=Color.WHITE) {
          liveL = false;
        } else {
          state[currentPoint1.y][currentPoint1.x] = Color.RED;
        }
        player2.move();
        currentPoint2 = player2.getCurrentPosition();
        if (state[currentPoint2.y][currentPoint2.x]!=Color.WHITE) {
          liveR = false;
          if( player1.getCurrentPosition() == player2.getCurrentPosition() ) {
            liveL = false;
            state[currentPoint1.y][currentPoint1.x] = Color.MAGENTA.darker();
          }
        } else {
          state[currentPoint2.y][currentPoint2.x] = Color.BLUE;
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
        repaint();
        try{
          Thread.sleep(250);
        } catch(InterruptedException e) {}
      }
      try{
        Thread.sleep(1750);
      } catch(InterruptedException e) {}
    }
  }

  public void keyPressed(KeyEvent e) {
    int key = e.getKeyCode();
    switch (key) {
    // 1P側の操作
    case 'A':  player1.decideMoveDirection(Define.LEFT);  break;
    case 'S':  player1.decideMoveDirection(Define.DOWN);  break;
    case 'D':  player1.decideMoveDirection(Define.UP);    break;
    case 'F':  player1.decideMoveDirection(Define.RIGHT); break;
    // 2P側の操作
    case 'H':  player2.decideMoveDirection(Define.LEFT);  break;
    case 'J':  player2.decideMoveDirection(Define.DOWN);  break;
    case 'K':  player2.decideMoveDirection(Define.UP);    break;
    case 'L':  player2.decideMoveDirection(Define.RIGHT); break;
    }
    System.out.println(key);
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
    liveL = liveR = true;
  }

}
