import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

public class Herbivore extends Fish 
{
	public Herbivore()
	{
		//all variables after the super(); are overridden
		super();
		randScale = Util.random(0.2, 0.5);
		scaleX = -1*randScale;
		scaleY = 1*randScale;
		
		if(randScale<=0.3)
		{
			fishScore = 5;
		}
		else if (randScale <= 0.4)
		{
			fishScore = 10;
		}
		else
		{
			fishScore = 20;
		}
			
		
		fishColor = new Color((int) Util.random(150, 256),(int) Util.random(150, 256),(int) Util.random(150, 256));
		outlineColor = new Color((int) Util.random(0, 100),(int) Util.random(0, 100),(int) Util.random(0, 100));
		tailColor = new Color((int) Util.random(0, 256),(int) Util.random(0, 256),(int) Util.random(0, 256));
		dorsalFinColor = new Color((int) Util.random(0, 256),(int) Util.random(0, 256),(int) Util.random(0, 256));
		
		//because FISH_WIDTH and FISH_HEIGHT were integrated into the drawing of the fish, changing their values directly would destroy the fish
		//thus i passed the values onto secondary variables that are used to handle boundary detection and scaling as they are dependent on each other but not the fish
		fishWidth2 = FISH_WIDTH+50; 
		fishWidth2 *= Math.abs(scaleX);
		fishHeight2 = FISH_HEIGHT;
		fishHeight2 *= Math.abs(scaleY);
		
		buffer = 100;
		xPos = 50+Util.random(FishPanel.POND_X + buffer +fishWidth2/2, FishPanel.POND_W+FishPanel.POND_X-buffer-fishWidth2/2);
		yPos = Util.random(FishPanel.POND_Y  +fishHeight2/2+buffer/2, FishPanel.POND_H+FishPanel.POND_Y-buffer-fishHeight2/2);
		xSpeed = Util.random(4, 8)*randScale;
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
		
		setAttributes();
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
		//g2.fill(headLine);

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
	
	
}
