import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.geom.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Timer;

public class FishPanel extends JPanel implements ActionListener {

	public static final int PANEL_WIDTH=1200;
	public static final int PANEL_HEIGHT=800;
	
	private Ellipse2D.Double pebble;
	private Arc2D.Double weed;
	private Rectangle2D.Double pond;
	
	private JButton replayButton;
	private boolean replay = false;
	private JButton startButton;
	private boolean start = false;
	private Text txt;
	private JFrame frame;

	
	public final static int POND_X = 0;
	public final static int POND_Y = 0;
	public final static int POND_W = PANEL_WIDTH;
	public final static int POND_H = PANEL_HEIGHT;
	
	private final static int PEBBLE_W = 100;
	private final static int PEBBLE_H = 50;
	private final static int WEED_W = 25;
	private final static int WEED_H = 200;
	

	public static int mouseX = PANEL_WIDTH/2;
	public static int mouseY = PANEL_HEIGHT/2;

	
	private Timer timer;

	private Color pondColor = new Color(123,186,235);
	private Color pebbleColor = new Color(100,100,100);
	private Color weedColor = new Color(32,186,68);
	private Color bubbleColor = new Color(153,210,255);
	
	private int numFish = 10;
	private Fish fish;
	private Fish fish2;
	private Shark fish3;
	private PlayerFish p1;
	private ArrayList<Fish> fishies = new ArrayList<Fish>();
	private ArrayList<Bubbles> bubbles = new ArrayList<Bubbles>();
	
	private boolean up, down, left, right;
	
	private double playerSpeed = 2;

	public class MyKeyListener extends KeyAdapter 
	{
		@Override
		public void keyPressed(KeyEvent e) 
		{
			if (e.getKeyCode() == KeyEvent.VK_RIGHT)
				right = true;
			if (e.getKeyCode() == KeyEvent.VK_LEFT)
				left = true;
			if (e.getKeyCode() == KeyEvent.VK_UP)
				up = true;
			if (e.getKeyCode() == KeyEvent.VK_DOWN)
				down = true;
		}

		@Override
		public void keyReleased(KeyEvent e) 
		{
			if (e.getKeyCode() == KeyEvent.VK_RIGHT)
				right = false;
			if (e.getKeyCode() == KeyEvent.VK_LEFT)
				left = false;
			if (e.getKeyCode() == KeyEvent.VK_UP)
				up = false;
			if (e.getKeyCode() == KeyEvent.VK_DOWN)
				down = false;
		}
	}
	
	public FishPanel(JFrame frame)
	{
		this.frame = frame;
		pebble = new Ellipse2D.Double();
		weed = new Arc2D.Double();
		pond = new Rectangle2D.Double();
		
		setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		
		timer = new Timer(60, this);
		//timer.start();
		timer.addActionListener(this);
		
		replayButton = new JButton("Replay");
		add(replayButton, BorderLayout.NORTH);
		replayButton.setVisible(false);
		replayButton.addActionListener(this);
		
		startButton = new JButton("Start");
		add(startButton, BorderLayout.SOUTH);
		startButton.setVisible(true);
		startButton.addActionListener(this);
		
		txt = new Text(0,4);
		
		MyKeyListener mkl = new MyKeyListener();
		this.addKeyListener(mkl);
		this.setFocusable(true);
		
		for(int i = 0; i < numFish; i++)
		{
			fishies.add(new Herbivore());
		}
		fishies.add(new Shark());
		fishies.add(new Shark());
		p1 = new PlayerFish();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		
		drawPond(g2);
		p1.drawFish(g2);
		txt.headsUpDisplay(g2);
		
		for(int i = 0; i < fishies.size(); i++)
		{
			fish = fishies.get(i);
			fish.drawFish(g2);
			p1.collide(fish);
			
			for(int j=1; j<fishies.size(); j++)
			{
				fish2 = fishies.get(j);
				fish.collide(fish2);
			}
		}
		
		for(int i = 0; i < bubbles.size(); i++)
		{
			bubbles.get(i).drawBubble(g2);
		}
		
		if (txt.getLives() <= 0)
		{
			txt.gameOver(g2);
			timer.stop();
			replayButton.setVisible(true);
			replay = true;
		}
		
		if(txt.getScore() >= 300)
		{
			txt.gameWin(g2);
			timer.stop();
			replayButton.setVisible(true);
			replay = true;
		}
		
		if(start == false)
		{
			txt.introScreen(g2);
		}
	}
	
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		
		// create a frame and put this panel on the frame (you can just call JFrame or pass in a string for the name)
		JFrame frame = new JFrame("Shark! Shark! Remake");
		
		//create a new panel
		FishPanel fishPanel = new FishPanel(frame);
		
		frame.add(fishPanel);
		
		// adjust the frame size
		frame.pack();
		
