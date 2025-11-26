package entity;

import data_access.DeckAPIInterface;

import java.util.*;

public class CurrentGame {

    private final User player;
    private List<Card> playerHand;
    private List<Card> dealerHand;
    private GameState gameState = GameState.ONGOING;
    private final DeckAPIInterface deck;

    Map<String, Integer> blackjackMap = new HashMap<>();


    public CurrentGame(User player) throws DeckAPIInterface.UnableToLoadDeck {
        this.player = player;
        this.deck = DeckFactory.createDeck();
        this.playerHand = new ArrayList<>();
        this.dealerHand = new ArrayList<>();
        blackjackMap.put("ACE", 1);
        blackjackMap.put("2", 2);
        blackjackMap.put("3", 3);
        blackjackMap.put("4", 4);
        blackjackMap.put("5", 5);
        blackjackMap.put("6", 6);
        blackjackMap.put("7", 7);
        blackjackMap.put("8", 8);
        blackjackMap.put("9", 9);
        blackjackMap.put("10", 10);
        blackjackMap.put("KING", 10);
        blackjackMap.put("QUEEN", 10);
        blackjackMap.put("JACK", 10);
    }

    public void addCardPlayer(int n) throws DeckAPIInterface.UnableToLoadDeck {
        //Add cards to the player's hand
        List<Card> tempCards = deck.drawCards(n);
        playerHand.addAll(tempCards);
    }

    public void addSingleCardPlayer(Card card) {
        playerHand.add(card);
    }

    public void addCardDealer(int n, boolean faceUp) throws DeckAPIInterface.UnableToLoadDeck {
        //Add cards to the dealer's hand
        List<Card> tempCards = deck.drawCards(n);
        for (Card card : tempCards) {
            card.setFaceUp(faceUp);
        }
        dealerHand.addAll(tempCards);
    }

    public void dealerReveal() {
        for  (Card card : dealerHand) {
            card.setFaceUp(true);
        }
    }

    public void addSingleCardDealer(Card card) {
        dealerHand.add(card);
    }

    public int calculateScore(List<Card> hand) {
        int score = 0;
        boolean ace = false;
        for  (Card card : hand) {
            score += blackjackMap.get(card.getValue());
            if (card.getValue().equals("ACE")) {
                ace = true;
            }
        }
        if (ace && score <= 11) {
            score += 10;
        }
        return score;
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

    public DeckAPIInterface getDeck() {return deck;}

    public User getPlayer() {return player;}
}
