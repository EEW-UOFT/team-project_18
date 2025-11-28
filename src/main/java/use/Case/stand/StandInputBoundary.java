package use.Case.stand;

import data.Access.DeckApiInterface;
import entity.CurrentGame;

public interface StandInputBoundary {
    void execute(CurrentGame currentGame) throws DeckApiInterface.UnableToLoadDeck;
}
