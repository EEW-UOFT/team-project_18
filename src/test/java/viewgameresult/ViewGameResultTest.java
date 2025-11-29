package viewgameresult;

import data.Access.DeckApiInterface;
import entity.Card;
import entity.CurrentGame;
import entity.User;
import interfaceadapter.ViewManagerModel;
import interfaceadapter.viewgameresult.ViewGameResultController;
import interfaceadapter.viewgameresult.ViewGameResultPresenter;
import interfaceadapter.viewgameresult.ViewGameResultViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use.Case.viewgameresult.ViewGameResultInteractor;
import use.Case.viewgameresult.ViewGameResultOutputBoundary;
import use.Case.viewgameresult.ViewGameResultOutputData;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ViewGameResultTest {

    private ViewGameResultViewModel viewModel;
    private ViewGameResultPresenter presenter;
    private ViewGameResultInteractor interactor;
    private ViewGameResultController controller;

    @BeforeEach
    void setUp() throws DeckApiInterface.UnableToLoadDeck {
        viewModel = new ViewGameResultViewModel();
        presenter = new ViewGameResultPresenter(viewModel, new ViewManagerModel());
        interactor = new ViewGameResultInteractor(presenter);
        controller = new ViewGameResultController(interactor);
    }

    @Test
    void testExecuteWhenGameOver() throws DeckApiInterface.UnableToLoadDeck {
        User user = new User(new ArrayList<>());

        CurrentGame game = new CurrentGame(user);
        game.addSingleCardPlayer(new Card("HEARTS", "10"));
        game.addSingleCardPlayer(new Card("SPADES", "KING"));
        game.addSingleCardDealer(new Card("CLUBS", "9"));
        game.addSingleCardDealer(new Card("DIAMONDS", "8"));
        game.gameWon();

        controller.execute(game);

        assertSame(viewModel.getCurrentGame(), game);
        assertEquals("Player Won", viewModel.getGameResult());
        assertEquals(20, viewModel.getPlayerScore());
        assertEquals(17, viewModel.getDealerScore());
    }

    @Test
    void testExecuteWhenGameOngoing() throws DeckApiInterface.UnableToLoadDeck {
        User user = new User(new ArrayList<>());
        CurrentGame game = new CurrentGame(user);

        // Game is still ongoing
        // Should throw an exception
        assertThrows(IllegalStateException.class, () -> {
            controller.execute(game);
        });
    }
}