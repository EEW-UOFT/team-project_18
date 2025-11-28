package stand;

import entity.Card;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import data.Access.Deck;
import data.Access.DeckApiInterface;
import use.Case.stand.StandInputData;
import use.Case.stand.StandInteractor;
import use.Case.stand.StandOutputBoundary;
import use.Case.stand.StandOutputData;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the StandInteractor use case.
 */
public class StandInteractorTest {

    /**
     * Fake deck that returns scripted cards instead of calling the real API.
     */
    private static class FakeDeck extends Deck {

        private final List<Card> scriptedCards;
        private int index = 0;

        FakeDeck(final List<Card> scriptedCards) throws UnableToLoadDeck {
            super();
            this.scriptedCards = new ArrayList<>(scriptedCards);
        }

        @Override
        public String initializeNewDeck() {
            // Not used in these tests.
            return "FAKE_DECK_ID";
        }

        @Override
        public List<Card> drawCards(final int cardNumber)
                throws DeckApiInterface.UnableToLoadDeck {
            List<Card> result = new ArrayList<>();
            for (int i = 0; i < cardNumber && index < scriptedCards.size(); i++, index++) {
                result.add(scriptedCards.get(index));
            }
            return result;
        }
    }

    /**
     * Deck that always throws UnableToLoadDeck.
     */
    private static class ErrorDeck extends Deck {

        public ErrorDeck() throws UnableToLoadDeck {
        }

        @Override
        public String initializeNewDeck() throws DeckApiInterface.UnableToLoadDeck {
            throw new DeckApiInterface.UnableToLoadDeck();
        }

        @Override
        public List<Card> drawCards(final int cardNumber)
                throws DeckApiInterface.UnableToLoadDeck {
            throw new DeckApiInterface.UnableToLoadDeck();
        }
    }

    /**
     * Deck that always returns an empty list on drawCards.
     */
    private static class EmptyDeck extends Deck {

        public EmptyDeck() throws UnableToLoadDeck {
        }

        @Override
        public String initializeNewDeck() {
            return "EMPTY_DECK";
        }

        @Override
        public List<Card> drawCards(final int cardNumber)
                throws DeckApiInterface.UnableToLoadDeck {
            return Collections.emptyList();
        }
    }

    /**
     * Presenter that records what the interactor sends.
     */
    private static class RecordingPresenter implements StandOutputBoundary {

        final List<String> dealerDrawEvents = new ArrayList<>();
        StandOutputData finalOutput;
        String errorMessage;

        @Override
        public void presentDealerDrew(final Card card, final int dealerTotal) {
            dealerDrawEvents.add(card.getValue() + " of " + card.getSuit()
                    + " | dealer total = " + dealerTotal);
        }

        @Override
        public void presentResult(final StandOutputData outputData) {
            this.finalOutput = outputData;
        }

        @Override
        public void presentError(final String message) {
            this.errorMessage = message;
        }
    }

    @Test
    void dealerReaches17AndPushWhenTotalsEqual()
            throws DeckApiInterface.UnableToLoadDeck {

        int playerTotal = 17;

        Card tenHearts = new Card("HEARTS", "10");
        Card sevenClubs = new Card("CLUBS", "7");

        Deck deck = new FakeDeck(Arrays.asList(tenHearts, sevenClubs));
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
    void dealerWinsWhenHigherTotalAndNoBust()
            throws DeckApiInterface.UnableToLoadDeck {

        int playerTotal = 16;

        // Dealer draws 9 + 10 = 19
        Card nineHearts = new Card("HEARTS", "9");
        Card tenClubs = new Card("CLUBS", "10");

        Deck deck = new FakeDeck(Arrays.asList(nineHearts, tenClubs));
        RecordingPresenter presenter = new RecordingPresenter();
        StandInteractor interactor = new StandInteractor(presenter);

        StandInputData inputData = new StandInputData(deck, playerTotal);
        interactor.execute(inputData);

        assertNull(presenter.errorMessage);
        assertNotNull(presenter.finalOutput);

        assertEquals(16, presenter.finalOutput.getPlayerTotal());
        assertEquals(19, presenter.finalOutput.getDealerTotal());
        assertEquals("Dealer wins", presenter.finalOutput.getOutcome());
        assertFalse(presenter.dealerDrawEvents.isEmpty());
    }

    @Test
    void playerBusts_DealerWinsImmediately()
            throws DeckApiInterface.UnableToLoadDeck {

        int playerTotal = 22;  // already bust

        Card tenHearts = new Card("HEARTS", "10");
        Card sevenClubs = new Card("CLUBS", "7");

        Deck deck = new FakeDeck(Arrays.asList(tenHearts, sevenClubs));
        RecordingPresenter presenter = new RecordingPresenter();
        StandInteractor interactor = new StandInteractor(presenter);

        StandInputData inputData = new StandInputData(deck, playerTotal);
        interactor.execute(inputData);

        assertNull(presenter.errorMessage);
        assertNotNull(presenter.finalOutput);

        assertEquals(22, presenter.finalOutput.getPlayerTotal());
        assertEquals(17, presenter.finalOutput.getDealerTotal());
        assertEquals("Dealer wins (player busts)", presenter.finalOutput.getOutcome());
    }

    @Test
    void dealerBusts_PlayerWins()
            throws DeckApiInterface.UnableToLoadDeck {

        int playerTotal = 18;

        // Dealer draws: 5 + KING + KING = 25
        Card fiveHearts = new Card("HEARTS", "5");
        Card kingClubs = new Card("CLUBS", "KING");
        Card kingSpades = new Card("SPADES", "KING");

        Deck deck =
                new FakeDeck(Arrays.asList(fiveHearts, kingClubs, kingSpades));
        RecordingPresenter presenter = new RecordingPresenter();
        StandInteractor interactor = new StandInteractor(presenter);

        StandInputData inputData = new StandInputData(deck, playerTotal);
        interactor.execute(inputData);

        assertNull(presenter.errorMessage);
        assertNotNull(presenter.finalOutput);

        assertEquals(18, presenter.finalOutput.getPlayerTotal());
        assertEquals(25, presenter.finalOutput.getDealerTotal());
        assertEquals("Player wins (dealer busts)", presenter.finalOutput.getOutcome());
        assertEquals(3, presenter.dealerDrawEvents.size());
    }

    @Test
    void apiErrorResultsInPresenterErrorMessage()
            throws DeckApiInterface.UnableToLoadDeck {

        int playerTotal = 17;

        Deck deck = new ErrorDeck();
        RecordingPresenter presenter = new RecordingPresenter();
        StandInteractor interactor = new StandInteractor(presenter);

        StandInputData inputData = new StandInputData(deck, playerTotal);
        interactor.execute(inputData);

        assertNull(presenter.finalOutput);
        assertNotNull(presenter.errorMessage);
        assertTrue(presenter.errorMessage.startsWith("Failed to draw from deck")
                || presenter.errorMessage.startsWith("Could not draw from deck"));
    }

    @Test
    void emptyDrawResultsInErrorAndNoResult()
            throws DeckApiInterface.UnableToLoadDeck {

        int playerTotal = 17;

        Deck deck = new EmptyDeck();
        RecordingPresenter presenter = new RecordingPresenter();
        StandInteractor interactor = new StandInteractor(presenter);

        StandInputData inputData = new StandInputData(deck, playerTotal);
        interactor.execute(inputData);

        assertNull(presenter.finalOutput);
        assertNotNull(presenter.errorMessage);
        assertEquals("Deck returned no cards.", presenter.errorMessage);
    }
}
