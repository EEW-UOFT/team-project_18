package interface_adapter;

import entity.*;
import use_case.hit.HitOutputData;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

public class GameModel {

    private CurrentGame currentGame;

    private List<Card> playerCards;
    private List<Card> dealerCards;
    private int playerScore;
    private int dealerScore;
    boolean playerTurn;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public GameModel(CurrentGame currentGame) {
        this.currentGame = currentGame;
        this.playerCards = currentGame.getPlayerHand();
        this.dealerCards = currentGame.getDealerHand();
        this.playerScore = currentGame.calculateScore(playerCards);
        this.dealerScore = currentGame.calculateScore(dealerCards);
    }

    public void update(HitOutputData hitOutputData) {
        this.currentGame = hitOutputData.getCurrentGame();
        this.playerCards = currentGame.getPlayerHand();
        this.dealerCards = currentGame.getDealerHand();
        this.playerScore = currentGame.calculateScore(playerCards);
        this.dealerScore = currentGame.calculateScore(dealerCards);
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

}
