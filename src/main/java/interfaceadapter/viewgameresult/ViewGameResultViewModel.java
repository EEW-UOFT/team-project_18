package interfaceadapter.viewgameresult;

import entity.CurrentGame;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ViewGameResultViewModel {
    private String gameResult;
    private int playerScore;
    private int dealerScore;
    private CurrentGame currentGame;

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public ViewGameResultViewModel() {
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
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
        final String oldResult = this.gameResult;
        support.firePropertyChange("gameResult", oldResult, gameResult);
        this.gameResult = gameResult;
    }

    public void setPlayerScore(int playerScore) {
        if (playerScore < 0) {
            throw new IllegalArgumentException("Score cannot be negative");
        }
        String oldScoreString = getPlayerScoreString();
        this.playerScore = playerScore;
        support.firePropertyChange("playerScore", oldScoreString, getPlayerScoreString());
    }

    public void setDealerScore(int dealerScore) {
        if (dealerScore < 0) {
            throw new IllegalArgumentException("Score cannot be negative");
        }
        String oldScoreString = getDealerScoreString();
        this.dealerScore = dealerScore;
        support.firePropertyChange("dealerScore", oldScoreString, getDealerScoreString());
    }

    public void setCurrentGame(CurrentGame currentGame) {
        this.currentGame = currentGame;
    }


}
