package entity;

import data.Access.Deck;
import data.Access.DeckApiInterface;

public class DeckFactory {

    public static Deck createDeck() throws DeckApiInterface.UnableToLoadDeck {
        return new Deck();
    }
}
