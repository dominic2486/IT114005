/***********************************************************************
 * *	Casino.java				Author: Dominic Quitoni
 *
 *	This is the core GUI, the main casino window(the first one to open)
 *	Is responsible for starting the game GUI's and for sending those
 *	games the BankAccount of the signed in player
 *  1900 line(excluding exception classes) recheck
 ***********************************************************************/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import WedgeHunt.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

class Casino extends JPanel implements ActionListener, KeyListener 
{
	private static final long serialVersionUID = 1L;
	private final boolean music=false;
	private String casinoName = "--=My Casino V10=--";
	JFrame mainFrame = new JFrame(this.casinoName);
	AdminGUI debug = new AdminGUI();
    JPanel topPanel = new JPanel();
    JPanel bottomPanel = new JPanel();
    JPanel rightPanel = new JPanel();
    JPanel leftPanel = new JPanel();
    JPanel centerPanel = new JPanel();
    Casino here = this;
    JTextField gameField = new JTextField(5);
    JTextField betField = new JTextField(5);
    JTextField balField = new JTextField(5);
    JTextField IDField = new JTextField(3);
    //JTextField PINField = new JTextField(3);
    JPasswordField PINSafe = new JPasswordField(3);
    JButton balRefreshButton = new JButton();
    JButton reReadPlayersButton = new JButton();
    JButton shutUpButton = new JButton();
    JButton gamblerButton = new JButton();
    //game buttons
    JButton blackjackButton = new JButton();
    JButton warButton = new JButton();
    JButton slotButton = new JButton();
    JButton HorseButton = new JButton();
    JButton wedgeHuntButton = new JButton();
    //JButton gameButton = new JButton();
    JButton setPlayerButton = new JButton();
    JButton signOutPlayerButton = new JButton();
    JButton saveButton = new JButton();
    JLabel labelGame = new JLabel();
    JLabel labelBet = new JLabel();
    JLabel labelPlayer = new JLabel();
    JLabel labelID = new JLabel();
    JLabel labelBal = new JLabel();
    ATM theATM;
    
    AudioInputStream audioIn;
    Clip clip;
    
    public Casino() 
    {
    	//configure panel settings
        this.topPanel.setBackground(new Color(0, 122, 0));
        this.bottomPanel.setBackground(new Color(0, 122, 0));
        this.topPanel.setLayout(new FlowLayout());
        this.rightPanel.setLayout(new BorderLayout());
        this.leftPanel.setLayout(new BorderLayout(10, 10));
        this.centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        
//configure buttons
        //game buttons
        this.blackjackButton.setText("Black Jack");
        this.blackjackButton.addActionListener(new RunBlackJack());
        this.blackjackButton.setEnabled(true);
        
        this.warButton.setText("War");
        this.warButton.addActionListener(new RunWar());
        this.warButton.setEnabled(true);
        
        this.slotButton.setText("Slot");
        this.slotButton.addActionListener(new RunSlot());
        this.slotButton.setEnabled(true);
        
        this.HorseButton.setText("Horse");
        this.HorseButton.addActionListener(new RunHorse());
        this.HorseButton.setEnabled(true);
        
        this.wedgeHuntButton.setText("Wedge Hunt");
        this.wedgeHuntButton.addActionListener(new RunWedgeHunt());
        this.wedgeHuntButton.setEnabled(true);
        //frame buttons
        this.setPlayerButton.setText("Enter ID");
        this.setPlayerButton.addActionListener(new SetPlayer());
        this.setPlayerButton.setEnabled(true);
        
        this.shutUpButton.setText("Toggle music");
        this.shutUpButton.addActionListener(this);
        this.shutUpButton.setEnabled(true);
        
        this.balRefreshButton.setText("Refresh Bal");
        this.balRefreshButton.addActionListener(new BalRefresh());
        this.balRefreshButton.setEnabled(false);
        
        this.reReadPlayersButton.setText("ReRead Players");
        this.reReadPlayersButton.addActionListener(new reReadPlayer());
        this.reReadPlayersButton.setEnabled(false);
        
        this.signOutPlayerButton.setText("Signout");
        this.signOutPlayerButton.addActionListener(new SignOut());
        this.signOutPlayerButton.setEnabled(false);
        
        this.saveButton.setText("Save Players");
        this.saveButton.addActionListener(new savePlayers());
        this.saveButton.setEnabled(true);
        
        this.gamblerButton.setText("Gambler");
        this.gamblerButton.setLayout(new FlowLayout());
        //this.gamblerButton.setBounds(20, 30, 100, 80);
        this.gamblerButton.addActionListener(new ActionListener()
        		{
					public void actionPerformed(ActionEvent e)
					{
						//System.out.println(JOptionPane.showConfirmDialog(null, "test"));
						JOptionPane.showMessageDialog(null,"If you or someone you know has a gambling problem please call 1-800-522-4700",casinoName+" PSA", JOptionPane.WARNING_MESSAGE, null);
					}
        		});
        this.gamblerButton.setEnabled(true);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(10,0,0,0);
        c.ipady = 40;
        c.ipadx = 40;
        centerPanel.add(gamblerButton, c);
        
        this.PINSafe.addKeyListener(this);
        this.labelPlayer.setText("Player ID:");
        this.labelID.setText("Enter ID and PIN");
        //adding to panels
        this.leftPanel.add(balRefreshButton, "Last");
        this.leftPanel.add(signOutPlayerButton, "First");
        this.rightPanel.add(labelPlayer, "First");
        this.rightPanel.add(labelBal, "Last");
        this.bottomPanel.add(saveButton);
        this.bottomPanel.add(reReadPlayersButton);
        this.bottomPanel.add(labelID);
        this.bottomPanel.add(IDField);
        this.bottomPanel.add(PINSafe);
        this.bottomPanel.add(setPlayerButton);
        this.bottomPanel.add(shutUpButton);
        this.topPanel.add(blackjackButton);
        this.topPanel.add(warButton);
        this.topPanel.add(slotButton);
        this.topPanel.add(HorseButton);
        this.topPanel.add(wedgeHuntButton);
        //this.centerPanel.add(gamblerButton,"Center");
        this.setLayout(new BorderLayout());
        this.add(topPanel, "North");
        this.add(bottomPanel, "South");
        this.add(rightPanel, "East");
        this.add(leftPanel, "West");
        this.add(centerPanel, "Center");
    }

