import java.awt.*;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class HorseComponent extends JComponent
{
	/**
	 * 
	 */
	
	//final BufferedImage image = ImageIO.read(new File("C:\\Users\\18quid01\\Downloads"));
	private static final long serialVersionUID = 1L;
	HorseDrawer d0,d1,d2,d3,d4;
	int num;
	JFrame ref;
	
	public HorseComponent()
	{
		d0 = new HorseDrawer(0,this);
		d1 = new HorseDrawer(1,this);
		d2 = new HorseDrawer(2,this);
		d3 = new HorseDrawer(3,this);
	}
	
	public void paintComponent(Graphics g)
	{
		g.setColor(Color.YELLOW);
		g.fillRect(0, 0, 600, 600);
		d0.draw(g);
		d1.draw(g);
		d2.draw(g);
		d3.draw(g);
	}

	public void resetHorses()
	{
		d0 = new HorseDrawer(0,this);
		d1 = new HorseDrawer(1,this);
		d2 = new HorseDrawer(2,this);
		d3 = new HorseDrawer(3,this);
	}
	
	public int startAnimation() 
	{
		class AnimationRunnable implements Runnable
		{
			public void run()
			{
				while(!(d0.test()||d1.test()||d2.test()||d3.test()))
				{
					//System.out.println(num);
					d0.doIt();
					/*d1.doIt();
					d2.doIt();
					d3.doIt();
					d4.doIt();*/
				}
					
			}
		}
		Runnable r = new AnimationRunnable();
		Thread t = new Thread(r);
		t.start();
		
		while(t.isAlive())
		{
			try 
			{
				Thread.sleep(100);
			} catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
		if(d0.test())
			return 1;
		else if(d1.test())
			return 2;
		else if(d2.test())
			return 3;
		else if(d3.test())
			return 4;
		return -1;
	}
	
	
}
