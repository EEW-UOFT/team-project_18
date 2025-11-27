package use.Case.stand;

import data.Access.DeckApiInterface;

public interface StandInputBoundary {
    void execute(StandInputData inputData) throws DeckApiInterface.UnableToLoadDeck;
}
