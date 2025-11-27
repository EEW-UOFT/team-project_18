package use_case.Stand;

import data_access.DeckApiInterface;

public class StandInputData {

    private final DeckApiInterface deck;
    private final int playerTotal;

    public StandInputData(DeckApiInterface deck, int playerTotal) {
        this.deck = deck;
        this.playerTotal = playerTotal;
    }

    public DeckApiInterface getDeck() {
        return deck;
    }

    public int getPlayerTotal() {
        return playerTotal;
    }
}