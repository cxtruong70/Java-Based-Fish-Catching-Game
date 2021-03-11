import javax.swing.JFrame;


//JFrame is a top level container, at the same level as JApplet.
//JFrame provides support and specifies the window operations (what to do whe resize, close, etc)
public class FishApp extends JFrame 
{
	public FishApp(String title) 
	{
		super(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FishPanel fpnl = new FishPanel(this);
		this.add(fpnl);
		this.pack();
		this.setVisible(true);
	}
	
	public static void main (String[] args)
	{
		new FishApp("FishApp");
	}
}