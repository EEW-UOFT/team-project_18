package data_access;

import entity.Card;

import java.util.List;

public interface DeckApiInterface {

    // Checked exception for any API / HTTP failure.
    class UnableToLoadDeck extends Exception {
        public UnableToLoadDeck() {
            super("Unable to load deck");
        }

        public UnableToLoadDeck(String message) {
            super(message);
        }
    }

    String initializeNewDeck() throws UnableToLoadDeck;

    List<Card> drawCards(int n) throws UnableToLoadDeck;
}

