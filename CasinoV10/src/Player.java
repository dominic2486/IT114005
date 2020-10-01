/*
 * Decompiled with CFR 0_123.
 */
public class Player 
{
    private int playerNumber;
    private int pin;
    private BankAccount checkingAccount;
    private BankAccount savingsAccount;

    public Player(int aID, int aPin, double aBal) 
    {
        this.playerNumber = aID;
        this.pin = aPin;
        this.checkingAccount = new BankAccount(aBal);
        this.savingsAccount = new BankAccount();
    }

    public boolean match(int aNumber, int aPin) 
    {
        return this.playerNumber == aNumber && this.pin == aPin;
    }

    public BankAccount getCheckingAccount() 
    {
        return this.checkingAccount;
    }

    public BankAccount getSavingsAccount() 
    {
        return this.savingsAccount;
    }

    public int getNumber() 
    {
        return this.playerNumber;
    }

    public int getPIN() 
    {
        return this.pin;
    }
}

