import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class SlotReel 
{
	public JComponent parentComponent;
	int position; //Stores the x position of this reels
	BufferedImage[] images; //array of all the image references 
	String[] imageNames; //String array that mirrors the buffered image array to check what is the winning
	
	imageShit is=null; //Image observer to allow for the images to be drawn
	final int IMAGELENGTH=100;
	final int IMAGEHEIGHT=100;
	
	Random r;
	int reelLoc; //Stores what position in the reel array its at
	
	String middleRow;

	
	/**
	 * Main Constructor:
	 * Pre-Conditions:
	 * 	int x is the index of which reel it is, to set its location
	 * 	JComponent component is the parent component where the reels are being placed
	 * 
	 * constructor finds and builds array of all the icon images and of strings to match them
	 * 
	 */
	public SlotReel(int x, JComponent component)
	{
		r = new Random();
		images = new BufferedImage[9];
		imageNames = new String[9];
		reelLoc=0;
		position=(x*150);
		parentComponent=component;
		middleRow=null;
		
		try
		{
			BufferedImage image7 = ImageIO.read(getClass().getResource("images/7.PNG"));
			BufferedImage imagebar = ImageIO.read(getClass().getResource("images/bar.PNG"));
			BufferedImage imagecherry = ImageIO.read(getClass().getResource("images/cherry.PNG"));
			BufferedImage imagelemon = ImageIO.read(getClass().getResource("images/lemon.PNG"));
			BufferedImage imageorange = ImageIO.read(getClass().getResource("images/orange.PNG"));
			BufferedImage imageplum = ImageIO.read(getClass().getResource("images/plum.PNG"));
			BufferedImage imagebigwin = ImageIO.read(getClass().getResource("images/bigwin.PNG"));
			BufferedImage imagebanana = ImageIO.read(getClass().getResource("images/banana.PNG"));
			BufferedImage imagewatermelon = ImageIO.read(getClass().getResource("images/watermelon.PNG"));
			
			images[0]=image7;
			imageNames[0]="7";
			images[1]=imagebar;
			imageNames[1]="bar";
			images[2]=imagecherry;
			imageNames[2]="cherry";
			images[3]=imagelemon;
			imageNames[3]="lemon";
			images[4]=imageorange;
			imageNames[4]="orange";
			images[5]=imageplum;
			imageNames[5]="plum";
			images[6]=imagebigwin;
			imageNames[6]="bigwin";
			images[7]=imagebanana;
			imageNames[7]="banana";
			images[8]=imagewatermelon;
			imageNames[8]="watermelon";
			
			Queue<BufferedImage> tempImages = new LinkedList<BufferedImage>();
			Queue<String> tempName = new LinkedList<String>();
			
			for(int i=0;i<images.length;i++)
			{
				Boolean test=true;
				int rand=r.nextInt(images.length);
				while(test)
				{
					rand=r.nextInt(images.length);
					
					if(tempImages.contains(images[rand]))
						test=true;
					else
					{
						test=false;
						tempImages.add(images[rand]);
						tempName.add(imageNames[rand]);
					}
				}
			}
			
			for(int i=0;i<images.length;i++)
			{
				images[i]=tempImages.remove();
				imageNames[i]=tempName.remove();
			}
						
			is = new imageShit();
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * returns string of what the middle icon is of this reel
	 * 
	 */
	public String getMiddle()
	{
		return imageNames[(reelLoc+2)%images.length];
	}

	public void reset()
	{
		reelLoc=0;
		try
		{
			BufferedImage image7 = ImageIO.read(getClass().getResource("images/7.PNG"));
			BufferedImage imagebar = ImageIO.read(getClass().getResource("images/bar.PNG"));
			BufferedImage imagecherry = ImageIO.read(getClass().getResource("images/cherry.PNG"));
			BufferedImage imagelemon = ImageIO.read(getClass().getResource("images/lemon.PNG"));
			BufferedImage imageorange = ImageIO.read(getClass().getResource("images/orange.PNG"));
			BufferedImage imageplum = ImageIO.read(getClass().getResource("images/plum.PNG"));
			BufferedImage imagebigwin = ImageIO.read(getClass().getResource("images/bigwin.PNG"));
			BufferedImage imagebanana = ImageIO.read(getClass().getResource("images/banana.PNG"));
			BufferedImage imagewatermelon = ImageIO.read(getClass().getResource("images/watermelon.PNG"));
			
			images[0]=image7;
			imageNames[0]="7";
			images[1]=imagebar;
			imageNames[1]="bar";
			images[2]=imagecherry;
			imageNames[2]="cherry";
			images[3]=imagelemon;
			imageNames[3]="lemon";
			images[4]=imageorange;
			imageNames[4]="orange";
			images[5]=imageplum;
			imageNames[5]="plum";
			images[6]=imagebigwin;
			imageNames[6]="bigwin";
			images[7]=imagebanana;
			imageNames[7]="banana";
			images[8]=imagewatermelon;
			imageNames[8]="watermelon";
			
			Queue<BufferedImage> tempImages = new LinkedList<BufferedImage>();
			Queue<String> tempName = new LinkedList<String>();
			
			for(int i=0;i<images.length;i++)
			{
				Boolean test=true;
				int rand=r.nextInt(images.length);
				while(test)
				{
					rand=r.nextInt(images.length);
					
					if(tempImages.contains(images[rand]))
						test=true;
					else
					{
						test=false;
						tempImages.add(images[rand]);
						tempName.add(imageNames[rand]);
					}
				}
			}
			
			for(int i=0;i<images.length;i++)
			{
				images[i]=tempImages.remove();
				imageNames[i]=tempName.remove();
			}
						
			is = new imageShit();
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Method required by JComponent to draw the image
	 * Pre-Condition:
	 * 	 Graphics g is passed in by JComponent super, by calling repaint(); 
	 */
	public void draw(Graphics g)
	{
		//System.out.println("drawing");
		try 
		{
			Thread.sleep(40);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//controls current location of where to be draw the image
		int tempHeight=0;
		
		g.setColor(Color.black);
		int compHeight = parentComponent.getHeight()/3;
		while(tempHeight<3)
		{
			if(tempHeight==0)
			{
				g.drawImage(images[reelLoc%images.length], position, tempHeight*(compHeight), IMAGELENGTH, IMAGEHEIGHT, is);
				middleRow=imageNames[reelLoc%images.length];
			}
			else
				g.drawImage(images[reelLoc%images.length], position, tempHeight*(compHeight), IMAGELENGTH, IMAGEHEIGHT, is);
			
			tempHeight++;
			reelLoc++;
		}
		reelLoc+=(images.length+2)/2;
		
	}
		
	/**
	 * Allows SlotComponent to call repaint of the reel
	 * 	which is JComponent super method
	 */
	public void paintIt()
	{
		parentComponent.repaint();
	}
	
	/**
	 * stupid required class to draw images
	 */
	class imageShit implements ImageObserver
	{
		public boolean imageUpdate(Image arg0, int arg1, int arg2, int arg3, int arg4, int arg5)
		{
			return true;
		}
		
	}
}
