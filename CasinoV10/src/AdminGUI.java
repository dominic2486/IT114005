import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

//import AdminGUI.test;

public class AdminGUI extends JPanel implements ActionListener, KeyListener
{
	private static final long serialVersionUID = 1L;

	JPanel topPanel = new JPanel();
    JPanel bottomPanel = new JPanel();
    JPanel rightPanel = new JPanel();
    JPanel leftPanel = new JPanel();
    JPanel centerPanel = new JPanel();
	
	JTextField IDLoginField = new JTextField();
	JTextField PINLoginField = new JTextField();
	JButton loginButton = new JButton();
	
	JLabel loggedBalanceLabel = new JLabel("NA");
	JTextField balAdjustField = new JTextField();
	JButton balAdjustButton = new JButton();
	
	JTextField IDCreateField = new JTextField();
	JTextField PINCreateField = new JTextField();
	JButton createButton = new JButton();
	
	JTextField IDDeleteField = new JTextField();
	JTextField PINDeleteField = new JTextField();
	JButton deleteButton = new JButton();
	
	JButton signOutButton = new JButton();
	JButton saveButton = new JButton();
	JButton reReadButton = new JButton();
	
	ATM theATM;
	Casino mainCasino;
	
	public AdminGUI() 
	{
		GridLayout testLayout = new GridLayout(0,3);
		testLayout.setHgap(5);
		testLayout.setVgap(10);
		
        centerPanel.setLayout(testLayout);
        
        PINLoginField.addKeyListener(this);
        loginButton.setText("Login");
        loginButton.addActionListener(new Login());
        loginButton.setEnabled(true);
		
        balAdjustField.addKeyListener(this);
        balAdjustButton.setText("Update Balance");
        balAdjustButton.addActionListener(new balAdjust());
        balAdjustButton.setEnabled(true);
        
        PINCreateField.addKeyListener(this);
        createButton.setText("Create");
        createButton.addActionListener(new CreatePlayer());
        createButton.setEnabled(true);
        
        PINDeleteField.addKeyListener(this);
        deleteButton.setText("Delete");
        deleteButton.addActionListener(new DeletePlayer());
        deleteButton.setEnabled(true);
        
        signOutButton.setText("Sign out");
        signOutButton.addActionListener(new SignOut());
        signOutButton.setEnabled(true);
        
        saveButton.setText("Save Players");
        saveButton.addActionListener(new ActionListener()
        		{
					public void actionPerformed(ActionEvent e) 
					{
						mainCasino.saveButton.doClick();
					}
        		});
        saveButton.setEnabled(true);
        
        reReadButton.setText("ReRead Players");
        reReadButton.addActionListener(new ActionListener()
        		{
					public void actionPerformed(ActionEvent e) 
					{
						signOutButton.doClick();
						mainCasino.reReadPlayersButton.doClick();
					}
        		});
        reReadButton.setEnabled(true);
        
        
        centerPanel.add(IDLoginField);
        centerPanel.add(PINLoginField);
        centerPanel.add(loginButton);
        centerPanel.add(loggedBalanceLabel);
        centerPanel.add(balAdjustField);
        centerPanel.add(balAdjustButton);
        centerPanel.add(IDCreateField);
        centerPanel.add(PINCreateField);
        centerPanel.add(createButton);
        centerPanel.add(IDDeleteField);
        centerPanel.add(PINDeleteField);
        centerPanel.add(deleteButton);
        centerPanel.add(signOutButton);
        centerPanel.add(saveButton);
        centerPanel.add(reReadButton);
        
        add(centerPanel, "Center");
	}

	public void display()
	{
		JFrame mainFrame = new JFrame("ADMINISTRATOR");
        mainFrame.setDefaultCloseOperation(2);
        mainFrame.setLayout(new FlowLayout());
        mainFrame.setContentPane(this);
        mainFrame.setPreferredSize(new Dimension(500, 250));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setLocation((dim.width/2)-250, (dim.height/2)-125);
        mainFrame.pack();
        mainFrame.setVisible(true);
	}
	
	public void setBank(Bank input,Casino ref)
	{
		theATM=new ATM(input);
		mainCasino=ref;
	}
	
	public void setBank(Bank input)
	{
		theATM=new ATM(input);
	}
	
