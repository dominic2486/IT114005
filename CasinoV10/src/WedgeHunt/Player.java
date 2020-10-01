package WedgeHunt;
	
/**
 * Jack Brand
 * 2018
 */
public class Player implements Comparable
{
	int score;
	String name;
	
	//constructor
	public Player(int iScore, String iName)
	{
		score = iScore;
		name = iName;
	}

	@Override//prioritizes larger scores
	public int compareTo(Object arg0) 
	{

		if(arg0 instanceof Player)//checks that the other is actually a player obj
		{
			if(this.score > ((Player) arg0).getScore())
			{
				return -1;
			}
			else if(this.score < ((Player) arg0).getScore())
			{
				return 1;
			}
		}
		
		return 0;
	}
	
	public int getScore()
	{
		return score;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String toString()
	{
		return "" + score + " \t"  + name;
	}
}
