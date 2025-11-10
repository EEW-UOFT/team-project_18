package interface_adapter;

import use_case.StandOutputBoundary;

public class ConsolePresenter implements StandOutputBoundary {
    @Override
    public void presentDealerDrew(String value, int dealerTotal) {
        System.out.println("Dealer drew: " + value + " | dealer total = " + dealerTotal);
    }
    @Override
    public void presentResult(String outcome, int playerTotal, int dealerTotal) {
        System.out.println("RESULT: " + outcome + " (Player " + playerTotal + " vs Dealer " + dealerTotal + ")");
    }
    @Override
    public void presentError(String message) {
        System.err.println(message);
    }
}

