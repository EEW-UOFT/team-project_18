package data.Access;

import java.util.List;

import entity.Card;

public interface DeckApiInterface {

    List<Card> drawCards(int cardNumber) throws UnableToLoadDeck;

    class UnableToLoadDeck extends Exception {

        public UnableToLoadDeck() {
            super("Unable to load deck");
        }
    }
}