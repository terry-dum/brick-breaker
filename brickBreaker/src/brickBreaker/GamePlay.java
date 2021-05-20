package brickBreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePlay extends JPanel implements KeyListener, ActionListener{
	
	private boolean play=false;
	private int score=0;
	private int totalBricks=28;			//7x3
	private javax.swing.Timer timer;
	private int delay=10;
	private int playerX=310;			//initial slider position
	private int ballposX=120;
	private int ballposY=350;
	private int ballXdirection=-3;
	private int ballYdirection=-3;
	
	private MapGenerator map;
	public GamePlay() {
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);	//tab key not allowed
		timer = new Timer(delay,this);
		timer.start();
		map=new MapGenerator(4,	7);
	}
	
	public void paint(Graphics g) {
		//background
		g.setColor(Color.white);
		g.fillRect(0, 0, 700, 600);
		
		map.draw((Graphics2D)g);
		
		//score
		g.setColor(Color.black);
		g.setFont(new Font("serif",Font.BOLD,25));
		g.drawString("Score: "+score, 560, 30);
		
		//paddle
		g.setColor(Color.black);
		g.fillRect(playerX, 525, 100, 8);
		
		//ball
		g.setColor(Color.red);
		g.fillOval(ballposX, ballposY, 18, 18);
		
		//gameover
		if(ballposY>544) {
			play = false;
			ballXdirection=0;
			ballYdirection=0;
			
			g.setColor(Color.RED);
			g.setFont(new Font("serif",Font.BOLD,25));
			g.drawString("Game Over, Score: "+score, 200, 300);
			g.setFont(new Font("serif",Font.BOLD,25));
			g.drawString("Enter to RESTART ", 200, 330);
		}
		
		if(totalBricks<=0) {
			play = false;
			ballXdirection=0;
			ballYdirection=0;
			
			g.setColor(Color.RED);
			g.setFont(new Font("serif",Font.BOLD,25));
			g.drawString("You WON, Score: "+score, 200, 300);
			g.setFont(new Font("serif",Font.BOLD,25));
			g.drawString("Enter to RESTART ", 200, 330);
		}
		g.dispose();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		
		if(play) {
			
			A:
				for(int i=0;i<map.map.length;i++) { //second map is variable in mapgenerator
					for(int j=0;j<map.map[0].length;j++) {
						if(map.map[i][j]>0) {
							int brickX= i*map.brickWidth+80;
							int brickY= j*map.brickHeight+50;
							int brickWidth=map.brickWidth;
							int brickHeight=map.brickHeight;
							
							Rectangle rect = new Rectangle(i*brickWidth+80, j*brickHeight+50, brickWidth, brickHeight);
							Rectangle ballRect = new Rectangle(ballposX,ballposY,18,18);
							
							if(ballRect.intersects(rect)) {
								map.setBrickValue(0, i, j);
								totalBricks--;
								score+=5;
								
								if(ballposX+17<=rect.x || ballposX+1>=rect.x + rect.width) {
									ballXdirection = -ballXdirection;
								}else {
									ballYdirection = -ballYdirection;
								}
								break A;
							}
						}
					}
				}
			ballposX+=ballXdirection;
			ballposY+=ballYdirection;
			
			if(ballposX<0) {
				ballXdirection = -ballXdirection;
			}
			if(ballposY<0) {
				ballYdirection = -ballYdirection;
			}
			if(ballposX>680) {
				ballXdirection = -ballXdirection;
			}
			if(new Rectangle(ballposX,ballposY,18,18).intersects(new Rectangle(playerX,525,100,8))) {
				ballYdirection = -ballYdirection;
			}
		}
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
			if(playerX>=600) {
				playerX=600;
			}else {
				moveRight();
			}
		}
		
		if(e.getKeyCode()==KeyEvent.VK_LEFT) {
			if(playerX<=0) {
				playerX=0;
			}else {
				moveLeft();			
			}
		}
		
		if(e.getKeyCode()==KeyEvent.VK_ENTER) {
			if(!play) {
				play=true;
				score=0;
				totalBricks=28;			
				playerX=310;			
				ballposX=120;
				ballposY=350;
				ballXdirection=-3;
				ballYdirection=-3;
				
				map= new MapGenerator(4, 7);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private void moveRight() {
		play=true;
		playerX+=20;
	}
	
	private void moveLeft() {
		play=true;
		playerX-=20;
	}

}
