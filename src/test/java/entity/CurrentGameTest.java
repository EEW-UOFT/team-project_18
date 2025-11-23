package entity;

import data_access.DeckAPIInterface;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class CurrentGameTest {

    @Test
    void testCurrentGameCreation() {
        DeckAPIInterface mockDeck = new MockDeck();
        User user = new User(new ArrayList<>());

        CurrentGame game = new CurrentGame(user, mockDeck);

        assertEquals(GameState.ONGOING, game.getGameState());
        assertTrue(game.getPlayerHand().isEmpty());
        assertTrue(game.getDealerHand().isEmpty());
        assertEquals(user, game.getPlayer());
        assertEquals(mockDeck, game.getDeck());
    }

    private static class MockDeck implements DeckAPIInterface {
        @Override
        public List<Card> drawCards(int n) {
            return new ArrayList<>();
        }
    }

    @Test
    void testAddCardsToPlayer() {
        DeckAPIInterface mockDeck = new MockDeck();
        User user = new User(new ArrayList<>());
        CurrentGame game = new CurrentGame(user, mockDeck);

        Card card1 = new Card("HEARTS", "ACE");
        Card card2 = new Card("DIAMONDS", "10");

        game.addCardPlayer(card1);
        game.addCardPlayer(card2);

        List<Card> playerHand = game.getPlayerHand();
        assertEquals(2, playerHand.size());
        assertEquals(card1, playerHand.get(0));
        assertEquals(card2, playerHand.get(1));
    }

    @Test
    void testAddCardsToDealer() {
        DeckAPIInterface mockDeck = new MockDeck();
        User user = new User(new ArrayList<>());
        CurrentGame game = new CurrentGame(user, mockDeck);

        Card card1 = new Card("SPADES", "7");
        Card card2 = new Card("CLUBS", "KING");

        game.addCardDealer(card1);
        game.addCardDealer(card2);

        List<Card> dealerHand = game.getDealerHand();
        assertEquals(2, dealerHand.size());
        assertEquals(card1, dealerHand.get(0));
        assertEquals(card2, dealerHand.get(1));
    }

    @Test
    void testGameStateChanges() {
        DeckAPIInterface mockDeck = new MockDeck();
        User user = new User(new ArrayList<>());
        CurrentGame game = new CurrentGame(user, mockDeck);

        assertEquals(GameState.ONGOING, game.getGameState());

        game.gameWon();
        assertEquals(GameState.WIN, game.getGameState());

        game.gameLost();
        assertEquals(GameState.LOST, game.getGameState());

        game.gameDraw();
        assertEquals(GameState.DRAW, game.getGameState());
    }

    @Test
    void testOutcomeMessages() {
        DeckAPIInterface mockDeck = new MockDeck();
        User user = new User(new ArrayList<>());
        CurrentGame game = new CurrentGame(user, mockDeck);

        game.gameWon();
        assertEquals("Player Won", game.outcome());

        game.gameLost();
        assertEquals("Dealer Won", game.outcome());

        game.gameDraw();
        assertEquals("Draw", game.outcome());

        CurrentGame ongoingGame = new CurrentGame(user, mockDeck);
        assertEquals("Game is currently ongoing", ongoingGame.outcome());
    }
}