		//set the frame to visible
		frame.setVisible(true);	
	}
	
	public void actionPerformed(ActionEvent arg0) 
	{
		//System.out.println(bubbles.size());
		if(startButton != null)
		{
			timer.start();
			startButton.setVisible(false);
			start = true;
		}
		//System.out.println(p1.randScale);
		//updates the fish's wave and movement patterns 
		for(int i = 0; i < fishies.size(); i++)
		{
			fish = fishies.get(i);
			fish.update();
			if(fish.detectOffScreen())
			{
				fishies.remove(fish);
			}
		}

		//checks and implements shark eating herbivore,
		//shark eating the player-fish if its headOn (doesn't involve size)
		//the herbivore killing the player-fish if its bigger,
		//the fish eating the herbivore if itself is bigger,
		for(int i = 0; i < fishies.size(); i++)	
		{
			fish = fishies.get(i);
			if(fish instanceof Herbivore&&p1.checkHeadOn(fish)&&p1.collide(fish))
			{
				p1.eat(fish);
				destroy(fish);
				txt.increaseScore(fish.fishScore);
				break;
			}
			else if(fish.randScale>p1.randScale&&fish.checkHeadOn(p1)&&fish.collide(p1))
			{
				fish.eat(p1);
				bubbles.add(new Bubbles(p1.xPos,p1.yPos));
				p1 = new PlayerFish();
				txt.decreaseLives();
				break;
			}
			
			if(fishies.get(i) instanceof Shark)
			{
				fish3 = (Shark) fishies.get(i);
				
				if(fish3.sharkTailCollision(p1))
				{
					System.out.println("hi");
					fish3.stasis();
					if(fish3.getLives()<0)
					{
						fishies.remove(i);
					}
					break;
				}
				
				else if(fish3.sharkBodyCollision(p1))
				{
					fish.eat(p1); 
					bubbles.add(new Bubbles(p1.xPos,p1.yPos));
					txt.decreaseLives();
					p1 = new PlayerFish();
					break;
				}

				for(int j = 0; j < fishies.size(); j++)	
				{	
					fish2 = fishies.get(j);
					if(fishies.get(j) instanceof Herbivore)
					{
						if(fish.collide(fish2)) 
						{
							fish.eat(fish2); 
							destroy(fish2);
							break;
						}
					}	
				}
			}
		}
		
		//respawning fish if there are less than 8 fish
		respawn();
		
		//moves player fish with keys
		playerMove();
	
		//turning the fish from left to right depedning on keys
		p1.update(right, left);
		
		if(replay)
		{
			frame.dispose();
			replay = false;
			frame = new FishApp("Fish Game");
		}
		
		for(int i = 0; i < bubbles.size(); i++)
		{
			bubbles.get(i).updateBubble();
			if(bubbles.get(i).getYPos()<=-10)
			{
				bubbles.remove(i);
			}
		}
		repaint();
	}
	
	private void respawn()
	{
		if(fishies.size() < 8)
		{
			//spawning fish randomly
			if(Util.random(0, 1) > 0.8)
			{
				fish = new Shark();
				fishies.add(fish);
			}
			else
			{
				fish = new Herbivore();
				fishies.add(fish);
			}
			
			if(Util.random(0, 2) > 1)
			{
				//left side respawn 
				fish.xPos = -100;
			}
			else
			{
				//right side respawn
				fish.xPos = PANEL_WIDTH+100;
				fish.xSpeed*=-1;
				fish.scaleX*=-1;
				fish.facingRight=false;
			}
		}
	}
	
	private void playerMove()
	{
		if(up)
		{
			p1.addAcc(0, -playerSpeed);
		}
		if(down)
		{
			p1.addAcc(0, playerSpeed);
		}
		if(left)
		{
			p1.addAcc(-playerSpeed,0);
		}
		if(right)
		{
			p1.addAcc(playerSpeed, 0);
		}
	}
	
	private void destroy(Fish fish)
	{
		bubbles.add(new Bubbles(fish.xPos,fish.yPos));
		fishies.remove(fish);
	}
	
	private void setPondAttributes()
	{
		pond.setFrame(POND_X,POND_Y,POND_W,POND_H);
		pebble.setFrame(POND_X + POND_W - PEBBLE_W, POND_Y + POND_H - PEBBLE_H, PEBBLE_W, PEBBLE_H);
		weed.setArc(POND_X + POND_W - PEBBLE_W* + 10, 
				POND_Y + POND_H - PEBBLE_H-50, 
				WEED_W, WEED_H, 0, 80,Arc2D.PIE);
	}

	private void drawPond(Graphics2D g2)
	{
		setPondAttributes();
		g2.setColor(pondColor);
		g2.fill(pond);
		
		//draws pebbles
		g2.setColor(pebbleColor);
		for(int i = 1; i<14;i++)
		{
			pebble.setFrame(POND_X + POND_W - PEBBLE_W*i, 
							POND_Y + POND_H - PEBBLE_H, 
							PEBBLE_W, PEBBLE_H);
			g2.setColor(Color.black);
			g2.draw(pebble);
			g2.setColor(pebbleColor);
			g2.fill(pebble);
		}
		
		//draws weed
		for (int i = 1; i < 14; i++)
		{
			weed.setArc(POND_X + POND_W - PEBBLE_W*i + 10, 
						POND_Y + POND_H - PEBBLE_H-50, 
						WEED_W, WEED_H, 0, 80,Arc2D.PIE);
			g2.setColor(weedColor);
			g2.draw(weed);
			g2.fill(weed);
		}	
	}
	
	public class MyMouseMotionListener extends MouseMotionAdapter
	{
		public void mouseMoved(MouseEvent e)
		{
			mouseX = e.getX();
			mouseY = e.getY();
		}
	}
}

