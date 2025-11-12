package entity;

import java.util.*;

public class CurrentGame {

    private User player;
    private List<Card> playerHand;
    private List<Card> dealerHand;
    private GameState gameState = GameState.ONGOING;
    private Deck deck;

    public int returnDeckID() {
        //Return the deckID
        return deck.returnDeckID();
    }

    public void addCardPlayer(Card card) {
        //Add a card to the player's hand
        playerHand.add(card);
    }

    public void addCardDealer(Card card) {
        //Add a card to the dealer's hand
        dealerHand.add(card);
    }

    public void gameWon() {
        //Change the game state to "WIN"
        gameState = GameState.WIN;
    }

    public void gameLost() {
        //Change the game state to "LOST", aka Dealer won
        gameState = GameState.LOST;
    }

    public void gameDraw() {
        //Change the game state to "DRAW"
        gameState = GameState.DRAW;
    }

    public String outcome() {
        //Return a string concerning the current game state
        switch (gameState) {
            case WIN:
                return "Player Won";
                break;
            case LOST:
                return "Dealer Won";
                break;
            case DRAW:
                return "Draw";
                break;
            case ONGOING:
                return "Game is currently ongoing";
        }
        return null;
    }

}
