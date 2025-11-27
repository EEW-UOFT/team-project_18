package use_case.Stand;

import entity.Card;

public interface StandOutputBoundary {
    void presentDealerDrew(Card card, int dealerTotal);
    void presentResult(StandOutputData outputData);
    void presentError(String message);
}
