package use_case.Stand;

import data_access.DeckAPIInterface;

public interface StandInputBoundary {
    void execute(StandInputData inputData) throws DeckAPIInterface.UnableToLoadDeck;
}



