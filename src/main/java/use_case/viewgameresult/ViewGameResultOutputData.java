package use_case.viewgameresult;

import entity.CurrentGame;

public class ViewGameResultOutputData {
    private final CurrentGame currentGame;
    private final String outcome;
    private final int playerScore;
    private final int dealerScore;

    public ViewGameResultOutputData(CurrentGame currentGame, String outcome,
                                    int playerScore, int dealerScore) {
        this.currentGame = currentGame;
        this.outcome = outcome;
        this.playerScore = playerScore;
        this.dealerScore = dealerScore;
    }

    public String getOutcome() {
        return this.outcome;
    }
    public int getPlayerScore() {
        return this.playerScore;
    }
    public int getDealerScore() {
        return this.dealerScore;
    }
    public CurrentGame getCurrentGame() {
        return this.currentGame;
    }
}
