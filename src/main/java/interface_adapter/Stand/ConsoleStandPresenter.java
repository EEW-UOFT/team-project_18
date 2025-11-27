package interface_adapter.Stand;

import entity.Card;
import use_case.Stand.StandOutputBoundary;
import use_case.Stand.StandOutputData;

public class ConsoleStandPresenter implements StandOutputBoundary {

    @Override
    public void presentDealerDrew(Card card, int dealerTotal) {
        System.out.println("Dealer drew: " + card.getValue()
                + " of " + card.getSuit()
                + " | dealer total = " + dealerTotal);
    }

    @Override
    public void presentResult(StandOutputData outputData) {
        System.out.println("RESULT: " + outputData.getOutcome()
                + " (Player " + outputData.getPlayerTotal()
                + " vs Dealer " + outputData.getDealerTotal() + ")");
    }

    @Override
    public void presentError(String message) {
        System.out.println("Stand failed: " + message);
    }
}
