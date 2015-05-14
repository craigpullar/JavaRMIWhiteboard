import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JComponent;


public class GraphicalObject extends JComponent{
	public enum ShapeType {
		TRIANGLE, RECTANGLE, OVAL;
	}
	private ShapeType type;
	private int x;
	private int y;
	private int width;
	private int height;
	private Color color;
	int id;
	
	//Constructor
	public GraphicalObject(ShapeType type, int x, int y, int width, int height, Color color) {
		this.type = type;
		this.color = color;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.setSize(width, height);
		this.setPreferredSize(new Dimension(width,height));
		this.setVisible(true);
		Random rd = new Random();
		this.id = rd.nextInt();
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g); 
	    g.setColor(color);
	    switch (this.type) {
	    case RECTANGLE:
	    	g.fillRect(0, 0,this.width , this.height);
	    	break;
	    case OVAL:
	    	g.fillOval(0, 0, width, height);
	    	break;
	    case TRIANGLE:
	    	int[] xPoints = {width/2,0, width};
	    	int[] yPoints = {0, height, height};
	    	g.fillPolygon(xPoints, yPoints, 3);
	    	break;
	    }
	    
	}
	//Getters & Setters
	public ShapeType getType() {
		return this.type;
	}
	public void setType(ShapeType type) {
		this.type = type;
	}
	public int getX() {
		return this.x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return this.y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getWidth() {
		return this.width;
	}
	public int getHeight(){
		return this.height;
	}
	public Dimension getPreferredSize() {
		  return new Dimension(getWidth(), getHeight());
		}
	public int getID(){
		return this.id;
	}
}

