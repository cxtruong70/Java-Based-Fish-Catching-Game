import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class Shark extends Fish
{
	private int lives;
	private int stasisCount = 0;

	public Shark()
	{
		// all the variables that are in this Shark constructor are overridden
		super();
		randScale = Util.random(0.7, 1.0);
		scaleX = -1.3*randScale;
		scaleY = 1*randScale;
		
		fishScore = 50;
		
		fishColor = new Color(59,66,94);
		outlineColor = new Color(46,49,63);
		tailColor = new Color(18, 42, 81);
		dorsalFinColor = new Color(18, 42, 81);
		eyeColor = new Color (0,0,0);
		
		//because FISH_WIDTH and FISH_HEIGHT were integrated into the drawing of the fish, changing their values directly would destroy the fish
		//thus i passed the values onto secondary variables that are used to handle boundary detection and scaling as they are dependent on each other but not the fish
		//this is left in because it is overridden
		fishWidth2 += 100;
		fishHeight2 += 75;
		fishWidth2 *= Math.abs(scaleX);
		fishHeight2 *= Math.abs(scaleY);
		
		xSpeed = Util.random(6, 10)*randScale;
		xSpeed = 0;
		ySpeed = 0;
		lives = 4;
		
		setAttributes();
	}
	
	protected void setAttributes()
	{
		
		//	
		test.setFrame(1,1,1,1);
		test2.setFrame(1,1,1,1);
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
		g2.setColor(eyeColor);
		g2.fill(eye);
		
		//draw fins
		g2.setColor(outlineColor);
		// fin 1: front fin
		g2.drawPolygon(new int[] {-30,-10,-10} , new int[] { 35, 65, 30},3);
		g2.fillPolygon(new int[] {-30,-10,-10} , new int[] { 35, 65, 30},3);
		
		// fin 2: dorsal fin
		//g2.drawPolygon(new int[] {-20,0,35,50} , new int[] { -38, -65, -50, -32},4);
		g2.fillPolygon(new int[] {-20,20,25} , new int[] { -38, -75, -37},3);
		// fin 3: arc fin
		g2.setColor(outlineColor);
		g2.draw(arcFin);
		g2.fill(arcFin);

		//draw tail
		g2.setColor(outlineColor);
		g2.drawPolygon(new int[] {70,70,120} , new int[] {20,-20,-60},3);
		g2.fillPolygon(new int[] {70,70,120} , new int[] {20,-20,-60},3);
		g2.drawPolygon(new int[] {70,70,90} , new int[] {20,-20,40},3);
		g2.fillPolygon(new int[] {70,70,90} , new int[] {20,-20,40},3);
		
		

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
		g2.setColor(Color.white);
		g2.fill(test);
		g2.fill(test2);
		
		g2.setTransform(transform);
	}
	
	public boolean collide(Fish otherFish)
	{
		/*//thinking about doing something liket his instead
		 * if(waveSin%50==0)
		{
			collided=false;
		}
		if(collided==false)
		{
			if(sharkBodyCollision(otherFish)) 		
		    {
		    	collided = true;
				return true;
		    }
		    else if(sharkTailCollision(otherFish))
		    {
		    	collided = true;
		    	return true;
		    }  
		}
		 */
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
		    	collided = true;
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
	
	public boolean sharkBodyCollision(Fish otherFish)
	{
		if(facingRight)
		{
			//test.setFrame(-FISH_WIDTH/2,-FISH_HEIGHT/2, fishWidth2-75,fishHeight2);
			//collision detection for the tail when facing right
			if(Math.abs(otherFish.xPos - (xPos-FISH_WIDTH/2)) <= (fishWidth2-75)/2 + otherFish.fishWidth2/2
			&& Math.abs(otherFish.yPos - (yPos-FISH_HEIGHT/2)) <= fishHeight2/2 + otherFish.fishHeight2/2)
			{
				stasis();
				return true;
				
			}
		}
		else if(!facingRight)
		{
			//collision detection for the tail when facing left
			if(Math.abs(otherFish.xPos - (xPos-FISH_WIDTH/2)) <= (fishWidth2-75)/2 + otherFish.fishWidth2/2
			&& Math.abs(otherFish.yPos - (yPos-FISH_HEIGHT/2)) <= fishHeight2/2 + otherFish.fishHeight2/2)
			{
				stasis();
				return true;
			}
		}
		return false;
	}
	
	public boolean sharkTailCollision(Fish otherFish)
	{
		//tail location
		//test.setFrame(-FISH_WIDTH/2+150,-FISH_HEIGHT/2-20, fishWidth2/4,fishHeight2+25);
		//body location
		//test.setFrame(-FISH_WIDTH/2,-FISH_HEIGHT/2, fishWidth2-75,fishHeight2);

		if(facingRight)
		{
			//collision detection for the tail when facing right
			if(Math.abs(otherFish.xPos - (xPos-FISH_WIDTH/2-150)) <= fishWidth2/8 + otherFish.fishWidth2/2
			&& Math.abs(otherFish.yPos - (yPos-FISH_HEIGHT/2)) <= (fishHeight2+25)/2 + otherFish.fishHeight2/2)
			{
				stasis();
				lives--;
				return true;
			}
		}
		else if(!facingRight)
		{
			//collision detection for the tail when facing left
			if(Math.abs(otherFish.xPos - (xPos-FISH_WIDTH/2+150)) <= fishWidth2/8 + otherFish.fishWidth2/2
			&& Math.abs(otherFish.yPos - (yPos-FISH_HEIGHT/2)) <= (fishHeight2+25)/2 + otherFish.fishHeight2/2)
			
			{
				lives--;
				return true;
			}
			
		}
		return false;
	}
	
	public int getLives()
	{
		return lives;
	}
	
	public void stasis()
	{
		stasisCount++;
		
		if(stasisCount <= 4)
		{
		
			fishColor = new Color(229, 224, 80);
			outlineColor = new Color(229, 224, 80);
			tailColor = new Color(229, 224, 80);
			dorsalFinColor = new Color(229, 224, 80);
			eyeColor = new Color (0,0,0);
			
		}
		else
		{
			stasisCount = 0;
		}
		
		if(stasisCount == 0)
		{
			fishColor = new Color(59,66,94);
			outlineColor = new Color(46,49,63);
			tailColor = new Color(18, 42, 81);
			dorsalFinColor = new Color(18, 42, 81);
			eyeColor = new Color (0,0,0);
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
		if(randScale<1)
		{
			double upScale = 1.1;
			scaleX*=upScale;
			scaleY*=upScale;
			randScale*=upScale;
			fishWidth2 *= upScale;
			fishHeight2*= upScale;
		}
	}
}
