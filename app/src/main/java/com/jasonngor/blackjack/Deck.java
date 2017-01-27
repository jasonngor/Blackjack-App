package com.jasonngor.blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by Jason Ngor on 1/24/2017.
 */

public class Deck implements Iterable<Card>{
    private ArrayList<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
    }

    @Override
    public String toString() {
        String s = "";
        for (Card card:cards) {
            s += card;
            s += "\n";
        }
        return s;
    }

    public void newDeck() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                Card card = new Card(i, j);
                cards.add(card);
            }
        }
    }
    public Card popCard() {
        Card card = cards.remove(cards.size() - 1);
        return card;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public void sort() {
        Collections.sort(cards);
    }

    public int size() {
        return cards.size();
    }

    @Override
    public Iterator<Card> iterator() {
        return cards.iterator();
    }
}
