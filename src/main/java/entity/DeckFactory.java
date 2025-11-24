package entity;

import data_access.Deck;
import data_access.DeckAPIInterface;

public class DeckFactory {

    public static Deck createDeck() throws DeckAPIInterface.UnableToLoadDeck {
        return new Deck();
    }
}
