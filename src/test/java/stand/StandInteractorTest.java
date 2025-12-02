package stand;

import data.Access.Deck;
import data.Access.DeckApiInterface;
import entity.Card;
import entity.CurrentGame;
import entity.GameState;
import entity.HistoryEntry;
import entity.User;
import org.junit.jupiter.api.Test;
import use.Case.stand.StandInputData;
import use.Case.stand.StandInteractor;
import use.Case.stand.StandOutputBoundary;
import use.Case.stand.StandOutputData;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link StandInteractor}.
 *
 * These tests cover:
 * - The private cardValue(Card) helper via reflection.
 * - The full execute(StandInputData) flow through all outcome branches.
 * - The error path when the deck fails (catch block).
 */
public class StandInteractorTest {

    /**
     * Helper that calls the private cardValue method on StandInteractor
     * using reflection.
     */
    private int invokeCardValue(final Card card) throws Exception {
        DummyPresenter presenter = new DummyPresenter();
        StandInteractor interactor = new StandInteractor(presenter);

        Method m = StandInteractor.class.getDeclaredMethod("cardValue", Card.class);
        m.setAccessible(true);
        return (int) m.invoke(interactor, card);
    }

    @Test
    void numberedCardsReturnTheirNumericValue() throws Exception {
        Card twoHearts = new Card("HEARTS", "2", "url");
        Card nineSpades = new Card("SPADES", "9", "url");

        assertEquals(2, invokeCardValue(twoHearts));
        assertEquals(9, invokeCardValue(nineSpades));
    }

    @Test
    void faceCardsAreWorthTen() throws Exception {
        Card jack = new Card("CLUBS", "JACK", "url");
        Card queen = new Card("DIAMONDS", "QUEEN", "url");
        Card king = new Card("HEARTS", "KING", "url");

        assertEquals(10, invokeCardValue(jack));
        assertEquals(10, invokeCardValue(queen));
        assertEquals(10, invokeCardValue(king));
    }

    @Test
    void aceIsWorthElevenInCurrentImplementation() throws Exception {
        Card ace = new Card("SPADES", "ACE", "url");

        assertEquals(11, invokeCardValue(ace));
    }

    @Test
    void invalidCardValueCausesNumberFormatException() {
        Card weird = new Card("HEARTS", "NOT_A_NUMBER", "url");

        DummyPresenter presenter = new DummyPresenter();
        StandInteractor interactor = new StandInteractor(presenter);

        assertThrows(Exception.class, () -> {
            try {
                Method m = StandInteractor.class.getDeclaredMethod("cardValue", Card.class);
                m.setAccessible(true);
                m.invoke(interactor, weird);
            } catch (Exception e) {
                // unwrap reflective invocation exceptions
                throw e.getCause() != null ? e.getCause() : e;
            }
        });
    }

    @Test
    void tenCardIsWorthTen() throws Exception {
        Card tenDiamonds = new Card("DIAMONDS", "10", "url");
        assertEquals(10, invokeCardValue(tenDiamonds));
    }

    // ---------- EXTRA TESTS FOR MORE CARDVALUE COVERAGE ----------

    @Test
    void multipleNumberedCardsAreIndependent() throws Exception {
        Card three = new Card("CLUBS", "3", "url");
        Card seven = new Card("HEARTS", "7", "url");
        Card eight = new Card("SPADES", "8", "url");

        assertEquals(3, invokeCardValue(three));
        assertEquals(7, invokeCardValue(seven));
        assertEquals(8, invokeCardValue(eight));
    }

    @Test
    void aceAndFaceCardHaveDifferentValues() throws Exception {
        Card ace = new Card("HEARTS", "ACE", "url");
        Card king = new Card("SPADES", "KING", "url");

        int aceValue = invokeCardValue(ace);
        int kingValue = invokeCardValue(king);

        assertEquals(11, aceValue);
        assertEquals(10, kingValue);
        assertTrue(aceValue > kingValue);
    }

