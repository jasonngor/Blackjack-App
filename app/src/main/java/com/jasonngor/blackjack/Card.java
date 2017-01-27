package com.jasonngor.blackjack;

/**
 * Created by Jason Ngor on 1/23/2017.
 */

enum Suit {
    CLUBS("Clubs"),
    DIAMONDS("Diamonds"),
    HEARTS("Hearts"),
    SPADES("Spades");

    private final String suit;

    Suit(String suit) {
        this.suit = suit;
    }

    @Override
    public String toString() {
        return suit;
    }
}

enum Rank {
    ACE("Ace"),
    Two("2"),
    Three("3"),
    Four("4"),
    Five("5"),
    Six("6"),
    Seven("7"),
    Eight("8"),
    Nine("9"),
    Ten("10"),
    Jack("Jack"),
    Queen("Queen"),
    King("King");

    private final String rank;

    Rank(String rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return rank;
    }
}

public class Card implements Comparable<Card> {
    private int suit;
    private int rank;

    public Card(int suit, int rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Card() {
        this(0, 0);
    }

    @Override
    public String toString() {
        return Rank.values()[rank] + " of " + Suit.values()[suit];
    }

    @Override
    public int compareTo(Card o) {
        if (this.rank == o.rank) {
            return this.suit - o.suit;
        }
        return this.rank - o.rank;
    }
}
