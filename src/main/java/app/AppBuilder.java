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
import interfaceadapter.restartgame.RestartGameController;
import interfaceadapter.restartgame.RestartGamePresenter;
import interfaceadapter.restartgame.RestartGameViewModel;
import use.Case.restartgame.RestartGameInputBoundary;
import use.Case.restartgame.RestartGameInteractor;
import use.Case.restartgame.RestartGameOutputBoundary;


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

    private RestartGameController restartGameController;
    private RestartGameViewModel restartGameViewModel;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
        addStartNewGameUseCase();
        addRestartGameUseCase();
        addViewGameResultUseCase();
    }

    public AppBuilder addStartNewGameUseCase() {
        final StartNewGameViewModel viewModel = new StartNewGameViewModel();
        final StartNewGameOutputBoundary presenter = new StartNewGamePresenter(viewModel, viewManagerModel);

        final StartNewGameInputBoundary interactor = new StartNewGameInteractor(presenter);
        final ArrayList<HistoryEntry> history = new ArrayList<>();

        this.startNewGameController = new StartNewGameController(interactor, new User(history));
        this.startNewGameViewModel = viewModel;

        return this;
    }

    public AppBuilder addRestartGameUseCase() {
        final RestartGameViewModel viewModel = new RestartGameViewModel();
        final RestartGameOutputBoundary presenter = new RestartGamePresenter(viewModel, viewManagerModel);

        final RestartGameInputBoundary interactor = new RestartGameInteractor(presenter);
        final ArrayList<HistoryEntry> history = new ArrayList<>();

        this.restartGameController = new RestartGameController(interactor, new User(history));
        this.restartGameViewModel = viewModel;

        return this;
    }

    public AppBuilder addViewGameResultUseCase() {
        final ViewGameResultViewModel viewModel = new ViewGameResultViewModel();
        final ViewGameResultPresenter presenter = new ViewGameResultPresenter(viewModel, viewManagerModel);
        final ViewGameResultInteractor viewGameResultInteractor = new ViewGameResultInteractor(presenter);

        this.viewGameResultController = new ViewGameResultController(viewGameResultInteractor);
        this.viewGameResultViewModel = viewModel;

        return this;
    }

    public AppBuilder addHomePageView() {
        final HomePageView homePage = new HomePageView(startNewGameViewModel, startNewGameController);
        cardPanel.add(homePage, "Home");
        return this;
    }

    public AppBuilder addBlackJackGameView() throws IOException {
        final BlackJackGameView blackJackGameView = new BlackJackGameView(viewGameResultController, startNewGameViewModel);
        cardPanel.add(blackJackGameView, "Game");
        return this;
    }

    public AppBuilder addGameResultView() {
        GameResultView gameResultView = new GameResultView(viewGameResultViewModel, restartGameController);
        cardPanel.add(gameResultView, "GameResult");
        return this;
    }

    public AppBuilder setupViewManager() {
        viewManagerModel.addPropertyChangeListener(evt -> {
            if ("view".equals(evt.getPropertyName())) {
                final String activeView = (String) evt.getNewValue();
                cardLayout.show(cardPanel, activeView);
            }
        });
        return this;
    }

    public JFrame build() {
        final JFrame frame = new JFrame("BlackJack Game");

        frame.add(cardPanel);
        frame.setSize(FRAME_WIDTH, FRAME_LENGTH);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        viewManagerModel.setActiveView("Home");

        return frame;
    }
}
