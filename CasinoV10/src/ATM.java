/**
   An ATM that accesses a bank.
 */
public class ATM
{
	private int state;
	int playerNumber;
	private Player currentPlayer;
	private BankAccount currentAccount;
	Bank theBank;
 
	/**
     *	Constructs an ATM for a given bank.
     *	@param aBank the bank to which this ATM connects
	 */
	public ATM(Bank aBank)
	{
		theBank = aBank;
		reset();
	}
  
	/**
     *	Resets the ATM to the initial state.
	 */
	public void reset()
	{
	    playerNumber = -1;
	    currentAccount = null;
	    state = 1;
	}
  
	public static final int START = 1;
	public static final int PIN = 2;
	public static final int ACCOUNT = 3;
	public static final int TRANSACT = 4;
	public static final int CHECKING = 1;
	public static final int SAVINGS = 2;
	
	/**
     *	Sets the current customer number
     *	and sets state to PIN.
     *	(Precondition: state is START)
     *	@param number the customer number.
	 */
	public void setPlayerNumber(int number)
	{
	    assert (state == 1);
	    playerNumber = number;
	    state = 2;
	}
  
	/**
     *	Finds customer in bank.
     *	If found sets state to ACCOUNT, else to START.
     *	(Precondition: state is PIN)
     *	@param pin the PIN of the current customer
	 */
	public void selectPlayer(int pin)
	{
		assert (state == 2);
	    currentPlayer = theBank.findPlayer(playerNumber, pin);
	    
	    if (currentPlayer == null) {
	      state = 1;
	    } else {
	      state = 3;
	    }
	}
  
	/**
     *	Sets current account to checking or savings. Sets
     *	state to TRANSACT.
     *	(Precondition: state is ACCOUNT or TRANSACT)
     *	@param account one of CHECKING or SAVINGS
	 */
	public void selectAccount(int account)
	{
	    assert ((state == 3) || (state == 4));
	    if (account == 1) {
	      currentAccount = currentPlayer.getCheckingAccount();
	    } else
	      currentAccount = currentPlayer.getSavingsAccount();
	    state = 4;
	}
  
	/**
     *	Withdraws amount from current account.
     *	(Precondition: state is TRANSACT)
     *	@param value the amount to withdraw
	 */
	public void withdraw(double value)
	{
		assert (state == 4);
	    currentAccount.withdraw(value);
	}
  
	/**
     *	Deposits amount to current account.
     *	(Precondition: state is TRANSACT)
     *	@param value the amount to deposit
	 */
	public void deposit(double value)
	{
	    assert (state == 4);
	    currentAccount.deposit(value);
	}
  
	/**
     *	Gets the balance of the current account.
     *	(Precondition: state is TRANSACT)
     *	@return the balance
	 */
	public double getBalance()
	{
		assert (state == 4);
		return currentAccount.getBalance();
	}
  
	/**
     *	Moves back to the previous state.
	 */
	public void back()
	{
	    if (state == 4) {
	      state = 3;
	    } else if (state == 3) {
	      state = 2;
	    } else if (state == 2) {
	      state = 1;
	    }
	}
  
	/**
     *	Gets the current state of this ATM.
     *	@return the current state
	 */
	public int getState()
	{
		return state;
	}
  
	/**
	 * Helper method to call (theBank).savePlayers()
	 * calls Bank saves all players to "players.txt"
	 */
	public void saveAll()
	{
		theBank.savePlayers("players.txt");
	}
  
	/**
	 * @return a reference to the current active BankAccount
	 */
	public BankAccount getBA()
	{
		return currentAccount;
	}
}