    // ---------- EXECUTE() BRANCH TESTS FOR FULL COVERAGE ----------

    /**
     * playerTotal > 21 -> "Dealer wins (player busts)" and gameLost().
     */
    @Test
    void execute_PlayerBusts_DealerWins() throws Exception {
        DummyPresenter presenter = new DummyPresenter();
        User user = new User(new ArrayList<>());
        CurrentGame game = new CurrentGame(user);

        // dealer: 10 + 8 = 18 (>=17)
        game.addSingleCardDealer(new Card("HEARTS", "10", "url"));
        game.addSingleCardDealer(new Card("CLUBS", "8", "url"));

        // player: 10 + 10 + 5 = 25 (>21 -> bust)
        game.addSingleCardPlayer(new Card("SPADES", "10", "url"));
        game.addSingleCardPlayer(new Card("DIAMONDS", "10", "url"));
        game.addSingleCardPlayer(new Card("HEARTS", "5", "url"));

        StandInputData input = new StandInputData(game, user);
        StandInteractor interactor = new StandInteractor(presenter);

        List<HistoryEntry> history = user.getGameHistory();
        int beforeSize = history.size();

        interactor.execute(input);

        assertEquals(GameState.LOST, game.getGameState());
        assertNotNull(presenter.lastOutput);
        assertNull(presenter.lastErrorMessage);

        assertEquals(beforeSize + 1, history.size());
        HistoryEntry last = history.get(history.size() - 1);
        assertEquals("Dealer wins (player busts)", last.getOutcome());
    }

    /**
     * dealerTotal > 21 -> "Player wins (dealer busts)" and gameWon().
     */
    @Test
    void execute_DealerBusts_PlayerWins() throws Exception {
        DummyPresenter presenter = new DummyPresenter();
        User user = new User(new ArrayList<>());
        CurrentGame game = new CurrentGame(user);

        // dealer: 10 + 10 + 5 = 25 (>21 -> bust)
        game.addSingleCardDealer(new Card("HEARTS", "10", "url"));
        game.addSingleCardDealer(new Card("CLUBS", "10", "url"));
        game.addSingleCardDealer(new Card("DIAMONDS", "5", "url"));

        // player: 10 + 8 = 18
        game.addSingleCardPlayer(new Card("SPADES", "10", "url"));
        game.addSingleCardPlayer(new Card("HEARTS", "8", "url"));

        StandInputData input = new StandInputData(game, user);
        StandInteractor interactor = new StandInteractor(presenter);

        List<HistoryEntry> history = user.getGameHistory();
        int beforeSize = history.size();

        interactor.execute(input);

        assertEquals(GameState.WIN, game.getGameState());
        assertNotNull(presenter.lastOutput);
        assertNull(presenter.lastErrorMessage);

        assertEquals(beforeSize + 1, history.size());
        HistoryEntry last = history.get(history.size() - 1);
        assertEquals("Player wins (dealer busts)", last.getOutcome());
    }

    /**
     * playerTotal > dealerTotal, both <= 21 -> "Player wins".
     */
    @Test
    void execute_PlayerHigherTotal_PlayerWins() throws Exception {
        DummyPresenter presenter = new DummyPresenter();
        User user = new User(new ArrayList<>());
        CurrentGame game = new CurrentGame(user);

        // dealer: 10 + 8 = 18
        game.addSingleCardDealer(new Card("HEARTS", "10", "url"));
        game.addSingleCardDealer(new Card("CLUBS", "8", "url"));

        // player: 10 + 10 = 20
        game.addSingleCardPlayer(new Card("SPADES", "10", "url"));
        game.addSingleCardPlayer(new Card("DIAMONDS", "10", "url"));

        StandInputData input = new StandInputData(game, user);
        StandInteractor interactor = new StandInteractor(presenter);

        List<HistoryEntry> history = user.getGameHistory();
        int beforeSize = history.size();

        interactor.execute(input);

        assertEquals(GameState.WIN, game.getGameState());
        assertNotNull(presenter.lastOutput);
        assertNull(presenter.lastErrorMessage);

        assertEquals(beforeSize + 1, history.size());
        HistoryEntry last = history.get(history.size() - 1);
        assertEquals("Player wins", last.getOutcome());
    }

