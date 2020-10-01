import java.awt.*;
import java.util.LinkedList;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class SlotComponent extends JComponent
{
	
	private static final long serialVersionUID = 1L;

	//int position;
	JFrame ref;
	SlotReel r1,r2,r3;
	String left,middle,right;
	LinkedList<String> namesList = new LinkedList<String>();
	int winning=0;
	final boolean rig=false;
	
	/**
	 * Main Constructor:
	 * 	That generates the refs to the SlotReels
	 *  
	 * Pre-Conditions:
	 * @param x is JFrame that the JComponent is being put into
	 * Post-Conditions:
	 * @param r1,r2,r3 are initialized
	 */
	public SlotComponent()
	{
		r1 = new SlotReel(0,this);
		r2 = new SlotReel(1,this);
		r3 = new SlotReel(2,this);
		namesList.clear();
		namesList.add("orange");
		namesList.add("lemon");
		namesList.add("plum");
		namesList.add("banana");
		namesList.add("cherry");
		namesList.add("watermelon");
		namesList.add("7");
		namesList.add("bar");
		namesList.add("spaceholder");
		namesList.add("bigwin");
	}
	
	/**
	 * Used by super class when repaint() is called
	 * redraws each reel
	 *  
	 */
	public void paintComponent(Graphics g)
	{
		//System.out.println("paint");
		g.setColor(Color.GRAY);
		r1.draw(g);
		r2.draw(g);
		r3.draw(g);
		
		left=r1.getMiddle();
		System.out.println(left);
		middle=r2.getMiddle();
		System.out.println(middle);
		right=r3.getMiddle();
		System.out.println(right);
		System.out.println("------");
		if(rig)
		{
			winning = namesList.indexOf(left)+1;
		}
		else if(left.equals(middle)&&middle.equals(right))
		{
			winning = namesList.indexOf(left)+1;
			System.out.println("Win inside "+winning);
		}
		else
			winning = 0;
	}

	/**
	 * Calls repaint
	 * 
	 * @return int corrisponding to the what the players bet should be multipled to
	 */
	public int startAnimation() 
	{
		this.repaint();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		System.out.println("Win outside "+winning);
		return winning;
	}
	
	/**
	 * Sets all 3 reels to brand new reels 
	 */
	public void resetReels()
	{
		r1 = new SlotReel(0,this);
		r2 = new SlotReel(1,this);
		r3 = new SlotReel(2,this);
	}
}
