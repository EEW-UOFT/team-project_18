import entity.Card;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import data_access.Deck;
import use_case.StandInputData;
import use_case.StandInteractor;
import use_case.StandOutputBoundary;
import use_case.StandOutputData;

import static org.junit.jupiter.api.Assertions.*;

class StandInteractorTest {

    /**
     * Fake deck that returns pre-programmed cards instead of calling the real API.
     * IMPORTANT: It extends Deck but does NOT override initializeNewDeck().
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
     * Simple presenter that stores everything for assertions.
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

        int playerTotal = 17; // user stands on 17

        // Dealer draws 10 → 17 total → stops → PUSH
        Card tenHearts = new Card("HEARTS", "10");
        Card sevenClubs = new Card("CLUBS", "7");

        FakeDeck deck = new FakeDeck(Arrays.asList(tenHearts, sevenClubs));

        RecordingPresenter presenter = new RecordingPresenter();
        StandInteractor interactor = new StandInteractor(presenter);

        StandInputData inputData = new StandInputData(deck, playerTotal);
        interactor.execute(inputData);

        // No errors expected
        assertNull(presenter.errorMessage);

        // Dealer should have drawn exactly 2 cards
        assertEquals(2, presenter.dealerDrawEvents.size());

        // Final result should be a push
        assertNotNull(presenter.finalOutput);
        assertEquals("Push", presenter.finalOutput.getOutcome());
        assertEquals(17, presenter.finalOutput.getPlayerTotal());
        assertEquals(17, presenter.finalOutput.getDealerTotal());
    }
}
