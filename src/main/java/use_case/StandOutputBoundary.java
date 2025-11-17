package use_case;

import entity.Card;

public interface StandOutputBoundary {

    // Called every time the dealer draws a card
    void presentDealerDrew(Card card, int dealerTotal);

    // Called when the Stand use case is finished
    void presentResult(StandOutputData outputData);

    // Called if something goes wrong (API issues etc.)
    void presentError(String message);
}

