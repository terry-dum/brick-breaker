package brickBreaker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class MapGenerator {
	
	public int map[][];
	public int brickWidth;
	public int brickHeight;
	
	public MapGenerator(int row, int col) {
		map = new int[row][col];
		for(int i=0;i<map.length;i++) {
			for(int j=0; j<map[0].length;j++) {
				map[i][j]=1;
			}
		}
		brickWidth=940/col;
		brickHeight=100/row;
	}
	
	public void draw(Graphics2D g) {
		for(int i=0;i<map.length;i++) {
			for(int j=0; j<map[0].length;j++) {
				if(map[i][j]>0) {
					g.setColor(Color.yellow);
					g.fillRect(i*brickWidth+80, j*brickHeight+50, brickWidth, brickHeight);
					
					//fill rect shape
//					g.setStroke(new BasicStroke());
					g.setColor(Color.blue);
					g.drawRect(i*brickWidth+80, j*brickHeight+50, brickWidth, brickHeight);
				}
			}
		}
	}
	
	public void setBrickValue(int value, int row, int col) {
		map[row][col]=value;
	}
}
