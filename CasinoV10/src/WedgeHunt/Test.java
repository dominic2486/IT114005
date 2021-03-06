package WedgeHunt;

/**
 * Jack Brand
 * 2018
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;


public class Test 
{
	static ArrayList<Player> aL; //how high scores are stored
	@SuppressWarnings("deprecation")
	public void doIt() throws IOException
	{
		//makes and starts a new game
		Game game = new Game();
		Thread t1 = new Thread(game);
		t1.start();
		
		while(true)
		{
			//for some reason it will not work without this print
			System.out.print("");
			if(game.isOver())
			{
				//reads in file, writes to it
				File inputFile = new File("input.txt");
				Scanner reader = new Scanner(inputFile);
				Scanner in = new Scanner(System.in);
				
				PriorityQueue<Player> q = new PriorityQueue<Player>();
				
				while(reader.hasNext()) //adds previous high scores to a priority queue
				{
					int score = reader.nextInt();
					String name = reader.next();
					Player player = new Player(score, name);
					q.add(player);
				}		
				
				
				OutputStreamWriter outWriter = new OutputStreamWriter(new FileOutputStream("input.txt"), "UTF-8");

				BufferedWriter writer = new BufferedWriter(outWriter);
				
				aL = new ArrayList<Player>(); //how the scores are printed
				
				int iScore =  Background.score;
				System.out.println("\nPLEASE ENTER FIRST NAME: "); //user input through console
				String iName =  in.next();
				q.add(new Player(iScore, iName));//adds this game's score to priority queue
				
				//puts stuff in array list
				while(!q.isEmpty() && aL.size() < 5)
				{
					Player p = q.remove();
					aL.add(p);
					writer.write("" + p.getScore() + " " + p.getName() + " ");
					
				}
				
			
				t1.stop(); //stops game
				game.getJFrame().dispose(); //closes old window
				writer.close();
				reader.close();
				in.close();
				break;
			}
		}
		
		//makes the high score window
		HighScore high = new HighScore();
		Thread t2 = new Thread(high);
		t2.start();
		
		
	}

}

