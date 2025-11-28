package use.Case.stand;

import entity.Card;

public interface StandOutputBoundary {

    // NEW: match what StandInteractor is calling
    void prepareSuccessView(StandOutputData outputData);
    void prepareFailView(String message);
}
