/*
 * Decompiled with CFR 0_123.
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import jss2.exceptions.ElementNotFoundException;
import jss2.exceptions.EmptySetException;
@SuppressWarnings("unused")
public class Hand 
{
    protected ArrayList<Card> inHand = new ArrayList<Card>();
    protected int handvalue = 0;
    protected int count = 0;

    private void reduceHand(Card newCard) 
    {
        if (this.handvalue > 21 && this.aceInHand()) {
            this.handvalue -= 10;
        }
    }

    private boolean aceInHand() 
    {
        boolean result = false;
        Object cardchk = null;
        for (int i = 0; i < this.inHand.size() && !result; ++i) {
            if (this.inHand.get(i).getvalue() != 11) continue;
            this.inHand.get(i).setvalue(1);
            result = true;
        }
        return result;
    }

    public Card newCard(Deck currentdeck) 
    {
        Card result = currentdeck.getCard();
        this.inHand.add(result);
        this.handvalue += result.getvalue();
        this.reduceHand(result);
        ++this.count;
        return result;
    }

    public Card newCardW(Hand personDeck) 
    {
        Card result = personDeck.getNextCard();
        this.inHand.add(result);
        this.handvalue = result.getvalue();
        this.reduceHand(result);
        ++this.count;
        return result;
    }

    public int getHandValue() 
    {
        return this.handvalue;
    }

    public Iterator<Card> iterator() 
    {
        return this.inHand.iterator();
    }

    public Card remove(Card crd) throws ElementNotFoundException 
    {
        Card output = null;
        int index = this.inHand.indexOf(crd);
        if (this.inHand.isEmpty()) {
            throw new EmptySetException();
        }
        if (index != -1) {
            output = this.inHand.remove(index);
        } else if (index == -1) {
            throw new NoSuchElementException();
        }
        return output;
    }

    public Card getNextCard() 
    {
        Card output = this.inHand.remove(0);
        return output;
    }

    public void addCard(Card crd) 
    {
        this.inHand.add(crd);
    }

    public String toString() 
    {
        String result = "";
        Card cardstr = null;
        for (int i = 0; i < this.inHand.size(); ++i) {
            cardstr = this.inHand.get(i);
            result = result + "card" + i + ": " + cardstr.getvalue() + "\n";
        }
        return result;
    }
}

