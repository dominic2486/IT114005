package WedgeHunt;
	
/**
 * Jack Brand
 * 2018
 */
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Gun extends Component implements MouseMotionListener{

	static int bullets;
	int mouseX;
	int mouseY;
	Car car;
	Sound sound;
	
	public Gun(JFrame jframe, Car iCar)
	{
		//sets instance variables
		bullets = 12;
		mouseX = 9999;
		mouseY = 9999;
		car = iCar;
		sound = new Sound();
		
		//click event
		jframe.addMouseListener(new MouseAdapter() 
		{
			public void mousePressed(MouseEvent e)
			{
				//need enough ammo
				if(bullets > 0)
				{					
					//reduces bullets
					bullets--;
					
					//sets mouse position
					mouseX = e.getX();
					mouseY = e.getY();	
					

					//makes loud noise
					//easter egg
					if(mouseX > 65 && mouseX < 125 && mouseY > 325 && mouseY < 410)
					{
						sound.playOw();
					}
					else
					{
						sound.playShoot();
					}
					
					//flashes screen
					jframe.getGraphics().fillRect(0, 0, 800, 600);
					
					//the sleep gives time for rect to flash
					try 
					{
						Thread.sleep(30);
					}
					catch (InterruptedException exception)
					{
						// TODO Auto-generated catch block
						exception.printStackTrace();
					}
					
					//clears the screen
					jframe.getGraphics().clearRect(0, 0, 800, 600);
					
					//this sleep gives time for click to register yet be reset so you can't click the future path
					try 
					{
						Thread.sleep(30);
					} 
					catch (InterruptedException exception) 
					{
						// TODO Auto-generated catch block
						exception.printStackTrace();
					}
					
					//checks for hit
					if(car.contains(mouseX, mouseY) && !car.isEnd())
					{
						//target can only die once
						if(!car.isDead())
						{
							car.die();
							sound.playDie();

						}
					}
					
					
					//makes sure mouseX and mouseY don't interfere with path
					mouseX = 9999;
					mouseY = 9999;
				}
				else
				{
					sound.playError();
				}
			}
		});
	}	
	
	//pass in a new target every time a new enemy comes in
	public void changeTarget(Car iCar)
	{
		car = iCar;
	}
	
	public Car getCar()
	{
		return car;
	}
	
	//resets bullet count after each level
	public void reload()
	{
		bullets = 12;
	}
	
	//getter for bullets
	public int getBullets()
	{
		return bullets;
	}
	
	//necessary implemented methods
	@Override
	public void mouseDragged(MouseEvent arg0) 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseMoved(MouseEvent arg0) 
	{
		// TODO Auto-generated method stub
	}
	
	
}
