//************************************************************
//
//  BlackJack.Java		Authors: Lewis, Chase, and Coleman
//
//  The BlackJack class provides an implementation of a single
//  deck blackjack game.  It makes use of the Hand class to
//  represent a player's hand and the Deck class to represent
//  the deck of cards for the game.
//
//*************************************************************

import java.util.Iterator;
import jss2.exceptions.ElementNotFoundException;

public class Blackjack {
    Hand dealer;
    Hand player;
    Deck newdeck;

    public Blackjack(Hand dlr, Hand plr) 
    {
        this.dealer = dlr;
        this.player = plr;
        this.newdeck = new Deck();
    }

    public void dealInitialCards() 
    {
        this.dealer.newCard(newdeck);
        this.dealer.newCard(newdeck);
        this.player.newCard(newdeck);
        this.player.newCard(newdeck);
    }

    public Card hit(Hand whohit)
    {
        Card result = whohit.newCard(this.newdeck);
        return result;
    }

    public int handValue(Hand whohand) 
    {
        int result = whohand.getHandValue();
        return result;
    }

    public void discard(Hand whodis, Card discrd) throws ElementNotFoundException 
    {
        Card card = null;
        boolean found = false;
        Iterator<Card> scan = whodis.iterator();
        while (scan.hasNext() && !found) {
            card = scan.next();
            if (!discrd.equals(card)) continue;
            whodis.remove(card);
            found = true;
        }
        if (!found) {
            throw new ElementNotFoundException("BlackJack");
        }
    }

    public boolean blackj() 
    {
        boolean result = false;
        if (this.player.getHandValue() == 21) {
            result = true;
        }
        return result;
    }

    public boolean bust(Hand whobust) 
    {
        boolean result = false;
        if (whobust.getHandValue() > 21) {
            result = true;
        }
        return result;
    }

    public Hand dealerPlays()
    {
        Hand result = this.dealer;
        while (this.dealer.getHandValue() <= 16) {
            this.dealer.newCard(this.newdeck);
        }
        return result;
    }

    public String winner() 
    {
        String result = "";
        result = this.player.getHandValue() < this.dealer.getHandValue() && this.dealer.getHandValue() <= 21 ? "Lose" : (this.player.getHandValue() == this.dealer.getHandValue() && this.dealer.getHandValue() <= 21 ? "Push" : "Win");
        return result;
    }
}

