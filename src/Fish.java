import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class Fish 
{	
	//attributes common to both herbivore and sharks
	protected Ellipse2D.Double body;
	protected Ellipse2D.Double eye;
	protected Ellipse2D.Double scales;
	protected Arc2D.Double arcFin;
	protected Line2D.Double tailStripe;
	protected Line2D.Double tailStripe2;
	protected Arc2D.Double gill;
	protected CubicCurve2D.Double headLine;
	protected Rectangle2D.Double test;
	protected Rectangle2D.Double test2;
	
	protected double xPos;
	protected double yPos;
	protected double scaleX;
	protected double scaleY;
	protected double scale = 1;
	protected double xSpeed = 1;
	protected double ySpeed = 0;
	protected double collisionSpeed = 0.5;
	protected double accX;
	protected double accY;
	protected double damp;
	protected double buffer;
	protected double randScale;
	protected int fishScore;
	
	protected double FISH_WIDTH = 160; //we are making the height and width proportional to the scale
	protected double FISH_HEIGHT = 80; //we are making the height and width proportional to the scale
	protected double fishWidth2;
	protected double fishHeight2;
	protected final int SCALE_SIZE = 10;
	protected final int EYE_SIZE = 10;
	protected int waveSin; //used to constantly change the values of the wave on which the fish move
	
	protected Color fishColor = Color.orange;
	protected Color outlineColor = Color.black;	
	protected Color tailColor = Color.yellow;
	protected Color dorsalFinColor = Color.cyan;
	protected Color eyeColor = Color.black;
	
	protected boolean collided = false;
	protected boolean facingRight = true;
	
	
	public Fish()
	{
		randScale = Util.random(0.2, 0.5);
		scaleX = -1*randScale;
		scaleY = 1*randScale;
		
		fishColor = new Color((int) Util.random(150, 256),(int) Util.random(150, 256),(int) Util.random(150, 256));
		outlineColor = new Color((int) Util.random(0, 100),(int) Util.random(0, 100),(int) Util.random(0, 100));
		tailColor = new Color((int) Util.random(0, 256),(int) Util.random(0, 256),(int) Util.random(0, 256));
		dorsalFinColor = new Color((int) Util.random(0, 256),(int) Util.random(0, 256),(int) Util.random(0, 256));
		
		//because FISH_WIDTH and FISH_HEIGHT were integrated into the drawing of the fish, changing their values directly would destroy the fish
		//thus i passed the values onto secondary variables that are used to handle boundary detection and scaling as they are dependent on each other but not the fish
		fishWidth2 = FISH_WIDTH; 
		fishWidth2 *= Math.abs(scaleX);
		fishHeight2 = FISH_HEIGHT;
		fishHeight2 *= Math.abs(scaleY);
		
		buffer = 100;
		xPos = Util.random(FishPanel.POND_X + buffer +fishWidth2/2, FishPanel.POND_W+FishPanel.POND_X-buffer-fishWidth2/2);
		yPos = Util.random(FishPanel.POND_Y  +fishHeight2/2+buffer/2, FishPanel.POND_H+FishPanel.POND_Y-buffer-fishHeight2/2);
		xSpeed = Util.random(8, 10)*randScale;
		ySpeed = 0;
		
		accX = 0;
		accY = 0;
		
		damp = 0.99f;
		
		body = new Ellipse2D.Double();
		scales = new Ellipse2D.Double();
		eye = new Ellipse2D.Double();
		arcFin = new Arc2D.Double();
		tailStripe = new Line2D.Double();
		tailStripe2 = new Line2D.Double();
		gill = new Arc2D.Double();
		headLine = new CubicCurve2D.Double();
		test = new Rectangle2D.Double();
		test2 = new Rectangle2D.Double();
		
		setAttributes();
	}
	
	public Fish(int fishX, int fishY)
	{
		xPos = fishX;
		yPos = fishY;
		scaleX = -1;
		scaleY = 1;
		
		body = new Ellipse2D.Double();
		scales = new Ellipse2D.Double();
		eye = new Ellipse2D.Double();
		arcFin = new Arc2D.Double();
		tailStripe = new Line2D.Double();
		tailStripe2 = new Line2D.Double();
		gill = new Arc2D.Double();
		headLine = new CubicCurve2D.Double();
	}
	
	public Fish(int fishX, int fishY, double scale)
	{
		xPos = fishX;
		yPos = fishY;
		scaleX = (-1*scale);
		scaleY = (1*scale);
		FISH_WIDTH *= scale;
		FISH_HEIGHT *= scale;
		
		body = new Ellipse2D.Double();
		scales = new Ellipse2D.Double();
		eye = new Ellipse2D.Double();
		arcFin = new Arc2D.Double();
		tailStripe = new Line2D.Double();
		tailStripe2 = new Line2D.Double();
		gill = new Arc2D.Double();
		headLine = new CubicCurve2D.Double();
	}
	
	protected void setAttributes()
	{
		//ellipses
		body.setFrame(-FISH_WIDTH/2, -FISH_HEIGHT/2, FISH_WIDTH, FISH_HEIGHT);
		eye.setFrame(-FISH_WIDTH/3, -FISH_HEIGHT/10, EYE_SIZE, EYE_SIZE);
		scales.setFrame(-10,-30,SCALE_SIZE,SCALE_SIZE);
		
		//lines
		tailStripe.setLine(70,0,110,-60);
		tailStripe2.setLine(70,0,110,60);
		
		//arcs
		arcFin.setArc(5,27,50,25,180,90,Arc2D.PIE);
	
		//gill arcs
		gill.setFrame(-FISH_WIDTH/4, -FISH_HEIGHT/2, 10, 90);
		gill.setAngleStart(330);
		gill.setAngleExtent(30);
		
		//CubicCurve
		headLine.setCurve(50,-32,-10,-60,-10,-50, -20, -38);
	
	}
	
	public void drawFish(Graphics2D g2)
	{
		AffineTransform transform = g2.getTransform();
		
		g2.translate(xPos, yPos);
		g2.scale(scaleX, scaleY);
		//setAttributes();
		//draw body
		g2.setColor(fishColor);
		g2.fill(body);
		g2.setColor(Color.black);
		g2.draw(body);
		
		//draw eyes
		g2.setColor(outlineColor);
		g2.fill(eye);
		
		//draw fins
		g2.setColor(outlineColor);
		// fin 1: front fin
		g2.drawPolygon(new int[] {-30,-10,-10} , new int[] { 35, 65, 30},3);
		g2.fillPolygon(new int[] {-30,-10,-10} , new int[] { 35, 65, 30},3);
		// fin 2: dorsal fin
		//g2.drawPolygon(new int[] {-20,0,35,50} , new int[] { -38, -65, -50, -32},4);
		g2.fillPolygon(new int[] {-20,0,35,50} , new int[] { -38, -65, -50, -32},4);
		// fin 3: arc fin
		g2.setColor(outlineColor);
		g2.draw(arcFin);
		g2.fill(arcFin);

		//draw tail
		g2.setColor(outlineColor);
		g2.drawPolygon(new int[] {70,70,110} , new int[] {20,-20,-60},3);
		g2.fillPolygon(new int[] {70,70,110} , new int[] {20,-20,-60},3);
		g2.drawPolygon(new int[] {70,70,110} , new int[] {20,-20,60},3);
		g2.fillPolygon(new int[] {70,70,110} , new int[] {20,-20,60},3);
		
		//draw tail stripes
		g2.setColor(tailColor);
		g2.draw(tailStripe);
		g2.draw(tailStripe2);
		
		//draw cubiccurve
		g2.setColor(dorsalFinColor);
		g2.draw(headLine);
		//g2.fill(headLine);\
	

		//draw gills
		for(int i = 0; i < 3; i++)
		{
			gill.setFrame(-FISH_WIDTH/4+5*i, -FISH_HEIGHT/2, 10, 90);
			g2.setColor(outlineColor);
			g2.draw(gill);
		}
		
		//draw scales
		for (int i = 0; i < 6; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				scales.setFrame(-10+i*10, -30+j*7, SCALE_SIZE, SCALE_SIZE);
				
				g2.setColor(outlineColor);
				//g2.drawOval(-10+i*10, -30+j*7, 10, 10);
				g2.draw(scales);
				g2.setColor(fishColor);
				//g2.fillOval(-10+i*10, -30+j*7, 10, 10);
				g2.fill(scales);
			}
		}
		for(int j = 0; j < 7; j++)
		{
			scales.setFrame(-20+70, -27+j*7, SCALE_SIZE, SCALE_SIZE);
			
			g2.setColor(outlineColor);
			g2.draw(scales);
			g2.setColor(fishColor);
			g2.fill(scales);
		}
		for(int j = 0; j < 5; j++)
		{
			scales.setFrame(-10+70, -20+j*7, SCALE_SIZE, SCALE_SIZE);
			g2.setColor(outlineColor);
			g2.draw(scales);
			g2.setColor(fishColor);
			g2.fill(scales);
		}	
		for (int i = 0; i < 4; i++)
		{
			scales.setFrame(-10+i*10, -30+7*8, SCALE_SIZE, SCALE_SIZE);
			g2.setColor(outlineColor);
			g2.draw(scales);
			g2.setColor(fishColor);
			g2.fill(scales);
		}
		g2.setTransform(transform);
	}
	
	public void setFishColor(Color c)
	{
		fishColor = c;
	}
	
	//setter for the scale field
	public void setScaleX(int s)
	{
		scaleX = s;
	}
	
	//getter for the scale field
	public double getScaleX()
	{
		return scaleX;
	}
	
	public void setScaleY(int s)
	{
		scaleY = s;
	}
	
	//getter for the scale field
	public double getScaleY()
	{
		return scaleY;
	}
	
	//getting xPos
	public double getXPos()
	{
		 return xPos;
	}
	
	//getting yPos
	public double getYPos()
	{
		 return yPos;
	}
	
	public double getWidth()
	{
		 return fishWidth2;
	}	
	
	public double getHeight()
	{
		 return fishHeight2;
	}
	//checks if a point is on the fish
	public boolean checkPointHit (int x, int y)
	{
		boolean hit = false; //by default it will return false
		
		if(x>xPos-fishWidth2/2 && x<xPos+fishWidth2/2 && y>yPos-fishHeight2/2 && y<yPos+fishHeight2/2)
		{
			hit = true;
		}	
		return hit;
	}
	
	public void update()
	{
		move();
		wave();
		//boundaryDetection();
	}
	private void move()
	{	
		
		xSpeed += accX;
		ySpeed += accY;	
		xPos += xSpeed;
		yPos += Math.sin(Math.toDegrees((double)waveSin/1000))+ySpeed;
		if(Math.abs(xSpeed)>0.5) xSpeed*=damp;
		ySpeed*=damp;
		accX=0;
		accY=0;
	}
	
	private void wave()
	{
		if(waveSin <10000)
		{
			waveSin++;	
		}
		else
		{
			waveSin=1;
		}
	}
	
	protected void boundaryDetection()
	{
		//left boundary
		if(xPos - fishWidth2/2 < FishPanel.POND_X)
		{
			accX = 10;
		}
		
		//right boundary
		if(xPos + fishWidth2/2 > FishPanel.POND_X + FishPanel.POND_W)
		{
			accX = -10;
		}
		
		//bootom boundary
		if(yPos + fishWidth2/2 > FishPanel.POND_H + FishPanel.POND_Y)
		{
			accY = -10;
		}
		
		//upper boundary
		if(yPos - fishWidth2/2 < FishPanel.POND_Y)
		{
			accY = 10;
		}
	}
	
	public boolean checkHeadOn(Fish otherFish)
	{
		if ((facingRight == true && otherFish.facingRight == false) || (facingRight == false && otherFish.facingRight == true))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean collide(Fish otherFish)
	{
		if(waveSin%25==0)
		{
			collided=false;
		}
		
		if(collided==false)
		{
			if(Math.abs(otherFish.yPos - yPos) <= (otherFish.fishHeight2/2 + fishHeight2/2) && Math.abs(otherFish.xPos - xPos) <= (otherFish.fishWidth2/2 + fishWidth2/2)) 		
		    {
		    	if(xSpeed<0)
		    	{
		    		xSpeed -= collisionSpeed;
		    	}
		    	else 
		    	{
		    		xSpeed += collisionSpeed;
		    	}
		    	
		    	if(otherFish.xSpeed<0)
		    	{
		    		otherFish.xSpeed -= collisionSpeed;
		    	}
		    	else 
		    	{
		    		otherFish.xSpeed += collisionSpeed;
		    	}
		    	
		    	xSpeed *= -1;
				scaleX *= -1;
				collided = true;
				
				if(facingRight == true)
				{
					facingRight = false;
				}
				else if (facingRight == false)
				{
					facingRight = true;
				}
				
				otherFish.xSpeed *= -1;
				otherFish.scaleX *= -1;
				otherFish.collided = true;
				
				if(otherFish.facingRight == true)
				{
					otherFish.facingRight = false;
				}
				else if (otherFish.facingRight == false)
				{
					otherFish.facingRight = true;
				}
				return true;
		    }
		    else
		    {
		    	return false;
		    }  
		}
		else
		{
		    return false;
		}
	}
	
	public boolean detectOffScreen()
	{
		// left and right boundary 
		if(xPos<-fishWidth2/2 -200 || xPos > FishPanel.PANEL_WIDTH+fishWidth2/2 +200)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean eat(Fish otherFish) 
	{
		boolean eat = false;
		if (randScale > otherFish.randScale) 
		{
			eat = true;
			sizeUp(randScale);
		} 
		else 
		{
			otherFish.sizeUp(otherFish.randScale);
		}
		
		return eat;
	}
	
	protected void sizeUp(double newScale)
	{
		if(randScale<1.5)
		{
			double upScale = 1.15;
			scaleX*=upScale;
			scaleY*=upScale;
			randScale*=upScale;
			fishWidth2 *= upScale;
			fishHeight2*= upScale;
		}
	}
}


