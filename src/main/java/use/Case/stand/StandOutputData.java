package use.Case.stand;

import entity.CurrentGame;

public class StandOutputData {
    private final int playerTotal;
    private final int dealerTotal;
    private final String outcome;
    private final CurrentGame currentGame;

    public StandOutputData(int playerTotal, int dealerTotal, String outcome) {
        this.playerTotal = playerTotal;
        this.dealerTotal = dealerTotal;
        this.outcome = outcome;
        this.currentGame = null;

    }

    public StandOutputData(CurrentGame currentGame, String outcome) {
        this.currentGame = currentGame;
        this.playerTotal = currentGame.calculateScore(currentGame.getPlayerHand());
        this.dealerTotal = currentGame.calculateScore(currentGame.getDealerHand());
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

