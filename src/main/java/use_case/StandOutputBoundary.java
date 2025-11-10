package use_case;

public interface StandOutputBoundary {
    void presentDealerDrew(String value, int dealerTotal);
    void presentResult(String outcome, int playerTotal, int dealerTotal);
    void presentError(String message);
}

