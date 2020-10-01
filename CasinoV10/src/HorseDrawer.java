import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class HorseDrawer
{
	public JComponent component;
	int a,xt;
	BufferedImage image=null;
	imageShit is=null;
	final int IMAGELENGTH=100;
	final int IMAGEHEIGHT=50;
	
	public HorseDrawer(int x ,JComponent aCom)
	{
		xt=x;
		component = aCom;
		try
		{
			image = ImageIO.read(getClass().getResource("images/horse.jpg"));
			is = new imageShit();
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}
		
	public void update()
	{
		Random r = new Random();
		a+=r.nextInt(10);
	}
	
	public void draw(Graphics g)
	{
		
		update();
		try 
		{
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		g.setColor(Color.black);
		//g.fillRect(0, 0, 600, 600);
		g.drawImage(image, a-IMAGELENGTH, xt*100+10, IMAGELENGTH, IMAGEHEIGHT, is);
		g.drawString(""+(xt+1), 10, xt*100+10+(IMAGEHEIGHT/2));
		
	}

	public boolean test()
	{
		if(a>component.getWidth())
			return true;
		return false;
	}
	
	public void doIt()
	{
		component.repaint();
	}

	class imageShit implements ImageObserver
	{
		public boolean imageUpdate(Image arg0, int arg1, int arg2, int arg3, int arg4, int arg5)
		{
			return true;
		}
		
	}
	
}
