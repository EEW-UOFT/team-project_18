package interfaceadapter.viewgameresult;

import entity.CurrentGame;

public class ViewGameResultViewModel {
    private String gameResult;
    private int playerScore;
    private int dealerScore;
    private CurrentGame currentGame;

    public ViewGameResultViewModel() {
    }

    public String getGameResult() {
        return gameResult;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public int getDealerScore() {
        return dealerScore;
    }

    public String getPlayerScoreString() {
        return "Player Score: " + Integer.toString(playerScore);
    }

    public String getDealerScoreString() {
        return "Dealer Score: " + Integer.toString(dealerScore);
    }

    public CurrentGame getCurrentGame() {
        return currentGame;
    }

    public void setGameResult(String gameResult) {
        this.gameResult = gameResult;
    }

    public void setPlayerScore(int playerScore) {
        if (playerScore < 0) {
            throw new IllegalArgumentException("Score cannot be negative");
        }
        else if (playerScore >= 31) {
            throw new IllegalArgumentException("Score cannot be greater than 30");
        }
        this.playerScore = playerScore;
    }

    public void setDealerScore(int dealerScore) {
        if (dealerScore < 0) {
            throw new IllegalArgumentException("Score cannot be negative");
        }
        else if (dealerScore >= 31) {
            throw new IllegalArgumentException("Score cannot be greater than 30");
        }
        this.dealerScore = dealerScore;
    }

    public void setCurrentGame(CurrentGame currentGame) {

        this.currentGame = currentGame;
    }
}
