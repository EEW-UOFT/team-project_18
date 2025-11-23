package use_case.stand;

import data_access.Deck;

public class StandInputData {
    private final Deck deck;
    private final int playerTotal;

    public StandInputData(Deck deck, int playerTotal) {
        this.deck = deck;
        this.playerTotal = playerTotal;
    }

    public Deck getDeck() {
        return deck;
    }

    public int getPlayerTotal() {
        return playerTotal;
    }
}


