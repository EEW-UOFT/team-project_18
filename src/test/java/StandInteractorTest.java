import data_access.DeckAPIInterface;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import data_access.Deck;
import entity.Card;
import use_case.StandInputData;
import use_case.StandInteractor;
import use_case.StandOutputBoundary;
import use_case.StandOutputData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class StandInteractorTest {

    /**
     * Fake deck that returns pre-programmed cards instead of calling the real API.
     * IMPORTANT: it EXTENDS Deck so we can pass it where a Deck is required.
     */
    private static class FakeDeck extends Deck {
        private final List<Card> scriptedCards;
        private int index = 0;

        FakeDeck(List<Card> scriptedCards) {
            this.scriptedCards = new ArrayList<>(scriptedCards);
        }

        @Override
        public List<Card> drawCards(int n) throws DeckAPIInterface.UnableToLoadDeck {
            List<Card> result = new ArrayList<>();
            for (int i = 0; i < n && index < scriptedCards.size(); i++) {
                result.add(scriptedCards.get(index++));
            }
            return result;
        }

        // we don’t need initializeNewDeck or getDeckID in this fake
        // so we just leave the inherited behaviour alone and never call them
    }

    /**
     * Presenter that records what the interactor told the UI.
     */
    private static class RecordingPresenter implements StandOutputBoundary {

        private final List<String> dealerDrawLog = new ArrayList<>();
        private StandOutputData finalOutput;
        private String errorMessage;

        @Override
        public void presentDealerDrew(Card card, int dealerTotal) {
            dealerDrawLog.add(card.getValue() + " of " + card.getSuit()
                    + " -> dealer total " + dealerTotal);
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
    void dealerHitsUntilAtLeast17AndPushesOnEqualTotals() {
        // Player stands on 17
        int playerTotal = 17;

        // Dealer will draw 10 then 7 => total 17 (so it should be a push)
        Card tenHearts = new Card("HEARTS", "10");
        Card sevenClubs = new Card("CLUBS", "7");

        // NOTE: variable type is Deck, instance is FakeDeck (polymorphism)
        Deck deck = new FakeDeck(Arrays.asList(tenHearts, sevenClubs));

        RecordingPresenter presenter = new RecordingPresenter();
        StandInteractor interactor = new StandInteractor(presenter);

        StandInputData inputData = new StandInputData(deck, playerTotal);

        // run the use case
        interactor.execute(inputData);

        // 1) no error
        assertNull(presenter.errorMessage);

        // 2) dealer actually drew multiple times (logged 2 draws)
        assertEquals(2, presenter.dealerDrawLog.size());

        // 3) final result is a push with correct totals
        assertNotNull(presenter.finalOutput);
        assertEquals("Push", presenter.finalOutput.getOutcome());
        assertEquals(playerTotal, presenter.finalOutput.getPlayerTotal());
        assertEquals(17, presenter.finalOutput.getDealerTotal());
    }
}
