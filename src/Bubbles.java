import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class Bubbles 
{
	private Ellipse2D.Double bubble;
	private double xPos;
	private double yPos;
	private double BUBBLE_SIZE = 20;
	
	public Bubbles(double xPos2, double yPos2)
	{
		bubble = new Ellipse2D.Double();
		xPos = xPos2;
		yPos = yPos2;
		
		setAttributes();
	}
	
	private void setAttributes()
	{
		bubble.setFrame(-BUBBLE_SIZE/2, -BUBBLE_SIZE/2, BUBBLE_SIZE, BUBBLE_SIZE);
	}
	
	public void drawBubble(Graphics2D g2)
	{
		float dash[] = { 2.0f };
		AffineTransform transform = g2.getTransform();
		g2.translate(xPos,yPos);
		g2.setColor(Color.cyan);
		g2.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_BUTT,
		BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));
		g2.draw(bubble);
		
		g2.setTransform(transform);
	}
	
	public void updateBubble()
	{
		floatUp();
	}
	
	private void floatUp()
	{
		xPos++;
		yPos--;
		yPos--;
	}
	
	public double getYPos()
	{
		return yPos;
	}
}
