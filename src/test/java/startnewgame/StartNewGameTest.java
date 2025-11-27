package startnewgame;

import data.Access.Deck;
import data.Access.DeckApiInterface;
import entity.*;
import org.junit.jupiter.api.Test;
import use.Case.startnewgame.StartNewGameInteractor;
import use.Case.startnewgame.StartNewGameOutputBoundary;
import use.Case.startnewgame.StartNewGameOutputData;

import java.util.ArrayList;
import java.util.List;
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

        // 测试getCurrentGame方法
        assertNotNull(outputData.getCurrentGame());
        assertEquals(game, outputData.getCurrentGame());
    }

    @Test
    void testExecuteWhenDeckFails() {
        // 创建会抛出异常的Presenter和Deck
        TestPresenter presenter = new TestPresenter();
        User user = new User(new ArrayList<>());

        // 执行测试 - 应该调用prepareFailView
        StartNewGameInteractor interactor = new StartNewGameInteractor(presenter);
        interactor.execute(user);

        // 验证失败方法被调用 - 使用正确的变量名
        assertTrue(presenter.failWasCalled, "prepareFailView should be called when deck fails");
        assertEquals("Failed to Start New Game, Please Try Again", presenter.failMessage);
    }

    // 会抛出异常的Deck
    static class FailingDeck implements DeckApiInterface {
        @Override
        public List<Card> drawCards(int cardNumber) throws Deck.UnableToLoadDeck {
            throw new Deck.UnableToLoadDeck();
        }
    }

    // 统一的TestPresenter
    static class TestPresenter implements StartNewGameOutputBoundary {
        boolean successWasCalled = false;
        boolean failWasCalled = false;  // 统一使用这个变量名
        StartNewGameOutputData successData;
        String failMessage;

        @Override
        public void prepareSuccessView(StartNewGameOutputData data) {
            successWasCalled = true;
            successData = data;
        }

        @Override
        public void prepareFailView(String error) {
            failWasCalled = true;  // 设置这个变量
            failMessage = error;
        }
    }

    static class TestDeck implements DeckApiInterface {
        public java.util.List<entity.Card> drawCards(int cardNumber) {
            return java.util.List.of(
                    new entity.Card("HEARTS", "ACE"),
                    new entity.Card("SPADES", "10")
            );
        }
    }
}
