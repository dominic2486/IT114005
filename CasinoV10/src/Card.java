/*
 * Decompiled with CFR 0_123.
 */
import javax.swing.ImageIcon;

public class Card {
    protected String face;
    protected ImageIcon cardpic;
    protected int value;
    protected String suit;

    public Card() 
    {
        this.cardpic = null;
        this.value = 0;
        this.suit = null;
        this.face = null;
    }

    public Card(ImageIcon x, int val, String s, String f)
    {
        this.cardpic = x;
        this.value = val;
        this.face = f;
        this.suit = s;
    }

    public ImageIcon getimage() 
    {
        return this.cardpic;
    }

    public int getvalue() 
    {
        return this.value;
    }

    public void setvalue(int v) 
    {
        this.value = v;
    }

    public String getsuit() 
    {
        return this.suit;
    }

    public String getface() 
    {
        return this.face;
    }

    public String toString() 
    {
        return "Face: " + this.face + " Suit" + this.suit + " Value: " + this.value;
    }
}

