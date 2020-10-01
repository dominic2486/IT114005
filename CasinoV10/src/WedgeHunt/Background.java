package WedgeHunt;
/**
 * Jack Brand
 * 2018
 */
import java.awt.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.image.*;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.Timer;

@SuppressWarnings("unused")
public class Background extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//variables
	//static because only one background
	static int level;
	static int score;
	static int moveCloud1;
	static int moveCloud2;
	static int cloud1X;
	static int cloud2X;
	static int enemiesLeft;
	static int highScore;
	static boolean askName;
	

	public Background() //constructor
	{
		moveCloud1 = 1;
		moveCloud2 = 2;	
		cloud1X = 400;
		cloud2X = 200;
		level = 1;
		enemiesLeft = 8;
		score = 0;
	}
	
	
	public void paintComponent(Graphics g) //draws the whole background
	{
		super.paintComponent(g);
		 
		//makes a sky blue color
		g.setColor(new Color(135, 206, 250));
		g.fillRect(0, 0, 800, 600);
		paintClouds(g);
		paintRyan(g);
		paintFloor(g);
		paintTrash(g);
		paintBagelNosh(g);
		paintThunder(g);
		g.setFont(new Font("Impact", Font.BOLD, 30));
		g.setColor(Color.BLACK);
		g.drawString("Score: " + score, 10, 33);
		g.setFont(new Font("Impact", Font.BOLD, 30));
		g.drawString("Enemies Left: " + enemiesLeft, 550, 33);
	
	}
	
	//draws the clouds and moves them with each iteration
	public void paintClouds(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.fillRect(cloud1X, 40, 100, 50);
		g.fillRect(cloud1X+25, 25, 50, 15);
		
		g.fillRect(cloud2X, 130, 100, 50);
		g.fillRect(cloud2X + 25, 110, 50, 20);
		
		cloud1X += moveCloud1;
		cloud2X += moveCloud2;
		
		if(cloud1X > 800)
		{
			cloud1X = -100;
		}
		if(cloud2X > 800)
		{
			cloud2X = -100;
		}
	}
	
	public void paintRyan(Graphics g)
	{
		//draws shoes
		g.setColor(Color.BLACK);
		g.fillRect(60, 500, 20, 20);
		g.fillRect(100, 500, 20, 20);
		
		//draws jeans
		g.setColor(Color.BLUE);
		g.fillRect(60, 460, 20, 40);
		g.fillRect(100, 460, 20, 40);
		
		//draws shirt
		g.setColor(new Color(127, 20, 25));
		g.fillRect(60, 380, 60, 80);
		g.fillRect(40, 380, 20, 60);
		g.fillRect(120, 380, 20, 60);
		g.setColor(Color.BLACK);
		g.fillRect(60, 400, 2, 40);
		g.fillRect(120, 400, 2, 40);
		
		//paints hands
		g.setColor(new Color(255, 222, 173));
		g.fillRect(40, 440, 20, 20);
		g.fillRect(120, 440, 20, 20);
		
		//paints neck
		g.fillRect(75, 360, 30, 25);
		
		//paints head
		g.fillRect(65, 325, 50, 45);
		
		//paints beard
		g.setColor(new Color(90, 37, 6));
		g.fillRect(65, 353, 50, 20);
		g.setColor(new Color(255, 222, 173));
		g.fillRect(75, 353, 30, 8);
		g.setColor(new Color(90, 37, 6));
		g.fillRect(65, 350, 50, 3);
		g.fillRect(65, 320, 4, 40);
		g.fillRect(111, 320, 4, 40);
		
		//paints hair
		g.fillRect(65, 320, 50, 10);
		
		//paints eyes
		g.setColor(Color.BLACK);
		g.fillRect(75, 337, 7, 7);
		g.fillRect(98, 337, 7, 7);
		
		//draws glasses
		g.drawRect(72, 333, 13, 13);
		g.drawRect(95, 333, 13, 13);
		g.fillRect(85, 340, 10, 1);
		
		//paints mouth
		g.fillRoundRect(80, 353, 20, 7, 200, 100);
			
	}
	
	public void paintTrash(Graphics g)
	{
		//paints can
		g.setColor(new Color(10, 68, 12));
		g.fillRect(165, 420, 82, 100);
		g.setColor(Color.BLACK);
		g.fillRect(165, 460, 82, 2);
		g.fillRect(165, 500, 82, 2);
		
		//paints bag
		g.fillRect(165, 420, 82, 20);
		
		//writes trash
		g.setFont(new Font("Arial", Font.BOLD, 16));
		g.drawString("TRASH", 178, 485);
	}
	
	public void paintBagelNosh(Graphics g)
	{
		//draws building
		g.setColor(new Color(252, 173, 108));
		g.fillRect(320, 240, 440, 280);
		
		//draws windows
		g.setColor(new Color(222, 236, 237));
		g.fillRect(345, 265, 200, 220);
		g.setColor(Color.RED);
		g.setFont(new Font("Verdana", Font.BOLD, 36));
		g.drawString("HOT", 395, 330);
		g.drawString("BAGELS", 365, 400);
		g.setFont(new Font("Verdana", Font.BOLD, 24));
		g.drawString("BULLETS: " + Gun.bullets, 365, 460);
		
		//draws door
		g.setColor(new Color(222, 236, 237));
		g.fillRect(600, 300, 120, 220);
		g.setColor(Color.BLACK);
		g.fillRect(690, 400, 12, 40);
		
		//draws top window
		g.setColor(new Color(222, 236, 237));
		g.fillRect(600, 265, 120, 25);
		
		//draws sign
		g.setColor(Color.BLACK);
		g.fillRect(360, 210, 20, 30);
		g.fillRect(700, 210, 20, 30);
		g.setColor(new Color(252, 173, 108));
		g.fillRect(340, 160, 400, 60);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Impact", Font.BOLD, 40));
		g.drawString("BAGEL", 430, 207);
		g.drawString("NOSH", 550, 207);
		
		
	}
	
	public void paintFloor(Graphics g)
	{
		g.setColor(Color.LIGHT_GRAY.darker());
		g.fillRect(0, 520, 800, 40);
	}
	
	public void paintThunder(Graphics g)
	{
		//draws legs
		g.setColor(new Color(234, 234, 234));
		g.fillRect(350, 140, 10, 20);
		g.fillRect(380, 140, 10, 20);
		g.fillRect(355, 130, 10, 10);
		g.fillRect(375, 130, 10, 10);
		g.setColor(Color.RED);
		g.fillRect(350, 156, 10, 4);
		g.fillRect(380, 156, 10, 4);
		
		//draws torso
		g.setColor(new Color(234, 234, 234));
		g.fillRect(360, 100, 20, 30);
		g.setColor(Color.RED);
		g.fillRect(360, 120, 20, 4);
		
		//draws arms and money
		g.setColor(new Color(234, 234, 234));
		g.fillRect(340, 105, 20, 7);
		g.fillRect(380, 105, 20, 7);
		g.setColor(Color.RED);
		g.fillRect(350, 105, 5, 7);
		g.fillRect(385, 105, 5, 7);
		g.setColor(new Color(133, 187, 101));
		g.fillRect(330, 97, 10, 20);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Impact", Font.BOLD, 11));
		g.drawString("$", 332, 111);
		g.setFont(new Font("Impact", Font.BOLD, 40));
		
		//draws neck
		g.setColor(new Color(234, 234, 234));
		g.fillRect(367, 95, 7, 6);
		
		//draws head
		g.fillRect(355, 80, 30, 15);
		g.setColor(Color.RED);
		g.fillRect(367, 80, 6, 6);
		g.setColor(Color.BLACK);
		g.fillRect(359, 84, 5, 5);
		g.fillRect(376, 84, 5, 5);
		g.fillRect(363, 91, 15, 1);
		
	}

}//end of class

