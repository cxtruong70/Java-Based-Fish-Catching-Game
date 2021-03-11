import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

public class PlayerFish extends Fish 
{

	public PlayerFish()
	{
		super();
		randScale = 0.4;
		scaleX = -1*randScale;
		scaleY = 1*randScale;
		
		//changed
		xPos = FishPanel.POND_W/2;
		yPos = FishPanel.POND_H/2;
		xSpeed = 0;
		ySpeed = 0;
		damp = 0.90f;
		fishColor = new Color(242, 240, 121);
		outlineColor = new Color(170, 169, 85);
	
		scaleX = -1*randScale;
		scaleY = 1*randScale;
		
		//changes with the changes above
		fishWidth2 = FISH_WIDTH; 
		fishWidth2 *= Math.abs(scaleX);
		fishHeight2 = FISH_HEIGHT;
		fishHeight2 *= Math.abs(scaleY);
		
		setAttributes();
	}
	
	public void setAttributes()
	{
		super.setAttributes();
	}
	
	public void addAcc(double accX, double accY)
	{
		//more of a setter but we're just going along with the UML
		this.accX = accX;
		this.accY = accY;	
	}
	
	public void update(Boolean right, Boolean left)
	{
		super.update();
		move();
		
		if(!facingRight && right)
		{
			scaleX *= -1;
			facingRight = true;
		}
		
		if(facingRight && left)
		{
			scaleX *= -1;
			facingRight = false;
		}
		
		boundaryDetection();
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
		    	
				collided = true;
//				
				
				otherFish.collided = true;
//				otherFish.xSpeed *= -1;
//				otherFish.scaleX *= -1;
//				
//				
//				if(otherFish.facingRight == true)
//				{
//					otherFish.facingRight = false;
//				}
//				else if (otherFish.facingRight == false)
//				{
//					otherFish.facingRight = true;
//				}
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
	
	private void move()
	{	
		xSpeed += accX;
		ySpeed += accY;	
		xPos += xSpeed;
		yPos += Math.sin(Math.toDegrees((double)waveSin/1000))+ySpeed;
		xSpeed*=damp;
		ySpeed*=damp;
		accX=0;
		accY=0;
	}
}
