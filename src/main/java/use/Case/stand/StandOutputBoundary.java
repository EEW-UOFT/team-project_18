package use.Case.stand;

import entity.Card;

public interface StandOutputBoundary {

    void presentDealerDrew(Card card, int dealerTotal);

    void presentResult(StandOutputData outputData);

    void presentError(String message);

    // NEW: match what StandInteractor is calling
    void prepareSuccessView(StandOutputData outputData);
    void prepareFailView(String message);
}
