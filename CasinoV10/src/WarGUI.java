/************************************************************
 *
 *  WarGUI.java       Authors: Dominic and Shane
 *
 *  Provides a graphical user interface for a war game
 *  using the War class to provide the functionality
 *  of the game
 *
 *  Modeled off of BlackjackGUI by Authors: Lewis, Chase, Coleman
 *
 ************************************************************/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.PrintStream;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
@SuppressWarnings("unused")
public class WarGUI extends JPanel implements KeyListener 
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
    JButton playagainbutton = new JButton();
    JLabel balLabel = new JLabel();
    JLabel betLabel = new JLabel();
    JLabel dealerlabel = new JLabel();
    JLabel playerlabel = new JLabel();
    Hand dealer = new Hand();
    Hand player = new Hand();
    War game = new War(this.dealer, this.player);
    BankAccount acc;
    boolean betSet = false;
    JLabel playercard1;
    JLabel playercard0;
    JLabel playercardhit;
    JLabel dealercard0;
    JLabel dealercard1;
    JLabel dealercardhit;
    Casino mainCas;
    
    public WarGUI(Casino ref)
    {
    	mainCas = ref;
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
    }

    public void display() 
    {
        JFrame myFrame = new JFrame("War");
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

    public void updateBoxRound() 
    {
        this.winlosebox.setText(this.game.winner());
    }

    @Override
    public void keyReleased(KeyEvent e) 
    {
        if (e.getKeyCode() == 10) 
        {
            String betFieldText = this.betField.getText();
            int bet = -1;
            try 
            {
                bet = Integer.parseInt(betFieldText);
            }
            catch (Exception y) 
            {
                JOptionPane.showMessageDialog(null, "Invalid Input");
                this.betSet = false;
                return;
            }
            if (this.acc.getBalance() - (double)bet >= 0.0) 
            {
                this.betLabel.setText(" Bet: " + betFieldText);
                this.dealbutton.setEnabled(true);
                this.betSet = true;
            } else if (bet > 0) 
            {
                JOptionPane.showMessageDialog(null, "You do not have enough");
                this.betSet = false;
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Input");
                this.betSet = false;
            }
        }
    }

    public void keyPressed(KeyEvent e) 
    {
    }

    public void keyTyped(KeyEvent e) 
    {
    }

    public void setBalance(BankAccount init) 
    {
        this.acc = init;
    }

    public void nothing() 
    {
    }

    class betButton implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            String betFieldText = WarGUI.this.betField.getText();
            int bet = -1;
            try
            {
                bet = Integer.parseInt(betFieldText);
            }
            catch (Exception y) 
            {
                JOptionPane.showMessageDialog(null, "Invalid Input");
                WarGUI.this.betSet = false;
                return;
            }
            if (WarGUI.this.acc.getBalance() - (double)bet >= 0.0) 
            {
                WarGUI.this.betLabel.setText(" Bet: " + betFieldText);
                WarGUI.this.dealbutton.setEnabled(true);
                WarGUI.this.betSet = true;
            } else if (bet > 0) 
            {
                JOptionPane.showMessageDialog(null, "You do not have enough");
                WarGUI.this.betSet = false;
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Input");
                WarGUI.this.betSet = false;
            }
        }
    }

    class playagainbutton implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            String outcome = WarGUI.this.winlosebox.getText();
            String betFieldText = WarGUI.this.betField.getText();
            int theBet = Integer.parseInt(betFieldText);
            if (outcome.equals("Win")) 
            {
                WarGUI.this.acc.deposit(theBet * 2);
            }
            WarGUI.this.updateBank();
            WarGUI.this.dealerlabel.setText("Dealer: ");
            WarGUI.this.playerlabel.setText("Player: ");
            WarGUI.this.winlosebox.setText("");
            WarGUI.this.dealer = new Hand();
            WarGUI.this.player = new Hand();
            WarGUI.this.game = new War(WarGUI.this.dealer, WarGUI.this.player);
            WarGUI.this.dcardPanel.removeAll();
            WarGUI.this.pcardPanel.removeAll();
            WarGUI.this.pcardPanel.add(WarGUI.this.betField);
            WarGUI.this.pcardPanel.add(WarGUI.this.betButton);
            WarGUI.this.hitbutton.setEnabled(false);
            WarGUI.this.playagainbutton.setEnabled(false);
            WarGUI.this.dealbutton.setEnabled(true);
            WarGUI.this.betField.setEnabled(true);
            WarGUI.this.betButton.setEnabled(true);
        }
    }

    class hitbutton implements ActionListener 
    {
        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            System.out.println("Player deck: " + WarGUI.this.game.playerDeck.inHand.size());
            System.out.println("Dealer deck: " + WarGUI.this.game.dealerDeck.inHand.size());
            if (WarGUI.this.game.winner().equals("Tie")) 
            {
                if (WarGUI.this.game.outOfCards() == 0) 
                {
                    WarGUI.this.game.hitW();
                    WarGUI.this.playercardhit = new JLabel(WarGUI.this.player.inHand.get(WarGUI.this.player.inHand.size() - 1).getimage());
                    WarGUI.this.dealercardhit = new JLabel(WarGUI.this.dealer.inHand.get(WarGUI.this.dealer.inHand.size() - 1).getimage());
                    WarGUI.this.pcardPanel.add(WarGUI.this.playercardhit);
                    WarGUI.this.dcardPanel.add(WarGUI.this.dealercardhit);
                    WarGUI.this.pcardPanel.repaint();
                    WarGUI.this.dcardPanel.repaint();
                    WarGUI.this.dealerlabel.setText("  Dealer:  " + WarGUI.this.game.handValue(WarGUI.this.dealer));
                    WarGUI.this.playerlabel.setText("  Player:  " + WarGUI.this.game.handValue(WarGUI.this.player));
                    WarGUI.this.updateBoxRound();
                    return;
                }
                JOptionPane.showMessageDialog(null, "You " + WarGUI.this.game.winner() + "");
                WarGUI.this.hitbutton.setEnabled(false);
                WarGUI.this.dealbutton.setEnabled(false);
                WarGUI.this.playagainbutton.setEnabled(true);
                return;
            }
            if (WarGUI.this.game.winner().equals("Win")) 
            {
                WarGUI.this.game.roundWinner(WarGUI.this.player, WarGUI.this.dealer, 1);
            } else if (WarGUI.this.game.winner().equals("Lose")) 
            {
                WarGUI.this.game.roundWinner(WarGUI.this.dealer, WarGUI.this.player, 0);
            }
            if (WarGUI.this.game.outOfCards() == 0) 
            {
                WarGUI.this.game.hitW();
                WarGUI.this.dcardPanel.removeAll();
                WarGUI.this.pcardPanel.removeAll();
                WarGUI.this.dcardPanel.add(WarGUI.this.dealerlabel);
                WarGUI.this.pcardPanel.add(WarGUI.this.betField);
                WarGUI.this.pcardPanel.add(WarGUI.this.betButton);
                WarGUI.this.pcardPanel.add(WarGUI.this.playerlabel);
                Card pcard = WarGUI.this.player.inHand.get(0);
                WarGUI.this.playercard1 = new JLabel(pcard.getimage());
                Card dcard = WarGUI.this.dealer.inHand.get(0);
                WarGUI.this.dealercard1 = new JLabel(dcard.getimage());
                WarGUI.this.dcardPanel.add(WarGUI.this.dealercard0);
                WarGUI.this.dcardPanel.add(WarGUI.this.dealercard1);
                WarGUI.this.pcardPanel.add(WarGUI.this.playercard0);
                WarGUI.this.pcardPanel.add(WarGUI.this.playercard1);
                WarGUI.this.winlosebox.setText(WarGUI.this.game.winner());
                WarGUI.this.dealerlabel.setText("Dealer: " + WarGUI.this.game.handValue(WarGUI.this.dealer));
                WarGUI.this.playerlabel.setText("  Player:   " + WarGUI.this.game.handValue(WarGUI.this.player));
                WarGUI.this.pcardPanel.repaint();
                WarGUI.this.dcardPanel.repaint();
                return;
            }
            JOptionPane.showMessageDialog(null, "You " + WarGUI.this.game.winner() + "");
            WarGUI.this.hitbutton.setEnabled(false);
            WarGUI.this.dealbutton.setEnabled(false);
            WarGUI.this.playagainbutton.setEnabled(true);
        }
    }

    class dealbutton implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            String betFieldText = WarGUI.this.betField.getText();
            if (betFieldText.isEmpty() || !WarGUI.this.betSet) 
            {
                JOptionPane.showMessageDialog(null, "You must place a bet");
                return;
            }
            WarGUI.this.betField.setEnabled(false);
            WarGUI.this.acc.withdraw(Integer.parseInt(betFieldText));
            updateBank();
            WarGUI.this.dcardPanel.add(WarGUI.this.dealerlabel);
            WarGUI.this.pcardPanel.add(WarGUI.this.playerlabel);
            WarGUI.this.dealercard0 = new JLabel(new ImageIcon("back.jpg"));
            WarGUI.this.playercard0 = new JLabel(new ImageIcon("back.jpg"));
            WarGUI.this.game.dealInitialCards();
            WarGUI.this.game.hitW();
            Card pcard = WarGUI.this.player.inHand.get(0);
            WarGUI.this.playercard1 = new JLabel(pcard.getimage());
            Card dcard = WarGUI.this.dealer.inHand.get(0);
            WarGUI.this.dealercard1 = new JLabel(dcard.getimage());
            WarGUI.this.dcardPanel.add(WarGUI.this.dealercard0);
            WarGUI.this.dcardPanel.add(WarGUI.this.dealercard1);
            WarGUI.this.pcardPanel.add(WarGUI.this.playercard0);
            WarGUI.this.pcardPanel.add(WarGUI.this.playercard1);
            WarGUI.this.dealerlabel.setText("  Dealer:  " + WarGUI.this.game.handValue(WarGUI.this.dealer));
            WarGUI.this.playerlabel.setText("  Player:  " + WarGUI.this.game.handValue(WarGUI.this.player));
            WarGUI.this.hitbutton.setEnabled(true);
            WarGUI.this.dealbutton.setEnabled(false);
            WarGUI.this.betButton.setEnabled(false);
            WarGUI.this.updateBoxRound();
            WarGUI.this.add((Component)WarGUI.this.dcardPanel, "Center");
            WarGUI.this.add((Component)WarGUI.this.pcardPanel, "South");
        }
    }

}