    /**
	 * Generates and displays the JFrame
	 */
    @SuppressWarnings("static-access")
	public void display() 
    {
        //JFrame mainFrame = new JFrame(this.casinoName);
        mainFrame.setDefaultCloseOperation(3);
        mainFrame.setLayout(new FlowLayout());
        mainFrame.setContentPane(this);
        mainFrame.setPreferredSize(new Dimension(700, 550));
        mainFrame.pack();
        mainFrame.setVisible(true);
        
        Bank theBank = new Bank();
        //theBank.readPlayers("players.txt");
        if(!theBank.readPlayersB("players.txt"))
        	JOptionPane.showMessageDialog(null, "Error opening accounts file.");
        theATM = new ATM(theBank);
        debug.setBank(theBank,this);
        //debug.display();
        mainFrame.addWindowListener(new WindowAdapter(){

            public void windowClosing(WindowEvent evt) {
                Casino.this.exitForm(evt);
            }
        });
        
        try
        {
        	audioIn = AudioSystem.getAudioInputStream(getClass().getResource("sounds/casino-ambient.wav"));
        	clip = AudioSystem.getClip();
        	
        	clip.open(audioIn);
        	if(music)
        	{
        		clip.start();
            	//clip.LOOP_CONTINUOUSLY
            	clip.loop(clip.LOOP_CONTINUOUSLY);
        	}
        }
        catch(UnsupportedAudioFileException e)
        {
        	e.printStackTrace();
        } catch (LineUnavailableException e) 
        {
			e.printStackTrace();
		} catch (IOException e) 
        {
			e.printStackTrace();
		}
        JOptionPane.showMessageDialog(null, "If you have not been given a ID & PIN use 1 & 1234 \nThis Game does not condone gambling");
    }

//methods for KeyListener
    public void keyReleased(KeyEvent e) 
    {
    	//enter PIN
        if (e.getKeyCode() == 10) 
        {
            setPlayerButton.doClick();
        }
        
    }
    
    public void keyPressed(KeyEvent e) 
    {
    }

    public void keyTyped(KeyEvent e) 
    {
    }

    /**
	 * Method for buttons that call "this"
	 */
    public void actionPerformed(ActionEvent e) 
    {
        //Object source = e.getSource();.
    	if(clip.isActive())
    		clip.stop();
    	else
    		clip.start();
    }

    public void nothing() 
    {
    	//spacer
    }

    /**
	 * Updates all components for BankAccount
	 */
    public void updateBank() 
    {
        if (this.accountEntered()) 
        {
            String ID = this.IDField.getText();
            this.labelPlayer.setText("Player ID: " + ID);
            this.labelBal.setText("Balance = " + this.theATM.getBalance());
            //this.PINSafe.setText("****");
            this.signOutPlayerButton.setEnabled(true);
            this.balRefreshButton.setEnabled(true);
            this.reReadPlayersButton.setEnabled(true);
        } else {
            this.labelPlayer.setText("Player ID:");
            this.labelBal.setText("");
            this.IDField.setText("");
            this.PINSafe.setText("");
            this.signOutPlayerButton.setEnabled(false);
            this.balRefreshButton.setEnabled(false);
            this.reReadPlayersButton.setEnabled(false);
        }
        debug.updateBalance();
    }
    public void updateBankAdmin()
    {
    	if (this.accountEntered()) 
        {
            String ID = this.IDField.getText();
            this.labelPlayer.setText("Player ID: " + ID);
            this.labelBal.setText("Balance = " + this.theATM.getBalance());
            this.signOutPlayerButton.setEnabled(true);
            this.balRefreshButton.setEnabled(true);
            this.reReadPlayersButton.setEnabled(true);
        } else {
            this.labelPlayer.setText("Player ID:");
            this.labelBal.setText("");
            this.IDField.setText("");
            this.PINSafe.setText("");
            this.signOutPlayerButton.setEnabled(false);
            this.balRefreshButton.setEnabled(false);
            this.reReadPlayersButton.setEnabled(false);
        }
    }

