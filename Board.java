package SnakeGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Board extends JPanel implements ActionListener {
    private final int b_width = 300;
    private final int b_height = 300;
    private final int dot_size = 10;
    private final int all_dots = 900;
    private final int rand_pos = 29;
    private final int delay = 140;

    private final int[] x = new int[all_dots];
    private final int[] y = new int[all_dots];

    private int dots;
    private int apple_x;
    private int apple_y;

    private boolean left_direction = false;
    private boolean right_direction = true;
    private boolean up_direction = false;
    private boolean down_direction = false;
    private boolean inGame = true;

    private Timer timer;
    private Image ball;
    private Image apple;
    private Image head;

    public  Board(){
        initBoard();
    }

    private void initBoard(){
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);
        setPreferredSize(new Dimension(b_height,b_width));
        loadImages();
        initGame();
    }

    private void loadImages() {
        ImageIcon iid = new ImageIcon(getClass().getResource("/SnakeGame/dot.png"));
        ball = iid.getImage();
        ImageIcon iia = new ImageIcon(getClass().getResource("/SnakeGame/apple.png"));
        apple = iia.getImage();
        ImageIcon iih = new ImageIcon(getClass().getResource("/SnakeGame/head.png"));
        head = iih.getImage();
    }
    private void initGame(){
        dots = 3;
        for(int z = 0;z<dots;z++){
            x[z] = 50-z*10;
            y[z] = 50;
        }

        locateApple();
        timer = new Timer(delay,this);
        timer.start();
    }

    public void paintComponent(Graphics g){
             super.paintComponent(g);
             doDrawing(g);
    }
             private void doDrawing(Graphics g){
                if(inGame){
                    g.drawImage(apple, apple_x, apple_y,this);

                    for(int z = 0;z<dots;z++){

                        if(z==0){
                            g.drawImage(head, x[z], y[z], this);
                        }
                        else{
                            g.drawImage(ball, x[z], y[z], this);
                        }
                    }
                    Toolkit.getDefaultToolkit().sync();
                }else{
                    gameOver(g);
                }
             }
             private void gameOver(Graphics g){
                  String msg = "Game over";
                  Font small = new Font("Helvetica", Font.BOLD, 14);
                  FontMetrics  metr =  getFontMetrics(small);

                  g.setColor(Color.white);
                  g.setFont(small);
                  g.drawString(msg, (b_width - metr.stringWidth(msg)) / 2, b_height / 2);
             }

             private void checkApple(){
               if((x[0] == apple_x) && (y[0] == apple_y)){
                dots++;
                locateApple();
               }
             }
             private void move(){
                for(int z = dots;z>0;z--){
                    x[z] = x[(z-1)];
                    y[z] = y[(z-1)];
                }

                if(left_direction){
                    x[0] -= dot_size;
                }
                if(right_direction){
                    x[0] += dot_size;
                }
                if(up_direction){
                    y[0] -= dot_size;
                }
                if(down_direction){
                    y[0] += dot_size;
                }
             }
             private void checkCollision(){
                for(int z = dots; z>0 ;z--){
                    if((z>4) && (x[0]== x[z]) && (y[0] == y[z])){
                        inGame = false;
                    }
                }

                if(y[0]>=b_height){
                    inGame = false;
                }
                if(y[0]<0){
                    inGame = false;
                }
                if(x[0]>=b_height){
                    inGame = false;
                }
                if(x[0]<0){
                    inGame = false;
                }
                if(!inGame){
                    timer.stop();
                }
             }
             private void locateApple(){
                int r = (int)(Math.random()*rand_pos);
                apple_x = ((r*dot_size));

                r = (int)(Math.random()*rand_pos);
                apple_y = ((r*dot_size));
             }
             public void actionPerformed(ActionEvent e){
               if(inGame){
                checkApple();
                checkCollision();
                move();
               }
               repaint();
             }
             private class TAdapter extends KeyAdapter{
                public void keyPressed(KeyEvent e){
                    int  key = e.getKeyCode();
                    if(key==KeyEvent.VK_LEFT && (!right_direction)){
                        left_direction = true;
                        up_direction = false;
                        down_direction = false;
                    }
                    if(key==KeyEvent.VK_RIGHT && (!left_direction)){
                        right_direction = true;
                        up_direction = false;
                        down_direction = false;
                    }
                    if(key==KeyEvent.VK_UP && (!down_direction)){
                        left_direction = false;
                        up_direction = true;
                        right_direction = false;
                    }
                    if(key==KeyEvent.VK_DOWN && (!up_direction)){
                        left_direction = false;
                        right_direction = false;
                        down_direction = true;
                    }
                }
             }
  }

