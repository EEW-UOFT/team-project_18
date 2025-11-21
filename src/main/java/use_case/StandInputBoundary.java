package use_case;

import data_access.DeckAPIInterface;

public interface StandInputBoundary {
    void execute(StandInputData inputData) throws DeckAPIInterface.UnableToLoadDeck;
}



