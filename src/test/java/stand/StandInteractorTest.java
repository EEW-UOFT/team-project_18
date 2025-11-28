package stand;

import entity.Card;
import org.junit.jupiter.api.Test;
import use.Case.stand.StandInteractor;
import use.Case.stand.StandOutputBoundary;
import use.Case.stand.StandOutputData;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link StandInteractor}.
 *
 * The production StandInteractor currently creates its own Deck instance
 * internally, which makes it hard to deterministically control dealer draws
 * from tests without modifying production code. To keep the tests isolated
 * from the external Deck of Cards API and still exercise meaningful logic in
 * StandInteractor, these tests focus on the pure, internal card–value
 * calculation logic via reflection.
 */
public class StandInteractorTest {

    /**
     * Minimal presenter implementation so we can instantiate {@link StandInteractor}
     * without depending on the UI.
     */
    private static class DummyPresenter implements StandOutputBoundary {

        StandOutputData lastOutput;
        String lastErrorMessage;

        @Override
        public void presentDealerDrew(final Card card, final int dealerTotal) {
            // not needed for these tests
        }

        @Override
        public void presentResult(final StandOutputData outputData) {
            this.lastOutput = outputData;
        }

        @Override
        public void presentError(final String message) {
            this.lastErrorMessage = message;
        }
    }

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

    // ---------- EXTRA TESTS FOR MORE COVERAGE ----------

    @Test
    void tenCardIsWorthTen() throws Exception {
        Card tenDiamonds = new Card("DIAMONDS", "10", "url");
        assertEquals(10, invokeCardValue(tenDiamonds));
    }

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
}
