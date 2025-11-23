package use_case.stand;

public class StandOutputData {
    private final int playerTotal;
    private final int dealerTotal;
    private final String outcome;

    public StandOutputData(int playerTotal, int dealerTotal, String outcome) {
        this.playerTotal = playerTotal;
        this.dealerTotal = dealerTotal;
        this.outcome = outcome;
    }

    public int getPlayerTotal() {
        return playerTotal;
    }

    public int getDealerTotal() {
        return dealerTotal;
    }

    public String getOutcome() {
        return outcome;
    }
}

