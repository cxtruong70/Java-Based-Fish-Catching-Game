import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Text 
{
	private Font headFont;
	private Font gameOverFont;
	private Font introScreenFont;
	private Font howToPlayFont;
	private int score;
	private int lives;
	
	public Text(int s, int l)
	{
		score = s;
		lives = l;
		headFont = new Font("Helvetica", Font.BOLD, 20);
		gameOverFont = new Font("Helvetica", Font.BOLD,65);	
		introScreenFont = new Font ("Tahoma",Font.BOLD,65);
		howToPlayFont = new Font("Tahoma", Font.BOLD, 30);
	}
	
	//HUD method
	public void headsUpDisplay(Graphics2D g2)
	{
		g2.setColor(Color.white);
		g2.setFont(headFont);
		g2.drawString("Score: " + score, 30, 25);
		g2.drawString("Lives: " + lives, FishPanel.PANEL_WIDTH - 110, 25);
	}
	
	public void introScreen(Graphics2D g2)
	{
		g2.setColor(new Color(255, 253, 168));
		Rectangle2D.Double bg = new Rectangle2D.Double(FishPanel.POND_X,FishPanel.POND_Y,FishPanel.PANEL_WIDTH,FishPanel.PANEL_HEIGHT);
		g2.fill(bg);
		
		g2.setFont(introScreenFont);
		g2.setColor(Color.black);
		g2.drawString("Shark Shark Remake!", FishPanel.PANEL_WIDTH/2-450,FishPanel.PANEL_HEIGHT/2);
		
		g2.setFont(howToPlayFont);
		g2.setColor(Color.black);
		g2.drawString("Movement Controls: ", FishPanel.PANEL_WIDTH/2-450,FishPanel.PANEL_HEIGHT/2+70);
		g2.drawString("Get 300 Points to win!", FishPanel.PANEL_WIDTH/2-450,FishPanel.PANEL_HEIGHT/2+30);
		
		g2.setFont(introScreenFont);
		g2.setColor(Color.ORANGE);
		g2.drawString("|Up|", FishPanel.PANEL_WIDTH/2-100,FishPanel.PANEL_HEIGHT/2+150);
		g2.drawString("|Left| |Down| |Right|", FishPanel.PANEL_WIDTH/2-250-125,FishPanel.PANEL_HEIGHT/2+250);
		
	}
	
	public void gameOver(Graphics2D g2)
	{
		// red background
		g2.setColor(new Color(255, 253, 168));
		Rectangle2D.Double bg = new Rectangle2D.Double(FishPanel.POND_X,FishPanel.POND_Y,FishPanel.PANEL_WIDTH,FishPanel.PANEL_HEIGHT);
		g2.fill(bg);
		
		g2.setFont(gameOverFont);
		g2.setColor(Color.black);
		g2.drawString("Game Over", FishPanel.PANEL_WIDTH/2-150,FishPanel.PANEL_HEIGHT/2+20);
		g2.drawString("Score: " + score, FishPanel.PANEL_WIDTH/2-150,FishPanel.PANEL_HEIGHT/2+90);
	}
	
	public void gameWin(Graphics2D g2)
	{
		// red background
		g2.setColor(new Color(255, 253, 168));
		Rectangle2D.Double bg = new Rectangle2D.Double(FishPanel.POND_X,FishPanel.POND_Y,FishPanel.PANEL_WIDTH,FishPanel.PANEL_HEIGHT);
		g2.fill(bg);
		
		g2.setFont(gameOverFont);
		g2.setColor(Color.black);
		g2.drawString("You Win!", FishPanel.PANEL_WIDTH/2-150,FishPanel.PANEL_HEIGHT/2+20);
		g2.drawString("Score: " + score, FishPanel.PANEL_WIDTH/2-150,FishPanel.PANEL_HEIGHT/2+90);
	}
	
	public void increaseScore(int fishScore)
	{
		score+=fishScore;
	}
	
	public void decreaseLives()
	{
		lives--;
	}
	
	public int getScore()
	{
		return score;
	}
	
	public int getLives()
	{
		return lives;
	}
}
