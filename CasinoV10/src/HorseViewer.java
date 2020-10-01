/*********************************************
 * SlotViewer.java							
 * 							   Dominic Quitoni
 *
 * Controls the graphical user interface 
 * for the slot machine.
 ********************************************/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

@SuppressWarnings("unused")
public class HorseViewer extends JPanel implements ActionListener, KeyListener
{
	private static final long serialVersionUID = 1L;
	
	HorseComponent comp;
	JButton parentRef;
	JPanel panel = new JPanel();
	
	JButton raceButton = new JButton();
	JButton betButton = new JButton();
	
	JPanel northPanel = new JPanel();
	JPanel southPanel = new JPanel();
	JPanel eastPanel = new JPanel();
	JPanel westPanel = new JPanel();
	
	JLabel betLabel = new JLabel();
	JLabel balLabel = new JLabel();
	JLabel horseNumFieldLabel = new JLabel();
	JLabel betFieldLabel = new JLabel();
	
	JTextField horseBetField = new JTextField(3);
	JTextField betField = new JTextField(3);
	JTextField outcomeField = new JTextField(4);
	
	Boolean betSet = false;
	BankAccount acc;
	
	AudioInputStream audioIn;
    Clip clip;
    Casino mainCas;
	
	final int FRAME_WIDTH=600;
	final int FRAME_HEIGHT=600;

	public HorseViewer(Casino ref)
	{
		mainCas = ref;
		this.setLayout(new BorderLayout());
		
	//Panel settings
		northPanel.setBackground(new Color(0,122,0));
		southPanel.setBackground(new Color(0,122,0));
		
		northPanel.setPreferredSize(new Dimension(100,100));
		southPanel.setPreferredSize(new Dimension(100,100));
		eastPanel.setPreferredSize(new Dimension(90,100));
		westPanel.setPreferredSize(new Dimension(90,100));
		
		northPanel.setLayout(new FlowLayout());
		southPanel.setLayout(new FlowLayout());
		eastPanel.setLayout(new BorderLayout());
		
	//Panel components
		//Buttons
		raceButton.setText("Race");
		raceButton.addActionListener(this);
		raceButton.setEnabled(false);
		
		betButton.setText(" Set Bet ");
		betButton.addKeyListener(this);
		betButton.addActionListener(new betButtonM());		
		betButton.setEnabled(true);
		
		//Labels
		balLabel.setText(" Balance: ");
		betLabel.setText(" Bet: ");
		horseNumFieldLabel.setText("Horse #");
		betFieldLabel.setText("Bet amount");
		
		betField.addKeyListener(this);
				
		//add components to panels
		eastPanel.add(raceButton,BorderLayout.CENTER);
		
		northPanel.add(balLabel);
		northPanel.add(betLabel);
		
		southPanel.add(horseNumFieldLabel);
		southPanel.add(horseBetField);
		southPanel.add(betFieldLabel);
		southPanel.add(betField);
		southPanel.add(betButton);
		
		//add panels to frame
		add(northPanel,BorderLayout.NORTH);
		add(southPanel,BorderLayout.SOUTH);
		add(eastPanel,BorderLayout.EAST);
		add(westPanel,BorderLayout.WEST);
		
		//Horse area components
		panel = new JPanel();
		comp = new HorseComponent();
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel.setPreferredSize(new Dimension(400,300));
		panel.setLayout(new BorderLayout());
		panel.add(comp,BorderLayout.CENTER);
		//addKeyListener(this);
		add(panel,BorderLayout.CENTER);
	}
	
	/**
	 * Called by Casino to create the JFrame and start the slot game GUI
	 */
	public void display()
	{
		JFrame frame = new JFrame();
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(2);
		frame.setLocation(250, 100);
		frame.setBackground(Color.YELLOW);
		frame.setContentPane(this);
		//frame.pack();
		frame.setVisible(true);
	}
	
