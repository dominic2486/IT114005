package WedgeHunt;

/**
 * Jack Brand
 * 2018
 */
import java.awt.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.image.*;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.ArrayList;
import java.util.*;

import javax.imageio.ImageIO;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.Timer;


@SuppressWarnings("unused")
public class Game extends JComponent implements Runnable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame jFrame;
	Background background;
	LinkedList<Car> enemies;

	
	int enemyNumber;
	int level;
	Gun gun;
	
	//set key to true to unlock final level
	boolean key = false;
	boolean gameOver;
	
	@SuppressWarnings("deprecation")
	public Game()
	{
		//does jFrame stuff
		jFrame = new JFrame();
		jFrame.setDefaultCloseOperation(3);
		jFrame.setVisible(true);
		jFrame.setMaximizedBounds(new Rectangle(800, 600));
		jFrame.setSize(800, 600);
		jFrame.setTitle("MAHWAH WEDGE HUNT");
		jFrame.setCursor(1);
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		jFrame.setBackground(new Color(135, 206, 250));
				
		gameOver = false;
				
		enemies = new LinkedList<Car>();
		
		
		//adds in bagels
		for(int x = 0; x < 9; x++)
		{
			if(x == 0)
			{
				
				Car car = new Car(170, 60, 5, "WHResources/bagel1.png");
				car.makeTitle();
				car.setVisible(true);
				enemies.add(car);
				panel.add(car);
				
			}
			else
			{
				Car car = new Car(125, 132, 5, "WHResources/bagel1.png");	
				car.setVisible(false);
				enemies.add(car);
				panel.add(car);
			}
			
		}
				
		
		//adds in cream cheese
		for(int x = 0; x < 9; x++)
		{
			if(x == 0)
			{
				Car car = new Car(170, 60, 7, "WHResources/creamcheese1.png");
				car.makeTitle();
				car.setVisible(false);
				enemies.add(car);
				panel.add(car);
					
			}
			else
			{
				Car car = new Car(125, 140, 7, "WHResources/creamcheese1.png");
				car.setVisible(false);
				enemies.add(car);
				panel.add(car);
			}
					
		}
		
		//adds in salmon
		for(int x = 0; x < 9; x++)
		{
			if(x == 0)
			{
				Car car = new Car(170, 60, 11, "WHResources/salmon1.png");
				car.makeTitle();
				car.setVisible(false);
				enemies.add(car);
				panel.add(car);
							
			}
			else
			{
				Car car = new Car(180, 77, 11, "WHResources/salmon1.png");
				car.setVisible(false);
				enemies.add(car);
				panel.add(car);
			}
		}					
		
		//adds in tomato
		for(int x = 0; x < 9; x++)
		{
			if(x == 0)
			{
				Car car = new Car(170, 60, 14, "WHResources/tomato1.png");
				car.makeTitle();
				car.setVisible(false);
				enemies.add(car);
				panel.add(car);
			}				
				
			else
			{
				Car car = new Car(120, 140, 14, "WHResources/tomato1.png");
				car.setVisible(false);
				enemies.add(car);
				panel.add(car);
			}
		}				
		
		//adds in onion
		for(int x = 0; x < 9; x++)
		{
			if(x == 0)
			{
				Car car = new Car(170, 60, 17, "WHResources/onion1.png");
				car.makeTitle();
				car.setVisible(false);
				enemies.add(car);
				panel.add(car);
			}				
						
			else
			{
				Car car = new Car(100, 120, 17, "WHResources/onion1.png");
				car.setVisible(false);
				enemies.add(car);
				panel.add(car);
			}
		}				
		
		//adds in jalapenos
		for(int x = 0; x < 9; x++)
		{
			if(x == 0)
			{
				Car car = new Car(170, 60, 20, "WHResources/pepper1.png");
				car.makeTitle();
				car.setVisible(false);
				enemies.add(car);
				panel.add(car);
			}			
								
			else
			{
				Car car = new Car(80, 80, 20, "WHResources/pepper1.png");
				car.setVisible(false);
				enemies.add(car);
				panel.add(car);
			}
		}				
		
		//adds in end card, skipped over if perfect game
		Car car = new Car(170, 60, 5, "WHResources/bagel1.png");
		car.makeEnd();
		car.setVisible(false);
		enemies.add(car);
		panel.add(car);
		
		//shown for testing
		key = false;
		
		//adds in secret gum level
		for(int x = 0; x < 9; x++)
		{
			if(x == 0)
			{
				Car car1 = new Car(170, 60, 25, "WHResources/gum1.png");
				car1.makeTitle();
				car1.setVisible(false);
				enemies.add(car1);
				panel.add(car1);
			}			
										
			else
			{
				Car car1 = new Car(80, 80, 25, "WHResources/gum1.png");
				car1.setVisible(false);
				enemies.add(car1);
				panel.add(car1);
			}
		}	
		
		
		//adds in end card, shown even after perfect game
		Car car2 = new Car(170, 60, 5, "WHResources/bagel1.png");
		car2.makeEnd();
		car2.setVisible(false);
		enemies.add(car2);
		panel.add(car2);

				
		//starts enemies at 0
		enemyNumber = 0;

	
		//adds panel to jFrame
		jFrame.add(panel);
		gun = new Gun(jFrame, enemies.getFirst());
		
		//sound = new Sound();
		
	}
	
	public JFrame getJFrame()
	{
		return jFrame;
	}
	
	public int getWidth()
	{
		return 800;
	}
	
	public int getHeight()
	{
		return 600;
	}

	public void run() 
	{
	
		
		while(true)
		{
			try
			{
				Thread.sleep(16);//makes game run smoothly
				enemies.get(enemyNumber).repaint();//paints target
		
				//swaps what enemy is visible
				if(enemies.get(enemyNumber).isOffScreen())
				{
					enemyNumber++;
					if(enemyNumber == 54 && (background.score >= 5400 || key))
					{
						enemyNumber++;
					}
					//checks if regular game or bonus game is done
					else if(enemyNumber == 54 || enemyNumber == 64)
					{
						gameOver = true; //use this to determine if the user should input name
						jFrame.getGraphics().setColor(new Color(135, 206, 250));
					}
					enemies.get(enemyNumber).setVisible(true);
					enemies.get(enemyNumber - 1).setVisible(false);
					//adjusts for secret level
					if(enemyNumber >= 2)
					{
						enemies.get(enemyNumber - 2).setVisible(false);
					}
					
					gun.changeTarget(enemies.get(enemyNumber));
				}
				
				else if(enemyNumber % 9 == 0 && enemyNumber != 0 && enemyNumber < 55)
				{
					background.enemiesLeft = 8;
					background.level = ((enemyNumber) / 9) + 1;
					gun.reload();
				}
				
				//sets up secret level
				else if(enemyNumber == 55)
				{
					background.enemiesLeft = 8;
					background.level = 7;
					gun.reload();
				}
				
			} 
			catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			
			
		}

	}	
	
	//tells the game when to put in a high score
	public boolean isOver()
	{
		return gameOver;
	}
		
}