	public void keyPressed(KeyEvent e) 
	{
		if(e.getKeyCode()==10)
		{
			if(PINLoginField.isFocusOwner())
				loginButton.doClick();
			if(balAdjustField.isFocusOwner())
				balAdjustButton.doClick();
			if(PINCreateField.isFocusOwner())
				createButton.doClick();
		}
	}
	
	public void keyReleased(KeyEvent e)
	{
		
	}

	public void keyTyped(KeyEvent e) 
	{
		
	}

	public void actionPerformed(ActionEvent e) 
	{
		
	}
	
	
	/**
	 * Tests if an account is entered
	 */
    public boolean accountEntered() 
    {
        boolean output = false;
        if (theATM.getState() == 4) 
        {
            output = true;
        }
        return output;
    }
	
    
    /**
	 * Updates all components for BankAccount
	 */
	public void updateBalance()
	{
		mainCasino.updateBankAdmin();
		if (accountEntered()) 
        {
            loggedBalanceLabel.setText("Balance = " + theATM.getBalance());
            loginButton.setEnabled(false);
            //PINLoginField.setText("****");
        } else {
            loggedBalanceLabel.setText("NA");
            IDLoginField.setText("");
            PINLoginField.setText("");
            balAdjustField.setText("");
            loginButton.setEnabled(true);
        }
	}
	
	
	class Login implements ActionListener 
    {
        public void actionPerformed(ActionEvent e)
        {
        	if(accountEntered())
        	{
        		int choice=JOptionPane.showConfirmDialog(null, "Account still logged in, would you like to sign out and continue");
        		if(choice==0)
        		{
        			signOutButton.doClick();
        		}
        		else
        			return;
        	}
        	String ID = IDLoginField.getText();
        	String PIN = PINLoginField.getText();
            int IDint = -1;
            int PINint = -1;
                        
            try {
                IDint = Integer.parseInt(ID);
                PINint = Integer.parseInt(PIN);
            }
            catch (Exception y) {
                JOptionPane.showMessageDialog(null, "Invalid Input");
                return;
            }
            theATM.setPlayerNumber(IDint);
            theATM.selectPlayer(PINint);
            if (theATM.getState() == 1) {
            	JOptionPane.showMessageDialog(null, "No player with that ID found");
            }
            theATM.selectAccount(1);
            loggedBalanceLabel.setText("Balance = " + theATM.getBalance());
        }
    }

	
	class balAdjust implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (theATM.getState() == 1)
            {
                JOptionPane.showMessageDialog(null, "No player selected");
            } else {
            	String adjust = balAdjustField.getText();
            	theATM.deposit(Integer.parseInt(adjust));
            	loggedBalanceLabel.setText("Balance = " + theATM.getBalance());
            	mainCasino.updateBank();
            }
		}
		
	}
	
	
	class CreatePlayer implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String ID = IDCreateField.getText();
        	String PIN = PINCreateField.getText();
            int IDint = -1;
            int PINint = -1;
            
            try {
                IDint = Integer.parseInt(ID);
                PINint = Integer.parseInt(PIN);
            }
            catch (Exception y) {
                JOptionPane.showMessageDialog(null, "Invalid Input");
                return;
            }
            Player temp = new Player(IDint,PINint,1000);
            if(JOptionPane.showConfirmDialog(null, "Are you sure you want to add this player")==0)
            	theATM.theBank.addPlayer(temp);
            
		}
		
	}
	
	class DeletePlayer implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String ID = IDDeleteField.getText();
        	String PIN = PINDeleteField.getText();
            int IDint = -1;
            int PINint = -1;
            
            try {
                IDint = Integer.parseInt(ID);
                PINint = Integer.parseInt(PIN);
            }
            catch (Exception y) {
                JOptionPane.showMessageDialog(null, "Invalid Input");
                return;
            }
            Player temp = theATM.theBank.findPlayer(IDint, PINint);
            if(temp==null)
            {
            	JOptionPane.showMessageDialog(null, "That player was not found");
            	return;
            }
            if(JOptionPane.showConfirmDialog(null, "Are you sure you want to remove this player")==0)
            {
            	if(mainCasino.theATM.playerNumber==IDint)
            		mainCasino.signOutPlayerButton.doClick();
            	theATM.theBank.removePlayer(temp);
            }
            
		}
		
	}

	class SignOut implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            theATM.reset();
            updateBalance();
            loginButton.setEnabled(true);
        }
    }
}
