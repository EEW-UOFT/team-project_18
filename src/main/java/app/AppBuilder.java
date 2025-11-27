package app;

import data_access.Deck;
import data_access.DeckAPIInterface;
import entity.HistoryEntry;
import entity.User;
import interface_adapter.startnewgame.StartNewGameController;
import interface_adapter.startnewgame.StartNewGamePresenter;
import interface_adapter.startnewgame.StartNewGameViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.statistics.StatisticsController;
import interface_adapter.statistics.StatisticsPresenter;
import interface_adapter.statistics.StatisticsViewModel;
import use_case.startnewgame.StartNewGameInputBoundary;
import use_case.startnewgame.StartNewGameInteractor;
import use_case.startnewgame.StartNewGameOutputBoundary;
import use_case.statistics.StatisticsInputBoundary;
import use_case.statistics.StatisticsInteractor;
import use_case.statistics.StatisticsOutputBoundary;
import view.BlackJackGameView;
import view.HomePageView;
import interface_adapter.restartgame.RestartGameController;
import interface_adapter.restartgame.RestartGamePresenter;
import interface_adapter.restartgame.RestartGameViewModel;
import use_case.restartgame.RestartGameInputBoundary;
import use_case.restartgame.RestartGameInteractor;
import use_case.restartgame.RestartGameOutputBoundary;


import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class AppBuilder {

    private StartNewGameController startNewGameController;
    private StartNewGameViewModel startNewGameViewModel;
    private final ViewManagerModel viewManagerModel;

    private RestartGameController restartGameController;
    private RestartGameViewModel restartGameViewModel;

    private StatisticsController statisticsController;
    private StatisticsViewModel statisticsViewModel;

    public AppBuilder() {
        this.viewManagerModel = new ViewManagerModel();

        addStartNewGameUseCase();
        addRestartGameUseCase();
        addStatisticsUseCase();
    }

    public void addStartNewGameUseCase() {
        try {
            StartNewGameViewModel viewModel = new StartNewGameViewModel();
            StartNewGameOutputBoundary presenter = new StartNewGamePresenter(viewModel, viewManagerModel);

            DeckAPIInterface deck = new Deck();
            StartNewGameInputBoundary interactor = new StartNewGameInteractor(presenter);
            ArrayList<HistoryEntry> history = new ArrayList<>();
            this.startNewGameController = new StartNewGameController(interactor, new User(history));
            this.startNewGameViewModel = viewModel;
        } catch (DeckAPIInterface.UnableToLoadDeck e) {
            System.err.println("Failed to create deck: " + e.getMessage());
        }

    }

    public void addRestartGameUseCase() {
        RestartGameViewModel viewModel = new RestartGameViewModel();
        RestartGameOutputBoundary presenter =
                new RestartGamePresenter(viewModel, viewManagerModel);

        RestartGameInputBoundary interactor =
                new RestartGameInteractor(presenter);

        ArrayList<HistoryEntry> history = new ArrayList<>();
        this.restartGameController =
                new RestartGameController(interactor, new User(history));
        this.restartGameViewModel = viewModel;
    }

    public void addStatisticsUseCase() {
        StatisticsViewModel viewModel = new StatisticsViewModel();
        StatisticsOutputBoundary presenter =
                new StatisticsPresenter(viewModel, viewManagerModel);

        StatisticsInputBoundary interactor =
                new StatisticsInteractor(presenter);

        ArrayList<HistoryEntry> history = new ArrayList<>();
        this.statisticsController =
                new StatisticsController(interactor, new User(history));
        this.statisticsViewModel = viewModel;
    }


    JFrame build() throws IOException {
        JFrame frame = new JFrame("BlackJack");

        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);


        HomePageView homePage = new HomePageView(startNewGameViewModel, startNewGameController, statisticsViewModel, statisticsController);
        BlackJackGameView gamePage = new BlackJackGameView(restartGameController, restartGameViewModel);

        mainPanel.add(homePage,"Home");
        mainPanel.add(gamePage,"Game");

        viewManagerModel.addPropertyChangeListener(evt ->{
            if("view".equals(evt.getPropertyName())){
                String activeView = (String) evt.getNewValue();
                cardLayout.show(mainPanel, activeView);
            }
        });

        frame.add(mainPanel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        System.out.println("hi");
        return frame;
    }
}
