package use_case;

import entity.Card;

public interface StandOutputBoundary {

    // NEW: match what StandInteractor is calling
    void presentDealerDrew(Card card, int dealerTotal);

    // Already there (keep these)
    void presentResult(StandOutputData outputData);

    void presentError(String message);
}


