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
  private int barSize;
  private int countMove;
  private Thread thread;
  private String message;
  private Font font;

  //-- 時間計測
  private int sec = 30;

  private Image img;     // オフスクリーンイメージ
  private Graphics offg; // オフスクリーン用のグラフィックス
  private int width, height;

  public Board board;
  public Player player1;
  public Player player2;
  
  public Bar barP1;
  public Bar barP2;
  public Point bMoveP1;
  public Point bMoveP2;

  @Override
  public void init() {
    board = new Board(1);
    xSize = board.xSize;
    ySize = board.ySize;
    barSize = 40;
    countMove = 0;
    System.out.println(xSize);
    System.out.println(ySize);
    player1 = new Player(Define.PLAYER1, board);
    player2 = new Player(Define.PLAYER2, board);
    bMoveP1 = new Point();
    bMoveP2 = new Point();
    barP1 = new Bar();
    barP2 = new Bar();
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
    offg.drawString("Left Score: "+String.valueOf(player1.getScore()), 2*block, block*(ySize+15));
    offg.drawString(barP1.queue.watchhead().x+"" , 10*block, block*(ySize+18));
    offg.drawString(barP1.queue.watchhead().y+"" , 20*block, block*(ySize+18));
//    offg.drawString("Right: "+String.valueOf(player2.getNumOfWin()), 2*block, block*(ySize+18));
//    offg.drawString("Right Score: "+String.valueOf(player2.getScore()), 2*block, block*(ySize+21));
//    offg.drawString(sec+"秒" , 2*block, block*(ySize+24));
  //  offg.drawString("Right: "+String.valueOf(player2.getNumOfWin()), 2*block, block*(ySize+27));
   // offg.drawString(sec+"秒" , 2*block, block*(ySize+30));
    
    g.drawImage(img, 0, 0, this);  // 一気に画面にコピー
  }

  public void run() {
    Thread thisThread = Thread.currentThread();
    Point currentPoint1 = new Point();
    Point currentPoint2 = new Point();
    while (thisThread==thread) {
      runInitialize();
      requestFocus();
      CountTime time = new CountTime();
      time.start();
      while ( player1.getLiveStatus() && player2.getLiveStatus() ) {
        player1.move();
        player1.increaseOfScore();
        
        currentPoint1 = player1.getCurrentPosition();
        if (state[currentPoint1.y][currentPoint1.x]==Color.BLUE) {
          player1.initOfScoreBonus();
        }

        if (state[currentPoint1.y][currentPoint1.x]==Color.BLACK || state[currentPoint1.y][currentPoint1.x]==Color.RED ) {
          player1.die();
        } else {
          state[currentPoint1.y][currentPoint1.x] = Color.RED;
          if ( countMove > barSize ) {
            bMoveP1 = barP1.queue.dequeue();
            state[bMoveP1.y][bMoveP1.x] = Color.GREEN;
          }  
          barP1.queue.enqueue(currentPoint1);
        }
        player2.move();
        player2.increaseOfScore();
        currentPoint2 = player2.getCurrentPosition();
        if (state[currentPoint2.y][currentPoint2.x]==Color.RED) {
          player2.initOfScoreBonus();
        }
        if (state[currentPoint2.y][currentPoint2.x]==Color.BLACK || state[currentPoint2.y][currentPoint2.x]==Color.BLUE ) {
          player2.die();
          if( player1.getCurrentPosition() == player2.getCurrentPosition() ) {
            player1.die();
            state[currentPoint1.y][currentPoint1.x] = Color.MAGENTA.darker();
          }
        } else {
          state[currentPoint2.y][currentPoint2.x] = Color.BLUE;
          
          if ( countMove > barSize ) {
            bMoveP2 = barP2.queue.dequeue();
            state[bMoveP2.y][bMoveP2.x] = Color.GREEN;
          }   
          // enqueueのみ          
          barP2.queue.enqueue(currentPoint2);
        } 
        countMove++;

        
        if ( ! player1.getLiveStatus() ) {
          if ( ! player2.getLiveStatus() ) {
            message = "Draw!";
          } else {
            message = "R won!";
            player2.increaseNumOfWin();
          }
        } else if ( ! player2.getLiveStatus() ) {
          message = "L won!";
          player1.increaseNumOfWin();
        }
        sec = time.getTime();    // 残り秒数の取得
        if ( sec < 0 ) { break; }
        repaint();
        try{
          Thread.sleep(250);
        } catch(InterruptedException e) {}
      }
     time.stopRun(-1);
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
    player1.setStartPosition(Define.PLAYER1, board);
    player1.setStartDirection(Define.PLAYER1);
    player1.born();
    player1.initOfScore();
    player1.initOfScoreBonus();
    player2.setStartPosition(Define.PLAYER2, board);
    player2.setStartDirection(Define.PLAYER2);
    player2.born();
    player2.initOfScore();
    player2.initOfScoreBonus();
    
    barP1.queue.init();
    barP2.queue.init();
    countMove = 0;
  }
}
