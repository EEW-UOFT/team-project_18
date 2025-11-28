package startnewgame;

import data.Access.DeckApiInterface;
import entity.CurrentGame;
import entity.User;
import org.junit.jupiter.api.Test;
import use.Case.startnewgame.StartNewGameInteractor;
import use.Case.startnewgame.StartNewGameOutputBoundary;
import use.Case.startnewgame.StartNewGameOutputData;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class StartNewGameTest {

    @Test
    void testExecuteSuccess() {

        TestPresenter presenter = new TestPresenter();
        User user = new User(new ArrayList<>());

        StartNewGameInteractor interactor = new StartNewGameInteractor(presenter);
        interactor.execute(user);

        assertTrue(presenter.successWasCalled);
    }

    @Test
    void testOutputDataCreation() throws DeckApiInterface.UnableToLoadDeck {
        User user = new User(new ArrayList<>());
        CurrentGame game = new CurrentGame(user);

        StartNewGameOutputData outputData = new StartNewGameOutputData(game);

        assertNotNull(outputData.getCurrentGame());
        assertEquals(game, outputData.getCurrentGame());
    }

    @Test
    void testExecuteWhenDeckFails() {
        TestPresenter presenter = new TestPresenter();

        presenter.prepareFailView("Failed to Start New Game, Please Try Again");

        assertTrue(presenter.failWasCalled);
        assertEquals("Failed to Start New Game, Please Try Again", presenter.failMessage);
    }

    static class TestPresenter implements StartNewGameOutputBoundary {
        boolean successWasCalled = false;
        boolean failWasCalled = false;
        StartNewGameOutputData successData;
        String failMessage;

        @Override
        public void prepareSuccessView(StartNewGameOutputData data) {
            successWasCalled = true;
            successData = data;
        }

        @Override
        public void prepareFailView(String error) {
            failWasCalled = true;  //
            failMessage = error;
        }
    }
}
