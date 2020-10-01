/*
 * Decompiled with CFR 0_123.
 */
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.jar.JarFile;
import java.util.*;

@SuppressWarnings("unused")
public class Bank 
{
    protected ArrayList<Player> players = new ArrayList<Player>();
    
    /**
     *	Reads the Player numbers and pins
     *	and initializes the bank accounts.
     *	@param filename the name of the Player file
     *	[when compiled] if file not found then it will use default file inside jar
     */
    public void readPlayers(String filename)
    {    	
    	//for inside jar file
    	
    	InputStream inStream = getClass().getResourceAsStream("/"+filename); 
    	Scanner in = new Scanner(new InputStreamReader(inStream));
        while (in.hasNext()) {
            int number = in.nextInt();
            int pin = in.nextInt();
            double bal = in.nextDouble();
            
			int active = in.nextInt();
            Player c = new Player(number, pin, bal);
            //System.out.println(pin);
            this.addPlayer(c);
        }
        in.close();
    }
    
    /**
     *	Reads the Player numbers and pins
     *	and initializes the bank accounts.
     *	@param filename the name of the Player file
     *	[When Compiled] if file not found outside jar 
     *	then it will use default file inside jar
     */
    public boolean readPlayersB(String filename)
    {
    	//for outside jar file
        //Scanner in = new Scanner(new FileReader(filename));
    	
    	//for inside jar file
    	//InputStream inStream = getClass().getResourceAsStream("/"+filename); 
    	//Scanner in = new Scanner(new InputStreamReader(inStream));
    	Scanner in;
    	Boolean test=true;
    	try
    	{
    		in = new Scanner(new FileReader(filename));
    		while (in.hasNext()) {
                int number = in.nextInt();
                int pin = in.nextInt();
                double bal = in.nextDouble();
                
    			int active = in.nextInt();
                Player c = new Player(number, pin, bal);
                //System.out.println(pin);
                this.addPlayer(c);
            }
            in.close();
            return true;
    	}catch(IOException e)
    	{
    		if(test)
    		{
    			test = false;
    			InputStream inStream = getClass().getResourceAsStream("/"+filename); 
    			in = new Scanner(new InputStreamReader(inStream));
    			while (in.hasNext()) {
                    int number = in.nextInt();
                    int pin = in.nextInt();
                    double bal = in.nextDouble();
                    
        			int active = in.nextInt();
                    Player c = new Player(number, pin, bal);
                    //System.out.println(pin);
                    this.addPlayer(c);
                }
            	return true;
    		}
    		return false;
    		            
    	}
    }

    /**
     *	Adds a Player to the bank.
     *	@param c the Player to add
     */
    public void addPlayer(Player c)
    {
        this.players.add(c);
        //System.out.println("added");
    }
    
    /**
     *	Rremoves a Player to the bank.
     *	@param c the Player to removed
     */
    public void removePlayer(Player c)
    {
    	players.remove(c);
    }

    /**
     *	Finds a Player in the bank.
     *	@param aNumber a Player number
     *	@param aPin a personal identification number
     *	@return the matching Player, or null if no Player
     *	matches
     */
    public Player findPlayer(int aNumber, int aPin) 
    {
        for (Player c : this.players) {
            if (!c.match(aNumber, aPin)) continue;
            return c;
        }
        return null;
    }

    /**
     * save bankaccounts to filename
     */
    public void savePlayers(String filename) 
    {
        try {
            //BufferedWriter output = new BufferedWriter(new FileWriter(new File(new File("CasinoV63.jar"), filename)));
        	BufferedWriter output = new BufferedWriter(new FileWriter(filename));
            for (int i = 0; i < this.players.size(); ++i) {
                output.append("" + this.players.get(i).getNumber() + " " + this.players.get(i).getPIN() + " " + this.players.get(i).getCheckingAccount().getBalance() + " 0");
                output.newLine();
            }
            output.close();
        }
        catch (IOException z) {
            z.printStackTrace();
        }
        
        /*Runtime rt = Runtime.getRuntime();
        try {
			Process pr = rt.exec("cmd /c java uf CasinoV7.jar players.txt");
			System.out.println("worked");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        /*try {
			JarFile test = new JarFile(getClass().getName());
			test.
			test.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        
    }
}