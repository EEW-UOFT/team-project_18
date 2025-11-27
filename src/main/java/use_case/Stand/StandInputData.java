package use_case.Stand;

import data_access.DeckAPIInterface;

public class StandInputData {

    private final DeckAPIInterface deck;
    private final int playerTotal;

    public StandInputData(DeckAPIInterface deck, int playerTotal) {
        this.deck = deck;
        this.playerTotal = playerTotal;
    }

    public DeckAPIInterface getDeck() {
        return deck;
    }

    public int getPlayerTotal() {
        return playerTotal;
    }
}