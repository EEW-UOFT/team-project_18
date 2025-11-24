import entity.Card;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import data_access.Deck;
import data_access.DeckAPIInterface;
import use_case.StandInputData;
import use_case.StandInteractor;
import use_case.StandOutputBoundary;
import use_case.StandOutputData;

import static org.junit.jupiter.api.Assertions.*;

class StandInteractorTest {

    /**
     * Fake deck that returns pre-programmed cards instead of calling the real API.
     */
    private static class FakeDeck extends Deck {

        private final List<Card> scriptedCards;
        private int index = 0;

        FakeDeck(List<Card> scriptedCards) {
            this.scriptedCards = new ArrayList<>(scriptedCards);
        }

        @Override
        public List<Card> drawCards(int n) {
            List<Card> result = new ArrayList<>();

            for (int i = 0; i < n && index < scriptedCards.size(); i++, index++) {
                result.add(scriptedCards.get(index));
            }
            return result;
        }
    }

    /**
     * Deck that always throws an API error, to test the error path.
     */
    private static class ErrorDeck extends Deck {

        @Override
        public List<Card> drawCards(int n) throws DeckAPIInterface.UnableToLoadDeck {
            throw new DeckAPIInterface.UnableToLoadDeck("Simulated API failure");
        }
    }

    /**
     * Presenter that records what the interactor sends so we can assert on it.
     */
    private static class RecordingPresenter implements StandOutputBoundary {

        final List<String> dealerDrawEvents = new ArrayList<>();
        StandOutputData finalOutput;
        String errorMessage;

        @Override
        public void presentDealerDrew(Card card, int dealerTotal) {
            dealerDrawEvents.add(card.getValue() + " of " + card.getSuit()
                    + " | dealer total = " + dealerTotal);
        }

        @Override
        public void presentResult(StandOutputData outputData) {
            this.finalOutput = outputData;
        }

        @Override
        public void presentError(String message) {
            this.errorMessage = message;
        }
    }

    @Test
    void dealerHitsUntilAtLeast17_AndResultIsPushWhenTotalsEqual() {
        int playerTotal = 17;

        // Dealer draws 10 then 7 => 17 → PUSH
        Card tenHearts = new Card("HEARTS", "10");
        Card sevenClubs = new Card("CLUBS", "7");

        FakeDeck deck = new FakeDeck(Arrays.asList(tenHearts, sevenClubs));
        RecordingPresenter presenter = new RecordingPresenter();
        StandInteractor interactor = new StandInteractor(presenter);

        StandInputData inputData = new StandInputData(deck, playerTotal);
        interactor.execute(inputData);

        assertNull(presenter.errorMessage);
        assertNotNull(presenter.finalOutput);

        assertEquals(17, presenter.finalOutput.getPlayerTotal());
        assertEquals(17, presenter.finalOutput.getDealerTotal());
        assertEquals("Push", presenter.finalOutput.getOutcome());
    }



    @Test
    void dealerHigherTotal_DealerWins() {
        int playerTotal = 16;

        // Dealer: 10 + 8 = 18 (> player, <=21)
        Card tenHearts = new Card("HEARTS", "10");
        Card eightSpades = new Card("SPADES", "8");

        FakeDeck deck = new FakeDeck(Arrays.asList(tenHearts, eightSpades));
        RecordingPresenter presenter = new RecordingPresenter();
        StandInteractor interactor = new StandInteractor(presenter);

        StandInputData inputData = new StandInputData(deck, playerTotal);
        interactor.execute(inputData);

        assertNull(presenter.errorMessage);
        assertNotNull(presenter.finalOutput);

        assertEquals(16, presenter.finalOutput.getPlayerTotal());
        assertEquals(18, presenter.finalOutput.getDealerTotal());

        String outcome = presenter.finalOutput.getOutcome().toLowerCase();
        assertTrue(outcome.contains("dealer"), "Expected dealer to be the winner");
    }

    @Test
    void playerHigherTotal_PlayerWins() {
        int playerTotal = 19;

        // Dealer: 10 + 7 = 17 (< player, <=21)
        Card tenHearts = new Card("HEARTS", "10");
        Card sevenClubs = new Card("CLUBS", "7");

        FakeDeck deck = new FakeDeck(Arrays.asList(tenHearts, sevenClubs));
        RecordingPresenter presenter = new RecordingPresenter();
        StandInteractor interactor = new StandInteractor(presenter);

        StandInputData inputData = new StandInputData(deck, playerTotal);
        interactor.execute(inputData);

        assertNull(presenter.errorMessage);
        assertNotNull(presenter.finalOutput);

        assertEquals(19, presenter.finalOutput.getPlayerTotal());
        assertEquals(17, presenter.finalOutput.getDealerTotal());

        String outcome = presenter.finalOutput.getOutcome().toLowerCase();
        assertFalse(outcome.contains("dealer wins"),
                "Dealer should not be declared winner when player has higher total");
    }

    @Test
    void playerAlreadyBusts_DealerWins() {
        int playerTotal = 22; // bust

        // Dealer still draws to at least 17, but branch should be "player bust"
        Card tenHearts = new Card("HEARTS", "10");
        Card sevenClubs = new Card("CLUBS", "7");

        FakeDeck deck = new FakeDeck(Arrays.asList(tenHearts, sevenClubs));
        RecordingPresenter presenter = new RecordingPresenter();
        StandInteractor interactor = new StandInteractor(presenter);

        StandInputData inputData = new StandInputData(deck, playerTotal);
        interactor.execute(inputData);

        assertNull(presenter.errorMessage);
        assertNotNull(presenter.finalOutput);

        assertEquals(22, presenter.finalOutput.getPlayerTotal());

        String outcome = presenter.finalOutput.getOutcome().toLowerCase();
        assertTrue(outcome.contains("dealer"), "Expected dealer to win when player busts");
    }

    @Test
    void apiFailure_PresenterShowsError() {
        int playerTotal = 17;

        ErrorDeck deck = new ErrorDeck();
        RecordingPresenter presenter = new RecordingPresenter();
        StandInteractor interactor = new StandInteractor(presenter);

        StandInputData inputData = new StandInputData(deck, playerTotal);
        interactor.execute(inputData);

        assertNull(presenter.finalOutput, "No result should be set when an error occurs");
        assertNotNull(presenter.errorMessage, "Error message should be set on API failure");
    }
}