    /**
     * dealerTotal > playerTotal, both <= 21 -> "Dealer wins".
     */
    @Test
    void execute_DealerHigherTotal_DealerWins() throws Exception {
        DummyPresenter presenter = new DummyPresenter();
        User user = new User(new ArrayList<>());
        CurrentGame game = new CurrentGame(user);

        // dealer: 10 + 10 = 20
        game.addSingleCardDealer(new Card("HEARTS", "10", "url"));
        game.addSingleCardDealer(new Card("CLUBS", "10", "url"));

        // player: 10 + 8 = 18
        game.addSingleCardPlayer(new Card("SPADES", "10", "url"));
        game.addSingleCardPlayer(new Card("DIAMONDS", "8", "url"));

        StandInputData input = new StandInputData(game, user);
        StandInteractor interactor = new StandInteractor(presenter);

        List<HistoryEntry> history = user.getGameHistory();
        int beforeSize = history.size();

        interactor.execute(input);

        assertEquals(GameState.LOST, game.getGameState());
        assertNotNull(presenter.lastOutput);
        assertNull(presenter.lastErrorMessage);

        assertEquals(beforeSize + 1, history.size());
        HistoryEntry last = history.get(history.size() - 1);
        assertEquals("Dealer wins", last.getOutcome());
    }

    /**
     * dealerTotal == playerTotal -> "Draw".
     */
    @Test
    void execute_EqualTotals_Draw() throws Exception {
        DummyPresenter presenter = new DummyPresenter();
        User user = new User(new ArrayList<>());
        CurrentGame game = new CurrentGame(user);

        // dealer: 10 + 9 = 19
        game.addSingleCardDealer(new Card("HEARTS", "10", "url"));
        game.addSingleCardDealer(new Card("CLUBS", "9", "url"));

        // player: 10 + 9 = 19
        game.addSingleCardPlayer(new Card("SPADES", "10", "url"));
        game.addSingleCardPlayer(new Card("DIAMONDS", "9", "url"));

        StandInputData input = new StandInputData(game, user);
        StandInteractor interactor = new StandInteractor(presenter);

        List<HistoryEntry> history = user.getGameHistory();
        int beforeSize = history.size();

        interactor.execute(input);

        assertEquals(GameState.DRAW, game.getGameState());
        assertNotNull(presenter.lastOutput);
        assertNull(presenter.lastErrorMessage);

        assertEquals(beforeSize + 1, history.size());
        HistoryEntry last = history.get(history.size() - 1);
        assertEquals("Draw", last.getOutcome());
    }

    /**
     * Forces the dealer draw loop to call addCardDealer, which in this fake
     * game throws Deck.UnableToLoadDeck, so the catch block is executed and
     * prepareFailView is called. Game history size should NOT change.
     */
    @Test
    void execute_DeckFailure_TriggersFailViewAndNoNewHistory() throws Exception {
        DummyPresenter presenter = new DummyPresenter();
        User user = new User(new ArrayList<>());
        CurrentGame game = new FailingCurrentGame(user);

        StandInputData input = new StandInputData(game, user);
        StandInteractor interactor = new StandInteractor(presenter);

        List<HistoryEntry> history = user.getGameHistory();
        int beforeSize = history.size();

        interactor.execute(input);

        assertNull(presenter.lastOutput);
        assertNotNull(presenter.lastErrorMessage);

        // no new history entries added in the failure case
        assertEquals(beforeSize, history.size());
    }

