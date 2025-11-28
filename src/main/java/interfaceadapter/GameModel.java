package interfaceadapter;

import entity.Card;
import entity.CurrentGame;
import use.Case.hit.HitOutputData;

import java.beans.PropertyChangeSupport;
import java.util.List;

public class GameModel {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private CurrentGame currentGame;
    private List<Card> playerCards;
    private List<Card> dealerCards;
    private int playerScore;
    private int dealerScore;
    private boolean playerTurn;

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

    public boolean getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(boolean playerTurn) {
        this.playerTurn = playerTurn;
    }

}
