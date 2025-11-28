package interfaceadapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

import entity.*;
import use.Case.hit.HitOutputData;

public class GameModel {

    private CurrentGame currentGame;

    private List<Card> playerCards;
    private List<Card> dealerCards;
    private int playerScore;
    private int dealerScore;
    private boolean playerTurn;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public GameModel() {
        this.currentGame = null;
        this.playerCards = new ArrayList<>();
        this.dealerCards = new ArrayList<>();
        this.playerScore = 0;
        this.dealerScore = 0;
    }
    public GameModel(CurrentGame currentGame) {
        if (currentGame != null) {
            this.currentGame = currentGame;
            this.playerCards = currentGame.getPlayerHand();
            this.dealerCards = currentGame.getDealerHand();
            this.playerScore = currentGame.calculateScore(playerCards);
            this.dealerScore = currentGame.calculateScore(dealerCards);
        } else {
            this.currentGame = null;
            this.playerCards = new ArrayList<>();
            this.dealerCards = new ArrayList<>();
            this.playerScore = 0;
            this.dealerScore = 0;
        }
    }

    public void update(HitOutputData hitOutputData) {
        CurrentGame oldGame = this.currentGame;
        this.currentGame = hitOutputData.getCurrentGame();
        this.playerCards = currentGame.getPlayerHand();
        this.dealerCards = currentGame.getDealerHand();
        this.playerScore = currentGame.calculateScore(playerCards);
        this.dealerScore = currentGame.calculateScore(dealerCards);

        support.firePropertyChange("currentGame", oldGame, currentGame);
        support.firePropertyChange("playerHand", null, playerCards);
    }

    public CurrentGame getCurrentGame() {
        return currentGame;
    }

    public List<Card> getPlayerCards() {
        return playerCards;
    }

    public List<Card> getDealerCards() {
        return dealerCards;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public int getDealerScore() {
        return dealerScore;
    }

    public void setPlayerTurn(boolean playerTurn) {
        this.playerTurn = playerTurn;
    }

    public boolean getPlayerTurn() {
        return playerTurn;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

}
