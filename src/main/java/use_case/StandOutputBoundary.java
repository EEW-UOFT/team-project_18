package use_case;

import entity.Card;

public interface StandOutputBoundary {
    void presentDealerDrew(Card card, int dealerTotal);
    void presentResult(StandOutputData outputData);
    void presentError(String message);
}
