/*
 * Decompiled with CFR 0_123.
 */
public class BankAccount {
    private double balance;
    private double lastTransaction;

    public BankAccount() 
    {
        this.balance = 1000.0;
        this.lastTransaction = 0.0;
    }
    
    public BankAccount(double iniBal) 
    {
        this.balance = iniBal;
        this.lastTransaction = 0.0;
    }

    public void deposit(double amount) 
    {
        this.balance += amount;
        this.lastTransaction = amount;
    }

    public void withdraw(double amount) 
    {
        this.balance -= amount;
        this.lastTransaction = - amount;
    }

    public double getBalance() {
        return this.balance;
    }

    public double getLastTransaction() 
    {
        return this.lastTransaction;
    }

    public String toString() 
    {
        return "" + this.balance;
    }
}

