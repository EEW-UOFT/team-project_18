package restartgame;

import entity.CurrentGame;
import entity.User;
import org.junit.jupiter.api.Test;
import use_case.restartgame.RestartGameInteractor;
import use_case.restartgame.RestartGameOutputBoundary;
import use_case.restartgame.RestartGameOutputData;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class RestartGameTest {

    @Test
    void testExecuteSuccess() {
        // given
        TestPresenter presenter = new TestPresenter();
        User user = new User(new ArrayList<>());

        RestartGameInteractor interactor = new RestartGameInteractor(presenter);

        // when
        interactor.execute(user);

        // then
        assertTrue(presenter.successWasCalled);
        assertNotNull(presenter.successData);
        assertNotNull(presenter.successData.getCurrentGame());
    }

    @Test
    void testOutputDataCreation() throws Exception {
        // given
        User user = new User(new ArrayList<>());
        CurrentGame game = new CurrentGame(user);

        // when
        RestartGameOutputData outputData = new RestartGameOutputData(game);

        // then
        assertNotNull(outputData.getCurrentGame());
        assertEquals(game, outputData.getCurrentGame());
    }

    /**
     * Simple presenter used for testing the interactor.
     */
    static class TestPresenter implements RestartGameOutputBoundary {
        boolean successWasCalled = false;
        boolean failWasCalled = false;
        RestartGameOutputData successData;
        String failMessage;

        @Override
        public void prepareSuccessView(RestartGameOutputData data) {
            successWasCalled = true;
            successData = data;
        }

        @Override
        public void prepareFailView(String error) {
            failWasCalled = true;
            failMessage = error;
        }
    }
}

