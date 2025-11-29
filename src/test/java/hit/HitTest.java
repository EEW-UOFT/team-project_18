package hit;


import data.Access.DeckApiInterface;
import entity.CurrentGame;
import entity.User;
import interfaceadapter.GameModel;
import interfaceadapter.hit.ConsoleHitPresenter;
import org.junit.Test;
import use.Case.hit.HitInputBoundary;
import use.Case.hit.HitInteractor;
import use.Case.hit.HitOutputBoundary;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class HitTest {

    @Test
    public void hitTest() throws DeckApiInterface.UnableToLoadDeck {
        User dummyUser = new User(new ArrayList<>());
        CurrentGame dummyGame = new CurrentGame(dummyUser);
        GameModel dummyGameModel = new GameModel(dummyGame);
        HitOutputBoundary presenter = new ConsoleHitPresenter(dummyGameModel);
        HitInputBoundary interactor = new HitInteractor(presenter);

        interactor.execute(dummyGame);
        assertEquals(1, dummyGame.getPlayerHand().size());
        interactor.execute(dummyGame);
        assertEquals(2, dummyGame.getPlayerHand().size());
    }
}
