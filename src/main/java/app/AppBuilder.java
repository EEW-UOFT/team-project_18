package app;

import java.awt.CardLayout;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import entity.HistoryEntry;
import entity.User;
import interfaceadapter.ViewManagerModel;
import interfaceadapter.startnewgame.StartNewGameController;
import interfaceadapter.startnewgame.StartNewGamePresenter;
import interfaceadapter.startnewgame.StartNewGameViewModel;
import use.Case.startnewgame.StartNewGameInputBoundary;
import use.Case.startnewgame.StartNewGameInteractor;
import use.Case.startnewgame.StartNewGameOutputBoundary;
import use.Case.viewgameresult.ViewGameResultInteractor;
import interfaceadapter.viewgameresult.ViewGameResultController;
import interfaceadapter.viewgameresult.ViewGameResultPresenter;
import interfaceadapter.viewgameresult.ViewGameResultViewModel;

import view.BlackJackGameView;
import view.GameResultView;
import view.HomePageView;

public class AppBuilder {

    public static final int FRAME_LENGTH = 600;
    public static final int FRAME_WIDTH = 800;

    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();

    private StartNewGameController startNewGameController;
    private StartNewGameViewModel startNewGameViewModel;

    private ViewGameResultController viewGameResultController;
    private ViewGameResultViewModel viewGameResultViewModel;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    /**
     * Adds the Start New Game Use Case to the application.
     *
     * @return this builder
     */
    public AppBuilder addStartNewGameUseCase() {
        final StartNewGameViewModel viewModel = new StartNewGameViewModel();
        final StartNewGameOutputBoundary presenter = new StartNewGamePresenter(viewModel, viewManagerModel);

        final StartNewGameInputBoundary interactor = new StartNewGameInteractor(presenter);
        final ArrayList<HistoryEntry> history = new ArrayList<>();

        this.startNewGameController = new StartNewGameController(interactor, new User(history));
        this.startNewGameViewModel = viewModel;
        return this;
    }

    /**
     * Adds the Home Page View to the application.
     *
     * @return this builder
     */
    public AppBuilder addHomePageView() {
        final HomePageView homePageView = new HomePageView(startNewGameViewModel, startNewGameController);
        cardPanel.add(homePageView, "Home");
        return this;
    }

    public AppBuilder addViewGameResultUseCase() {
        final ViewGameResultViewModel viewModel = new ViewGameResultViewModel();
        final ViewGameResultPresenter presenter = new ViewGameResultPresenter(viewModel, viewManagerModel);
        final ViewGameResultInteractor viewGameResultInteractor = new ViewGameResultInteractor(presenter);

        this.viewGameResultController = new ViewGameResultController(viewGameResultInteractor);
        this.viewGameResultViewModel = viewModel;

        final GameResultView gameResultView = new GameResultView(viewGameResultViewModel);
        cardPanel.add(gameResultView, "Result");
        return this;
    }

    /**
     * Adds the BlackJack Game View to the application.
     * @return this builder
     * @throws IOException if there's an IO error during adding
     */
    public AppBuilder addBlackJackGameView() throws IOException {
        final BlackJackGameView blackJackGameView = new BlackJackGameView(viewGameResultController, startNewGameViewModel);
        cardPanel.add(blackJackGameView, "Game");
        return this;
    }

    /**
     * Sets up the view manager to handle view switching.
     * @return this builder
     */
    public AppBuilder setupViewManager() {
        viewManagerModel.addPropertyChangeListener(evt -> {
            if ("view".equals(evt.getPropertyName())) {
                final String activeView = (String) evt.getNewValue();
                cardLayout.show(cardPanel, activeView);
            }
        });
        return this;
    }

    /**
     * Builds and returns the complete application frame.
     * @return the built JFrame
     */
    public JFrame build() {
        final JFrame frame = new JFrame("BlackJack Game");

        frame.add(cardPanel);
        frame.setSize(FRAME_WIDTH, FRAME_LENGTH);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        viewManagerModel.setActiveView("Home");

        return frame;
    }
}
