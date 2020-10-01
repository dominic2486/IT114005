//************************************************************
// [Original]
//  BlackJackGUI.java       Authors: Lewis, Chase, Coleman
//
//  Provides a graphical user interface for a blackjack game
//  using the BlackJack class to provide the functionality
//  of the game
//http://faculty.washington.edu/moishe/javademos/blackjack/
//************************************************************

/*********************************************
 * [Edited]								  2017
 * 							   Dominic Quitoni
 *
 * Added betting feature, using BankAccount
 ********************************************/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BlackjackGUI extends JPanel implements KeyListener 
{
	private static final long serialVersionUID = 1L;
	JPanel topPanel = new JPanel();
    JPanel dcardPanel = new JPanel();
    JPanel pcardPanel = new JPanel();
    JPanel bankPanel = new JPanel();
    JTextField winlosebox = new JTextField(5);
    JTextField betField = new JTextField(3);
    JButton betButton = new JButton();
    JButton hitbutton = new JButton();
    JButton dealbutton = new JButton();
    JButton staybutton = new JButton();
    JButton playagainbutton = new JButton();
    JLabel balLabel = new JLabel();
    JLabel betLabel = new JLabel();
    JLabel dealerlabel = new JLabel();
    JLabel playerlabel = new JLabel();
    Hand dealer = new Hand();
    Hand player = new Hand();
    Blackjack game = new Blackjack(this.dealer, this.player);
    BankAccount acc;
    boolean EA = true;
    boolean betSet = false;
    JLabel playercard1;
    JLabel playercard2;
    JLabel playercardhit;
    JLabel dealercard0;
    JLabel dealercard2;
    JLabel dealercard1;
    JLabel dealercardhit;
    Casino mainCas;
    AudioInputStream audioIn;
    Clip clip;

    public BlackjackGUI(Casino ref) 
    {
    	mainCas=ref;
        this.topPanel.setBackground(new Color(0, 122, 0));
        this.dcardPanel.setBackground(new Color(0, 122, 0));
        this.pcardPanel.setBackground(new Color(0, 122, 0));
        this.bankPanel.setBackground(new Color(0, 122, 0));
        this.topPanel.setLayout(new FlowLayout());
        this.winlosebox.setText(" ");
        this.winlosebox.setFont(new Font("Helvetica Bold", 1, 20));
        this.winlosebox.setEnabled(false);
        this.dealbutton.setText("  Deal");
        this.dealbutton.addActionListener(new dealbutton());
        this.hitbutton.setText("  Hit");
        this.hitbutton.addActionListener(new hitbutton());
        this.hitbutton.setEnabled(false);
        this.staybutton.setText("  Stay");
        this.staybutton.addActionListener(new staybutton());
        this.staybutton.setEnabled(false);
        this.playagainbutton.setText("  Play Again");
        this.playagainbutton.addActionListener(new playagainbutton());
        this.playagainbutton.setEnabled(false);
        this.betField.setText("");
        this.betField.setFont(new Font("Helvetica Bold", 1, 20));
        this.betField.addKeyListener(this);
        this.betField.setEnabled(true);
        this.betButton.setText(" Set Bet");
        this.betButton.addActionListener(new betButton());
        this.betButton.setEnabled(true);
        this.balLabel.setText(" Balance: ");
        this.betLabel.setText(" Bet: ");
        this.dealerlabel.setText("  Dealer:  ");
        this.playerlabel.setText("  Player:  ");
        this.topPanel.add(this.winlosebox);
        this.topPanel.add(this.dealbutton);
        this.topPanel.add(this.hitbutton);
        this.topPanel.add(this.staybutton);
        this.topPanel.add(this.playagainbutton);
        this.pcardPanel.add(this.betField);
        this.pcardPanel.add(this.betButton);
        this.pcardPanel.add(this.playerlabel);
        this.dcardPanel.add(this.dealerlabel);
        this.topPanel.add(this.balLabel);
        this.topPanel.add(this.betLabel);
        this.setLayout(new BorderLayout());
        this.add((Component)this.topPanel, "North");
        this.add((Component)this.dcardPanel, "Center");
        this.add((Component)this.pcardPanel, "South");
        
        try
        {
        	//File file = new File("./src/Cards-Shuffling.wav");
        	audioIn = AudioSystem.getAudioInputStream(getClass().getResource("sounds/Cards-Shuffling.wav"));
        	clip = AudioSystem.getClip();
        	
        	clip.open(audioIn);
        }
        catch(UnsupportedAudioFileException ee)
        {
        	ee.printStackTrace();
        } catch (LineUnavailableException ee) 
        {
			ee.printStackTrace();
		} catch (IOException ee) 
        {
			ee.printStackTrace();
		}
    }

    public void display() 
    {
        JFrame myFrame = new JFrame("BlackJack");
        myFrame.setDefaultCloseOperation(2);
        myFrame.setContentPane(this);
        myFrame.setPreferredSize(new Dimension(700, 550));
        this.balLabel.setText(" Balance: " + this.acc.getBalance());
        myFrame.pack();
        myFrame.setVisible(true);
    }

    public void updateBank()
    {
        this.balLabel.setText(" Balance: " + this.acc.getBalance());
        mainCas.updateBank();
    }

    public void setBalance(BankAccount init)
    {
        this.acc = init;
    }

    @Override
    public void keyReleased(KeyEvent e) 
    {
        if (e.getKeyCode() == 10) {
        	betButton.doClick();
        }
    }    
    
    public void keyPressed(KeyEvent e) 
    {
    }

    public void keyTyped(KeyEvent e) 
    {
    }

    public void nothing() 
    {
    }

    class betButton implements ActionListener 
    {
    	public void actionPerformed(ActionEvent e) 
    	{
            String betFieldText = BlackjackGUI.this.betField.getText();
            int bet = -1;
            if (betFieldText.equalsIgnoreCase("Patches") && BlackjackGUI.this.EA) 
            {
                BlackjackGUI.this.acc.deposit(1000.0);
                BlackjackGUI.this.updateBank();
            } else {
                try 
                {
                    bet = Integer.parseInt(betFieldText);
                }
                catch (Exception y) 
                {
                    JOptionPane.showMessageDialog(null, "Invalid Input");
                    BlackjackGUI.this.betSet = false;
                    return;
                }
                if (bet <= 0) 
                {
                    JOptionPane.showMessageDialog(null, "You cant bet nothing");
                    BlackjackGUI.this.betSet = false;
                } else if (BlackjackGUI.this.acc.getBalance() - (double)bet >= 0.0) 
                {
                    BlackjackGUI.this.betLabel.setText(" Bet: " + betFieldText);
                    BlackjackGUI.this.dealbutton.setEnabled(true);
                    BlackjackGUI.this.betSet = true;
                } else if (bet > 0) 
                {
                    JOptionPane.showMessageDialog(null, "You do not have enough");
                    BlackjackGUI.this.betSet = false;
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Input");
                    BlackjackGUI.this.betSet = false;
                }
            }
        }
    }

    class playagainbutton implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) {
        	clip.stop();
        	clip.setFramePosition(0);
            String outcome = BlackjackGUI.this.winlosebox.getText();
            String betFieldText = betLabel.getText().substring(6);
            int theBet = Integer.parseInt(betFieldText);
            if (outcome.equals("Win")) 
            {
                BlackjackGUI.this.acc.deposit(theBet * 2);
            } else if (outcome.equals("Push"))
            {
                BlackjackGUI.this.acc.deposit(theBet);
            } else if (outcome.equals("BlackJack")) 
            {
                BlackjackGUI.this.acc.deposit(theBet * 2);
            }
            BlackjackGUI.this.updateBank();
            BlackjackGUI.this.dealerlabel.setText("Dealer: ");
            BlackjackGUI.this.playerlabel.setText("Player: ");
            BlackjackGUI.this.winlosebox.setText("");
            BlackjackGUI.this.dealer = new Hand();
            BlackjackGUI.this.player = new Hand();
            BlackjackGUI.this.game = new Blackjack(BlackjackGUI.this.dealer, BlackjackGUI.this.player);
            BlackjackGUI.this.dcardPanel.removeAll();
            BlackjackGUI.this.pcardPanel.removeAll();
            BlackjackGUI.this.pcardPanel.add(BlackjackGUI.this.betField);
            BlackjackGUI.this.pcardPanel.add(BlackjackGUI.this.betButton);
            BlackjackGUI.this.hitbutton.setEnabled(false);
            BlackjackGUI.this.staybutton.setEnabled(false);
            BlackjackGUI.this.playagainbutton.setEnabled(false);
            BlackjackGUI.this.dealbutton.setEnabled(true);
            BlackjackGUI.this.betField.setEnabled(true);
            BlackjackGUI.this.betButton.setEnabled(true);
            if (acc.getBalance() - (double)theBet >= 0.0) 
            {
                //dealbutton.setEnabled(true);
                betSet = true;
            } else if (theBet > 0) 
            {
                JOptionPane.showMessageDialog(null, "You do not have enough to play that bet again");
                //dealbutton.setEnabled(false);
                betLabel.setText(" Bet: ");
                betSet = false;
            }
        }
    }

    class staybutton implements ActionListener
    {
        public void actionPerformed(ActionEvent e) 
        {
            BlackjackGUI.this.dcardPanel.remove(BlackjackGUI.this.dealercard0);
            BlackjackGUI.this.dcardPanel.add(BlackjackGUI.this.dealercard1);
            BlackjackGUI.this.dealer = BlackjackGUI.this.game.dealerPlays();
            BlackjackGUI.this.dcardPanel.removeAll();
            BlackjackGUI.this.dcardPanel.add(BlackjackGUI.this.dealerlabel);
            BlackjackGUI.this.dealerlabel.setText(" " + BlackjackGUI.this.dealerlabel.getText());
            //Card dhitcard2 = null;
            for (Card dhitcard2 : dealer.inHand) 
            {
                BlackjackGUI.this.dealercardhit = new JLabel(dhitcard2.getimage());
                BlackjackGUI.this.dcardPanel.add(BlackjackGUI.this.dealercardhit);
            }
            BlackjackGUI.this.dealerlabel.setText("Dealer: " + BlackjackGUI.this.game.handValue(BlackjackGUI.this.dealer));
            BlackjackGUI.this.playerlabel.setText("Player: " + BlackjackGUI.this.game.handValue(BlackjackGUI.this.player));
            BlackjackGUI.this.winlosebox.setText(BlackjackGUI.this.game.winner());
            BlackjackGUI.this.hitbutton.setEnabled(false);
            BlackjackGUI.this.staybutton.setEnabled(false);
            BlackjackGUI.this.playagainbutton.setEnabled(true);
        }
    }

    class hitbutton implements ActionListener
    {
        public void actionPerformed(ActionEvent e) 
        {
            Card hitcard = BlackjackGUI.this.game.hit(BlackjackGUI.this.player);
            BlackjackGUI.this.playercardhit = new JLabel(hitcard.getimage());
            BlackjackGUI.this.pcardPanel.add(BlackjackGUI.this.playercardhit);
            BlackjackGUI.this.pcardPanel.repaint();
            if (BlackjackGUI.this.game.bust(BlackjackGUI.this.player)) 
            {
                BlackjackGUI.this.winlosebox.setText("Bust");
                BlackjackGUI.this.hitbutton.setEnabled(false);
                BlackjackGUI.this.dealbutton.setEnabled(false);
                BlackjackGUI.this.staybutton.setEnabled(false);
                BlackjackGUI.this.playagainbutton.setEnabled(true);
            }
            BlackjackGUI.this.playerlabel.setText("  Player:   " + BlackjackGUI.this.game.handValue(BlackjackGUI.this.player));
        }
    }

    class dealbutton implements ActionListener
    {
        public void actionPerformed(ActionEvent e) 
        {
            String betFieldText = betLabel.getText().substring(6);
            //betFieldText = betLabel.getText().substring(6);
            //BlackjackGUI.this.checkBalance(betFieldText);
            if (betFieldText.isEmpty() || !betSet) {
                JOptionPane.showMessageDialog(null, "You must place a bet");
                return;
            }
            clip.start();
            BlackjackGUI.this.betField.setEnabled(false);
            BlackjackGUI.this.acc.withdraw(Integer.parseInt(betFieldText));
            updateBank();
            BlackjackGUI.this.dcardPanel.add(BlackjackGUI.this.dealerlabel);
            BlackjackGUI.this.pcardPanel.add(BlackjackGUI.this.playerlabel);
            BlackjackGUI.this.dealercard0 = new JLabel(new ImageIcon(getClass().getResource("images/back.jpg")));
            BlackjackGUI.this.game.dealInitialCards();
            Card dcard = null;
            Iterator<Card> dscan = dealer.inHand.iterator();
            int count = 0;
            while (dscan.hasNext()) {
                dcard = dscan.next();
                if (count == 0) {
                    BlackjackGUI.this.dealercard1 = new JLabel(dcard.getimage());
                } else {
                    BlackjackGUI.this.dealercard2 = new JLabel(dcard.getimage());
                }
                ++count;
            }
            Iterator<Card> pscan = player.inHand.iterator();
            count = 0;
            playercard2 = new JLabel();
            while (pscan.hasNext()) 
            {
                Card pcard = pscan.next();
                if (count == 0) {
                    playercard1 = new JLabel(pcard.getimage());
                } else {
                    playercard2 = new JLabel(pcard.getimage());
                }
                ++count;
            }
            BlackjackGUI.this.dcardPanel.add(BlackjackGUI.this.dealercard0);
            BlackjackGUI.this.dcardPanel.add(BlackjackGUI.this.dealercard2);
            BlackjackGUI.this.pcardPanel.add(BlackjackGUI.this.playercard1);
            BlackjackGUI.this.pcardPanel.add(BlackjackGUI.this.playercard2);
            BlackjackGUI.this.dealerlabel.setText("  Dealer:  " + dcard.getvalue());
            BlackjackGUI.this.playerlabel.setText("  Player:  " + BlackjackGUI.this.game.handValue(BlackjackGUI.this.player));
            BlackjackGUI.this.hitbutton.setEnabled(true);
            BlackjackGUI.this.staybutton.setEnabled(true);
            BlackjackGUI.this.dealbutton.setEnabled(false);
            BlackjackGUI.this.betButton.setEnabled(false);
            if (BlackjackGUI.this.game.blackj()) {
                BlackjackGUI.this.hitbutton.setEnabled(false);
                BlackjackGUI.this.staybutton.setEnabled(false);
                BlackjackGUI.this.dealbutton.setEnabled(false);
                BlackjackGUI.this.playagainbutton.setEnabled(true);
                BlackjackGUI.this.betButton.setEnabled(true);
                BlackjackGUI.this.winlosebox.setText("BlackJack");
            }
            BlackjackGUI.this.add((Component)BlackjackGUI.this.dcardPanel, "Center");
            BlackjackGUI.this.add((Component)BlackjackGUI.this.pcardPanel, "South");
        }
    }

}

