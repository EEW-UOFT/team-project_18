package data_access;

import entity.Card;

import java.util.List;

public interface DeckAPIInterface {

    class UnableToLoadDeck extends Exception{
        //Exception thrown when API call fails
        public UnableToLoadDeck(){
            super("Unable to load deck");
        }
    }

    //Draws n cards
    List<Card> drawCards(int n) throws UnableToLoadDeck;
}

    public class DeckFactory {
        public static Deck createDeck() throws DeckAPIInterface.UnableToLoadDeck {
            return new Deck();
        }
    }