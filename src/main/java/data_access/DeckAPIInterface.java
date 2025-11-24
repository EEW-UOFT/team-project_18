package data_access;

import entity.Card;

import java.util.List;

public interface DeckAPIInterface {

    /**
     * Exception thrown when the deck API cannot be reached
     * or returns an invalid response.
     */
    class UnableToLoadDeck extends Exception {
        public UnableToLoadDeck() {
            super("Unable to load deck");
        }

        public UnableToLoadDeck(String message) {
            super(message);
        }

        public UnableToLoadDeck(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * Initialize a new shuffled deck from the Deck of Cards API.
     * Implementations should set their internal deckID.
     */
    void initializeNewDeck() throws UnableToLoadDeck;

    /**
     * Draw n cards from the current deck.
     */
    List<Card> drawCards(int n) throws UnableToLoadDeck;
}
