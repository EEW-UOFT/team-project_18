package data.Access;

import entity.Card;

import java.util.List;

public interface DeckApiInterface {

    List<Card> drawCards(int cardNumber) throws UnableToLoadDeck;

    class UnableToLoadDeck extends Exception {

        public UnableToLoadDeck() {
            super("Unable to load deck");
        }
    }
}
