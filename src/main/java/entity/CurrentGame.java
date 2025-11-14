package entity;

import java.util.*;

public class CurrentGame {

    private User player;
    private List<Card> playerHand;
    private List<Card> dealerHand;
    private GameState gameState = GameState.ONGOING;
    private Deck deck;

    public CurrentGame(User player, Deck deck) {
        this.player = player;
        this.deck = deck;
        this.playerHand = new ArrayList<>();
        this.dealerHand = new ArrayList<>();
    }

    public String returnDeckID() {
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
            case LOST:
                return "Dealer Won";
            case DRAW:
                return "Draw";
            case ONGOING:
                return "Game is currently ongoing";
            default:
                return "Game is currently over";
        }
    }

    public List<Card> getPlayerHand() {return playerHand;}

    public List<Card> getDealerHand() {return dealerHand;}

    public GameState getGameState() {return gameState;}

    public Deck getDeck() {return deck;}

    public User getPlayer() {return player;}
}
