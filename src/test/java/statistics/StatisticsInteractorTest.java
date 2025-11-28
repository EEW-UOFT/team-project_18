package statistics;

import entity.HistoryEntry;
import entity.User;
import org.junit.jupiter.api.Test;
import use_case.statistics.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StatisticsInteractorTest {

    @Test
    void testExecuteWithHistory() {
        List<HistoryEntry> history = new ArrayList<>();
        history.add(new HistoryEntry(1, 21, 18, "Player Won"));
        history.add(new HistoryEntry(2, 20, 19, "Player Won"));
        history.add(new HistoryEntry(3, 19, 18, "Player Won"));
        history.add(new HistoryEntry(4, 18, 20, "Dealer Won"));
        history.add(new HistoryEntry(5, 19, 19, "TIE"));

        User user = new User(history);

        TestPresenter presenter = new TestPresenter();
        StatisticsInteractor interactor = new StatisticsInteractor(presenter);

        interactor.execute(user);

        assertTrue(presenter.successCalled);
        assertEquals(5, presenter.data.getTotalGames());
        assertEquals(3, presenter.data.getWins());
        assertEquals(1, presenter.data.getLosses());
        assertEquals(1, presenter.data.getTies());
        assertEquals(3, presenter.data.getLongestWinStreak());
    }

    @Test
    void testExecuteNoHistory() {
        User user = new User(new ArrayList<>());

        TestPresenter presenter = new TestPresenter();
        StatisticsInteractor interactor = new StatisticsInteractor(presenter);

        interactor.execute(user);

        assertFalse(presenter.successCalled);
        assertTrue(presenter.failCalled);
        assertEquals("No statistics available yet.", presenter.failMessage);
    }

    static class TestPresenter implements StatisticsOutputBoundary {
        boolean successCalled = false;
        boolean failCalled = false;
        StatisticsOutputData data;
        String failMessage;

        @Override
        public void prepareSuccessView(StatisticsOutputData outputData) {
            successCalled = true;
            data = outputData;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            failCalled = true;
            failMessage = errorMessage;
        }
    }
}
