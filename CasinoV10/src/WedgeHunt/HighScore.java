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


public class HighScore extends JComponent implements Runnable
{
	private JFrame jFrame;

	int enemyNumber;
	int level;
	Car drawer; //this car draws the high scores
	
	
	@SuppressWarnings("deprecation")
	public HighScore()
	{
		//does jFrame stuff
		jFrame = new JFrame();
		jFrame.setSize(800, 600);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setVisible(true);
		jFrame.setMaximizedBounds(new Rectangle(800, 600));
		jFrame.setTitle("High Scores");
		jFrame.setCursor(1);
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		jFrame.setBackground(new Color(135, 206, 250));
		drawer = new Car(170, 60, 5, "bagel1.png");
		drawer.makeHighScore();
		panel.add(drawer);
		//adds panel to jFrame
		jFrame.add(panel);
		
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
		//simply draws the high scores over and over again
		while(true)
		{
			drawer.repaint();	
		}

	}	
		
}