    /*
	 * Tests if an account is entered
	 */
    public boolean accountEntered() 
    {
        boolean output = false;
        if (this.theATM.getState() == 4) 
        {
            output = true;
        }
        return output;
    }

    /**
	 * Saves all the players info to the file
	 */
    public void savePlayer() 
    {
        this.theATM.saveAll();
    }

    /**
     * Saves player data to the file when window is closed
     */
    public void exitForm(WindowEvent evt) 
    {
        this.savePlayer();
        JOptionPane.showMessageDialog(null, "Players have been saved");
    }

//classes
    class reReadPlayer implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            Casino.this.theATM.reset();
            Bank theBank = new Bank();
            //theBank.readPlayers("players.txt");
            if(!theBank.readPlayersB("players.txt"))
            	JOptionPane.showMessageDialog(null, "Error opening accounts file.");
            theATM = new ATM(theBank);
            debug.setBank(theBank);
            Casino.this.updateBank();
            
        }
    }

     class savePlayers implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            Casino.this.theATM.saveAll();
            JOptionPane.showMessageDialog(null, "All Players Saved");
        }
    }

    class SignOut implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            Casino.this.theATM.reset();
            Casino.this.updateBank();
        }
    }

    class BalRefresh implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            if (Casino.this.theATM.getState() == 1)
            {
                JOptionPane.showMessageDialog(null, "No player selected");
            } else {
                Casino.this.labelBal.setText("Balance = " + Casino.this.theATM.getBalance());
            }
        }
    }

    class RunWar implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
        	if(accountEntered())
        	{
        		WarGUI wframe = new WarGUI(here);
	            wframe.setBalance(Casino.this.theATM.getBA());
	            wframe.display();
        	}
        	else
        		JOptionPane.showMessageDialog(null, "Must enter a player account");
            
        }
    }

    class RunBlackJack implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
        	if(accountEntered())
        	{
        		BlackjackGUI bjframe = new BlackjackGUI(here);
                bjframe.setBalance(Casino.this.theATM.getBA());
                bjframe.display();
        	}
        	else
        		JOptionPane.showMessageDialog(null, "Must enter a player account");
        }
    }

    class RunSlot implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
        	
        	if(accountEntered())
        	{
        		SlotViewer sframe = new SlotViewer(here);
            	sframe.setBalance(theATM.getBA());
        		sframe.display();
        	}
        	else
        		JOptionPane.showMessageDialog(null, "Must enter a player account");
        }
    }
    
    class RunHorse implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
        	
        	if(accountEntered())
        	{
        		HorseViewer hframe = new HorseViewer(here);
            	hframe.setBalance(theATM.getBA());
        		hframe.display();
        	}
        	else
        		JOptionPane.showMessageDialog(null, "Must enter a player account");
        }
    }
    
    class SetPlayer implements ActionListener 
    {
        @SuppressWarnings("unused")
		public void actionPerformed(ActionEvent e)
        {
        	String ID = Casino.this.IDField.getText();
            String PIN = String.copyValueOf(PINSafe.getPassword());
            //System.out.println(PIN);
            int IDint = -1;
            int PINint = -1;
            
            if(PIN.equalsIgnoreCase("Java Life"))
            {
            	//Make Debug/admin frame that will let you manipulate the bank accounts
            	debug.display();
            	PINSafe.setText("");
            	return;
            }
            
            try {
                IDint = Integer.parseInt(ID);
                PINint = Integer.parseInt(PIN);
            }
            catch (Exception y) {
                JOptionPane.showMessageDialog(null, "Invalid Input");
                return;
            }
            Casino.this.theATM.setPlayerNumber(Integer.parseInt(ID));
            Casino.this.theATM.selectPlayer(Integer.parseInt(PIN));
            if (Casino.this.theATM.getState() == 1) {
            	JOptionPane.showMessageDialog(null, "No player with that ID found");
            }
            Casino.this.theATM.selectAccount(1);
            Casino.this.labelPlayer.setText("Player ID: " + ID);
            Casino.this.labelBal.setText("Balance = " + Casino.this.theATM.getBalance());
            Casino.this.updateBank();
            
        }
    }
    
    class RunWedgeHunt implements ActionListener
    {
		public void actionPerformed(ActionEvent e)
		{
			class WedgeHun implements Runnable
			{
				public void run() 
				{
					Test WH = new Test();
					try {
						WH.doIt();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				
			}
			Runnable r = new WedgeHun();
			Thread t = new Thread(r);
			t.start();
		}
    }
}