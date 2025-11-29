package app;

import entity.HistoryEntry;
import entity.User;
import interfaceadapter.GameModel;
import interfaceadapter.ViewManagerModel;
import interfaceadapter.hit.ConsoleHitPresenter;
import interfaceadapter.hit.HitController;
import interfaceadapter.restartgame.RestartGameController;
import interfaceadapter.restartgame.RestartGamePresenter;
import interfaceadapter.restartgame.RestartGameViewModel;
import interfaceadapter.startnewgame.StartNewGameController;
import interfaceadapter.startnewgame.StartNewGamePresenter;
import interfaceadapter.startnewgame.StartNewGameViewModel;
import interfaceadapter.statistics.StatisticsController;
import interfaceadapter.statistics.StatisticsPresenter;
import interfaceadapter.statistics.StatisticsViewModel;
import interfaceadapter.viewgamehistory.ViewHistoryController;
import interfaceadapter.viewgamehistory.ViewHistoryPresenter;
import interfaceadapter.viewgamehistory.ViewHistoryViewModel;
import interfaceadapter.viewgameresult.ViewGameResultController;
import interfaceadapter.viewgameresult.ViewGameResultPresenter;
import interfaceadapter.viewgameresult.ViewGameResultViewModel;
import interfaceadapter.stand.ConsoleStandPresenter;
import interfaceadapter.stand.StandController;
import use.Case.hit.HitInputBoundary;
import use.Case.hit.HitInteractor;
import use.Case.hit.HitOutputBoundary;
import use.Case.restartgame.RestartGameInputBoundary;
import use.Case.restartgame.RestartGameInteractor;
import use.Case.restartgame.RestartGameOutputBoundary;
import use.Case.startnewgame.StartNewGameInputBoundary;
import use.Case.startnewgame.StartNewGameInteractor;
import use.Case.startnewgame.StartNewGameOutputBoundary;
import use.Case.statistics.StatisticsInputBoundary;
import use.Case.statistics.StatisticsInteractor;
import use.Case.statistics.StatisticsOutputBoundary;
import use.Case.viewgameresult.ViewGameResultInteractor;
import use.Case.stand.StandInputBoundary;
import use.Case.stand.StandInteractor;
import use.Case.stand.StandOutputBoundary;
import use.Case.viewhistory.ViewHistoryInputBoundary;
import use.Case.viewhistory.ViewHistoryInteractor;
import use.Case.viewhistory.ViewHistoryOutputBoundary;
import view.BlackJackGameView;
import view.GameHistoryView;
import view.GameResultView;
import view.HomePageView;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

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

    private ViewHistoryController viewHistoryController;
    private ViewHistoryViewModel viewHistoryViewModel;
    private GameHistoryView historyView;

    private RestartGameController restartGameController;
    private RestartGameViewModel restartGameViewModel;

    private StatisticsController statisticsController;
    private StatisticsViewModel statisticsViewModel;

    private HitController hitController;

    private StandController standController;

    private GameModel gameModel;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
        addStartNewGameUseCase();
        addHitUseCase();
        addStandUseCase();
        addRestartGameUseCase();
        addViewGameResultUseCase();
        addStatisticsUseCase();
        addStandUseCase();
        addViewHistoryUseCase();

        addHomePageView();

        try {
            addBlackJackGameView();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        addGameResultView();
        addGameHistoryView();

        setupViewManager();

    }

    public AppBuilder addStartNewGameUseCase() {
        final StartNewGameViewModel viewModel = new StartNewGameViewModel();
        final StartNewGameOutputBoundary presenter =
                new StartNewGamePresenter(viewModel, viewManagerModel);

        final StartNewGameInputBoundary interactor =
                new StartNewGameInteractor(presenter);
        final ArrayList<HistoryEntry> history = new ArrayList<>();

        this.startNewGameController =
                new StartNewGameController(interactor, new User(history));
        this.startNewGameViewModel = viewModel;

        return this;
    }

    public AppBuilder addHitUseCase() {
        this.gameModel = new GameModel();
        HitOutputBoundary presenter = new ConsoleHitPresenter(gameModel);
        HitInputBoundary interactor = new HitInteractor(presenter);

        this.hitController = new HitController(interactor);
        return this;
    }

    public AppBuilder addRestartGameUseCase() {
        final RestartGameViewModel viewModel = new RestartGameViewModel();
        final RestartGameOutputBoundary presenter =
                new RestartGamePresenter(viewModel, viewManagerModel);

        final RestartGameInputBoundary interactor =
                new RestartGameInteractor(presenter);
        final ArrayList<HistoryEntry> history = new ArrayList<>();

        this.restartGameController =
                new RestartGameController(interactor, new User(history));
        this.restartGameViewModel = viewModel;

        return this;
    }

    public AppBuilder addStatisticsUseCase() {
        final StatisticsViewModel viewModel = new StatisticsViewModel();
        final StatisticsOutputBoundary presenter =
                new StatisticsPresenter(viewModel);

        final StatisticsInputBoundary interactor =
                new StatisticsInteractor(presenter);

        final ArrayList<HistoryEntry> history = new ArrayList<>();
        this.statisticsController =
                new StatisticsController(interactor, new User(history));
        this.statisticsViewModel = viewModel;

        return this;
    }

    public AppBuilder addStandUseCase() {
        final StandOutputBoundary presenter =
                new ConsoleStandPresenter(viewManagerModel);
        final StandInputBoundary interactor =
                new StandInteractor(presenter);

        this.standController = new StandController(interactor);
        return this;
    }

    public AppBuilder addViewGameResultUseCase() {
        final ViewGameResultViewModel viewModel =
                new ViewGameResultViewModel();
        final ViewGameResultPresenter presenter =
                new ViewGameResultPresenter(viewModel, viewManagerModel);
        final ViewGameResultInteractor viewGameResultInteractor =
                new ViewGameResultInteractor(presenter);

        this.viewGameResultController =
                new ViewGameResultController(viewGameResultInteractor);
        this.viewGameResultViewModel = viewModel;

        return this;
    }

    public AppBuilder addViewHistoryUseCase() {
        viewHistoryViewModel = new ViewHistoryViewModel();
        ViewHistoryOutputBoundary presenter =
                new ViewHistoryPresenter(viewHistoryViewModel);

        ViewHistoryInputBoundary interactor =
                new ViewHistoryInteractor(presenter);

        this.viewHistoryController =
                new ViewHistoryController(interactor);

        return this;
    }


    public AppBuilder addHomePageView() {
        final HomePageView homePage =
                new HomePageView(startNewGameViewModel, startNewGameController);
        cardPanel.add(homePage, "Home");
        return this;
    }

    public AppBuilder addBlackJackGameView() throws IOException {
        final BlackJackGameView blackJackGameView =
                new BlackJackGameView(viewGameResultController,
                        startNewGameViewModel,restartGameViewModel,hitController,gameModel,standController);
        cardPanel.add(blackJackGameView, "Game");
        return this;
    }

    public AppBuilder addGameResultView() {
        final GameResultView gameResultView =
                new GameResultView(viewGameResultViewModel,
                        restartGameController, statisticsController, viewHistoryController, viewManagerModel);
        cardPanel.add(gameResultView, "GameResult");
        return this;
    }

    public AppBuilder addGameHistoryView() {
        historyView = new GameHistoryView(viewHistoryViewModel);
        cardPanel.add(historyView, "History");
        return this;
    }


    public AppBuilder setupViewManager() {
        viewManagerModel.addPropertyChangeListener(evt -> {
            if ("view".equals(evt.getPropertyName())) {
                final String activeView = (String) evt.getNewValue();

                if ("History".equals(activeView) && historyView != null) {
                    historyView.refresh();
                }

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
