//************************************************************
//
//  War.Java		Authors: Dominic and Shane
//
//  The BlackJack class provides an implementation of a single
//  deck blackjack game.  It makes use of the Hand class to
//  represent a player's hand and the Deck class to represent
//  the deck of cards for the game.

//	based off BlackJack.java by Authors: Lewis, Chase, and Coleman
//
//*************************************************************
/*
 * Decompiled with CFR 0_123.
 */
import java.util.ArrayList;
import java.util.Iterator;
import jss2.exceptions.ElementNotFoundException;
@SuppressWarnings("unused")
public class War {
    protected Hand dealerDeck;
    protected Hand playerDeck;
    Hand dealer;
    Hand player;
    Deck newdeck;

    public War(Hand dlr, Hand plr) 
    {
        this.dealer = dlr;
        this.player = plr;
        this.newdeck = new Deck();
        this.dealerDeck = new Hand();
        this.playerDeck = new Hand();
    }

    public void dealInitialCards() 
    {
        for (int count = 0; count < 26; ++count) {
            this.dealerDeck.newCard(this.newdeck);
            this.playerDeck.newCard(this.newdeck);
        }
    }

    public void hitW() 
    {
        this.dealer.newCardW(this.dealerDeck);
        this.player.newCardW(this.playerDeck);
    }

    public int handValue(Hand whoHand) 
    {
        int result = whoHand.getHandValue();
        if (result == 1) {
            result += 10;
        }
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
        result = this.handValue(this.player) < this.handValue(this.dealer) ? "Lose" : (this.handValue(this.player) == this.handValue(this.dealer) ? "Tie" : "Win");
        return result;
    }

    public void roundWinner(Hand win, Hand lose, int who) 
    {
        Hand temp = new Hand();
        while (win.inHand.size() > 0) 
        {
            temp.addCard(win.inHand.remove(0));
        }
        while (lose.inHand.size() > 0) 
        {
            temp.addCard(lose.inHand.remove(0));
        }
        if (who == 1) 
        {
            while (temp.inHand.size() > 0 && !temp.inHand.isEmpty()) 
            {
                this.playerDeck.addCard(temp.inHand.remove(0));
            }
        } else if (who == 0) {
            while (temp.inHand.size() > 0 && !temp.inHand.isEmpty()) 
            {
                this.dealerDeck.addCard(temp.inHand.remove(0));
            }
        } else if (who == 1 || who != 0) {
            // empty if block
        }
    }

    public int outOfCards() 
    {
        int out = 0;
        if (this.playerDeck.inHand.size() == 0) 
        {
            out = 1;
        } else if (this.dealerDeck.inHand.size() == 0) 
        {
            out = 2;
        }
        return out;
    }
}

