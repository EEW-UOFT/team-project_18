package data_access;

public interface DeckGateway {
    /** Returns a deck_id (e.g., with deck_count=6 for a Blackjack shoe). */
    String createShuffledShoe(int deckCount) throws Exception;

    /** Draws a single card and returns its "value": "2".."10","JACK","QUEEN","KING","ACE". */
    String drawOneValue(String deckId) throws Exception;
}

