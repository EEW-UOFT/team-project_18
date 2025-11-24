package data_access;

import entity.Card;

import java.util.List;

public interface DeckAPIInterface {

    String getDeckID();

    String initializeNewDeck() throws UnableToLoadDeck;

    //Draws n cards
    public List<Card> drawCards(int n) throws UnableToLoadDeck;

    List<Card> getDrawnCards();

    class UnableToLoadDeck extends Exception {
        //Exception thrown when API call fails
        public UnableToLoadDeck() {
            super("Unable to load deck");
        }
    }
}