	/**
	 * Method for buttons that call "this"
	 */
	public void actionPerformed(ActionEvent e)
	{		
		String horseBetFieldText = horseBetField.getText();
		String betFieldText = betLabel.getText().substring(6);
        if (horseBetFieldText.isEmpty() || betFieldText.isEmpty() || !betSet) {
            JOptionPane.showMessageDialog(null, "You must place a bet and pick a horse");
            return;
        }
        try {
            int horse = Integer.parseInt(horseBetFieldText);
            int betint = Integer.parseInt(betFieldText);
            if(horse>4)
            {
            	JOptionPane.showMessageDialog(null, "There is only 4 horses");
                return;
            }
        }
        catch (Exception y) {
            JOptionPane.showMessageDialog(null, "Invalid Input");
            return;
        }
        raceButton.setEnabled(false);
        try
        {
        	audioIn = AudioSystem.getAudioInputStream(getClass().getResource("sounds/starting.wav"));
        	clip = AudioSystem.getClip();
        	
        	clip.open(audioIn);
        	clip.start();
        }
        catch(UnsupportedAudioFileException et)
        {
        	et.printStackTrace();
        } catch (LineUnavailableException et) 
        {
			et.printStackTrace();
		} catch (IOException et) 
        {
			et.printStackTrace();
		}
        horseBetField.setEnabled(false);
        betField.setEnabled(false);
        acc.withdraw(Integer.parseInt(betFieldText));
        updateBank();
		
		comp.resetHorses();
		spinit();
	}
	
//methods for KeyListener	
	public void keyPressed(KeyEvent e) 
	{
		if(e.getKeyCode()==10)
		{
			betButton.doClick();
		}
		else if(e.getKeyCode()==82)
		{
			String fieldText=betField.getText();
			if(fieldText.substring(fieldText.length()-1).equalsIgnoreCase("r"))
				betField.setText(fieldText.substring(0, fieldText.length()-1));
			raceButton.doClick();
		}
	}

	public void keyReleased(KeyEvent e) 
	{
	}

	public void keyTyped(KeyEvent e)
	{
				
	}
	
	/**
	 * Required my casino to allow the game to hold a bank account
	 * 
	 * allows Slot to handle the bets 
	 */
	public void setBalance(BankAccount init)
	{
		acc = init;
		updateBank();
	}
		
	/**
	 * Updates the balance labels
	 */
	public void updateBank() 
    {
        balLabel.setText(" Balance: " + this.acc.getBalance());
        mainCas.updateBank();
    }
	
	/**
	 * Stops the reels from spinning and calculate the winnings
	 */
	public void stopSpin()
	{		
		String horseBetFieldText = horseBetField.getText();
		String betFieldText = betLabel.getText().substring(6);
		int theBet = Integer.parseInt(betFieldText);
		int horseNum = Integer.parseInt(horseBetFieldText);
		
		int win=-1;
		win=comp.startAnimation();
		clip.stop();
		if(win!=horseNum)
			JOptionPane.showMessageDialog(null, "YOU LOSE");
		else
		{
			JOptionPane.showMessageDialog(null, "WON with horse #"+win+"\n3x times your bet");
			System.out.println("WON with horse #"+win+"\n3x times your bet");
			
			outcomeField.setText(""+win);
			acc.deposit(3*theBet);
			updateBank();
		}
		
		if (acc.getBalance() - (double)theBet >= 0.0) 
        {
			raceButton.setEnabled(true);
            betSet = true;
        } else if (theBet > 0) 
        {
            JOptionPane.showMessageDialog(null, "You do not have enough to play that bet again");
            raceButton.setEnabled(false);
            betLabel.setText(" Bet: ");
            betSet = false;
        }
		betField.setEnabled(true);
		horseBetField.setEnabled(true);
		try 
		{
			Thread.sleep(100);
		} catch (InterruptedException eq) 
		{
			eq.printStackTrace();
		}
		
	}
	
	/**
	 * Starts spinning the reels
	 */
	public void spinit()
	{
		class AnimationRunnable implements Runnable
		{
			int counting=0;
			boolean test=true;
			public void run()
			{
				while(counting<1)
				{
					//System.out.println(num);
					comp.repaint();
					counting++;
					try 
					{
						Thread.sleep(400);
					} catch (InterruptedException e) 
					{
						e.printStackTrace();
					}
				}
				test=false;	
				stopSpin();
			}
			
			public boolean running()
			{
				return test;
			}
		}
		AnimationRunnable c = new AnimationRunnable();
		Runnable r = c;
		Thread tz= new Thread(r);
		tz.start();
	}
	
	class betButtonM implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
        	String horseBetFieldText = horseBetField.getText();
            String betFieldText = betField.getText();
            int bet = -1;
            int horseNum = -1;
            try
            {
                bet = Integer.parseInt(betFieldText);
                horseNum = Integer.parseInt(horseBetFieldText);
            }
            catch (Exception y) 
            {
                JOptionPane.showMessageDialog(null, "Invalid Input");
                betSet = false;
                return;
            }
            if(horseNum <= 0)
            {
            	JOptionPane.showMessageDialog(null, "You have to pick a horse to bet on");
                betSet = false;
            }
            if (bet <= 0) 
            {
                JOptionPane.showMessageDialog(null, "You can not do that");
                betSet = false;
            }
            else if (acc.getBalance() - (double)bet >= 0.0) 
            {
                betLabel.setText(" Bet: " + betFieldText);
                raceButton.setEnabled(true);
                betSet = true;
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Input");
                betSet = false;
            }
        }
    }

	

}