    /**
     * Extra test to exercise the body of the dealer draw loop AND
     * the line that re-assigns dealerTotal inside the loop.
     * We use LoopCurrentGame to fake dealer scores: first < 17, then >= 17.
     */
    @Test
    void execute_DealerDrawLoopRunsAndPlayerStillWins() throws Exception {
        DummyPresenter presenter = new DummyPresenter();
        User user = new User(new ArrayList<>());
        CurrentGame game = new LoopCurrentGame(user);

        // No need to add cards; scores are controlled in LoopCurrentGame.

        StandInputData input = new StandInputData(game, user);
        StandInteractor interactor = new StandInteractor(presenter);

        List<HistoryEntry> history = user.getGameHistory();
        int beforeSize = history.size();

        interactor.execute(input);

        assertEquals(GameState.WIN, game.getGameState());
        assertNotNull(presenter.lastOutput);
        assertNull(presenter.lastErrorMessage);

        assertEquals(beforeSize + 1, history.size());
        HistoryEntry last = history.get(history.size() - 1);
        assertEquals("Player wins", last.getOutcome());
    }

    /**
     * Minimal presenter implementation so we can instantiate {@link StandInteractor}
     * without depending on the UI. We use the prepareSuccessView/prepareFailView
     * methods because that's what StandInteractor calls.
     */
    private static class DummyPresenter implements StandOutputBoundary {

        StandOutputData lastOutput;
        String lastErrorMessage;

        @Override
        public void presentDealerDrew(final Card card, final int dealerTotal) {
            // not used by StandInteractor
        }

        @Override
        public void presentResult(final StandOutputData outputData) {
            // not used by StandInteractor
            this.lastOutput = outputData;
        }

        @Override
        public void presentError(final String message) {
            // not used by StandInteractor
            this.lastErrorMessage = message;
        }

        @Override
        public void prepareSuccessView(StandOutputData outputData) {
            this.lastOutput = outputData;
        }

        @Override
        public void prepareFailView(String message) {
            this.lastErrorMessage = message;
        }
    }

    /**
     * Fake CurrentGame used only to trigger the Deck.UnableToLoadDeck path.
     * calculateScore returns low dealer total so the while-loop runs,
     * and addCardDealer immediately throws.
     */
    private static class FailingCurrentGame extends CurrentGame {

        FailingCurrentGame(User player) throws DeckApiInterface.UnableToLoadDeck {
            super(player);
        }

        @Override
        public int calculateScore(List<Card> hand) {
            // Ensure dealerTotal < 17 so the loop tries to draw.
            if (hand == getDealerHand()) {
                return 10;
            } else {
                return 15;
            }
        }

        @Override
        public void addCardDealer(int cardNumber, boolean faceUp) throws Deck.UnableToLoadDeck {
            throw new Deck.UnableToLoadDeck();
        }
    }

    /**
     * Fake CurrentGame that makes the dealer draw loop run once successfully:
     * - First dealer score < 17 (enter loop)
     * - After one draw, dealer score >= 17 (exit loop)
     * Player total is higher, so outcome is "Player wins".
     */
    private static class LoopCurrentGame extends CurrentGame {

        private int dealerScoreCallCount = 0;

        LoopCurrentGame(User player) throws DeckApiInterface.UnableToLoadDeck {
            super(player);
        }

        @Override
        public int calculateScore(List<Card> hand) {
            if (hand == getDealerHand()) {
                dealerScoreCallCount++;
                if (dealerScoreCallCount == 1) {
                    // first call: before loop, < 17
                    return 10;
                } else {
                    // second call: inside loop after addCardDealer, >= 17
                    return 18;
                }
            } else if (hand == getPlayerHand()) {
                // player beats dealer
                return 20;
            } else {
                return 0;
            }
        }

        @Override
        public void addCardDealer(int cardNumber, boolean faceUp) throws DeckApiInterface.UnableToLoadDeck {
            // no-op; we just need the loop to run without throwing
        }
    }
}
