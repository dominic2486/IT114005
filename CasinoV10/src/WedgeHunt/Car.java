package WedgeHunt;

/**
 * Jack Brand
 * 2018
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
public class Car extends JComponent
{
	//instance variables
	int x;
	int y;
	int delX;
	int delY;
	int picWidth;
	int picHeight;
	int difficulty;
	boolean side;
	boolean dead;
	boolean titleCard;
	boolean endCard;
	boolean highScore;
	String picture;
	Background background;
	Sound sound;

	//constructor
	public Car(int iWidth, int iHeight, int iDifficulty, String iPicture)
	{
		//sets instance variables
		picWidth = iWidth;
		picHeight = iHeight;
		difficulty = iDifficulty; //max game difficulty is 20, extra level it is 25
		//difficulty must be > 5
		//starting difficulty is 5 to guarantee no y movement on first level
		//difficulties go 5, 7, 10, 14, 17, 20
		picture = iPicture;
		background = new Background();
		
		//car is not dead and is just regular
		dead = false;
		titleCard = false;
		endCard = false;
		highScore = false;
		
		//decides what side target starts on
		double randSide = Math.random();
		if(randSide < .5)
		{
			x = -200;
			side = false;
		}
		else
		{
			x = 900;
			side = true;
		}
		
		//changes x velocity depending if left or right
		//by setting delX to difficulty, can guarantee game speeds up
		if(side)
		{
			delX = difficulty * -1;
		}
		else
		{
			delX = difficulty;
		}
		
		//decides y starting position
		double randY = Math.random();
		y = (int)(200 + randY * 100);
				
		//decides y speed
		double ySpeedRand = Math.random();
		delY = (int)(ySpeedRand * (difficulty - 5));//-5 because if not it will be too hard
				
		//randomly decides if target goes up or down
		double upOrDown = Math.random();
		if(upOrDown > .5)
		{
			delY = delY * -1;
			//makes sure car does not only pass through corners
			y += 125;//less than 150 cause 150 puts it too low
		}
		else
		{
			y -= 150;
		}
	}
	
	@SuppressWarnings("static-access")
	public void die()
	{
		//target drops down screen
		dead = true;
		delX = 0;
		delY = 3;
		background.score += 100;
		
	}	
	
	//checks if a point is within the target
	public boolean contains(int checkX, int checkY)
	{
		//point must be in all of the target's bounds
		if(checkX > x && checkX < x + picWidth && checkY > y && checkY < y + picHeight)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	//checks if the target is outside the limits 
	@SuppressWarnings("static-access")
	public boolean isOffScreen()
	{
		if(x < -300 || x > 1100 || y < -100 || y > 700)
		{
			if(!titleCard)
			{
				//reduces enemies left
				background.enemiesLeft --;
			}
		
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void setSpeed()
	{
		delX = 1;
	}
	
	public boolean isDead() 
	{
		return dead;
	}
	
	//registers the target as a title card
	public void makeTitle()
	{
		x = -180;
		y = 100;
		delX = 5;
		delY = 0;
		titleCard = true;
	}
	
	//registers the target as an end card
	public void makeEnd()
	{
		x = -190;
		y = 50;
		delX = 1;
		delY = 0;
		endCard = true;
	}
	
	//registers the target as an end card
	public void makeHighScore()
	{
		highScore = true;
	}

	
	//returns if the target is a title
	public boolean isTitle()
	{
		return titleCard;
	}
	
	//returns if the target is an end card
	public boolean isEnd()
	{
		return endCard;
	}
	
	//paints the picture and the background
	@SuppressWarnings({ "static-access", "unused" })
	public void paint(Graphics g)
	{

		//if it is a regualar target, draw background and pic
		if(!titleCard && !endCard && !highScore)
		{
			background.paintComponent(g);
			x = x + delX;
			y = y + delY;
			
			BufferedImage image = null;

			try //loads in image
			{
				//System.out.println(getClass().getClassLoader().getResource("WHResources/bagel1.png").getPath());
				//image = ImageIO.read(new File(("src/"+picture)));
				image = ImageIO.read(getClass().getClassLoader().getResource(picture));
			} 
			catch (IOException e)
			{
				// TODO Auto-generated catch block
			}	
			
			g.drawImage(image, x, y, this);
		}
		
		//if it is a title, draw the level
		else if(titleCard)
		{
			background.paintComponent(g);
			y += delY;
			x += delX;
			g.setFont(new Font("Impact", Font.BOLD, 50));
			g.setColor(Color.BLACK);
			g.drawString("Level: " + background.level, x, y);
		}
		
		//if it is end, draw in game over
		else if(endCard)
		{
			background.paintComponent(g);
			y += delY;
			int maxX;
		
			background.enemiesLeft = 0;
			g.setFont(new Font("Impact", Font.BOLD, 50));
			g.setColor(Color.BLACK);
			
			x = 260;
			g.drawString("Game Over", x, y);

		}
		
		//if it is the final high score section, paint all high scores
		else if(highScore)
		{
			BufferedImage image = null;

			try //loads in image
			{
				image = ImageIO.read(getClass().getResource("WHResources/bagelNosh1.jpg"));
			} 
			catch (IOException e)
			{
				// TODO Auto-generated catch block
			}	
			
			g.drawImage(image, 0, 0, this);
			
			g.setFont(new Font("ComicSansMS", Font.BOLD, 50));
			g.setColor(Color.BLACK);
			int yPos = 140;
			int shift = 100;
			int counter = 0;
			
			g.drawString("HIGH SCORES", 230, 40);
			g.setColor(Color.WHITE);
			
			//prints high scores in order
			while(counter < 5)
			{
				g.drawString(Test.aL.get(counter).name, 150, yPos);
				g.drawString("" + Test.aL.get(counter).score, 400, yPos);
				yPos += shift;
				counter ++;
			}
			
		}
		
	}
	
	public void setDelX(int setter)
	{
		delX = setter;
	}
	
	public void setDelY(int setter)
	{
		delY = setter;
	}
	
	
	
}
