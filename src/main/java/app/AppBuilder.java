package app;

import data_access.Deck;
import data_access.DeckAPIInterface;
import entity.HistoryEntry;
import entity.User;
import interface_adapter.StartNewGame.StartNewGameController;
import interface_adapter.StartNewGame.StartNewGamePresenter;
import interface_adapter.StartNewGame.StartNewGameViewModel;
import interface_adapter.ViewGameResult.ViewGameResultController;
import interface_adapter.ViewGameResult.ViewGameResultPresenter;
import interface_adapter.ViewGameResult.ViewGameResultViewModel;
import interface_adapter.ViewManagerModel;
import use_case.startnewgame.StartNewGameInputBoundary;
import use_case.startnewgame.StartNewGameInteractor;
import use_case.startnewgame.StartNewGameOutputBoundary;
import use_case.viewgameresult.ViewGameResultInteractor;
import view.BlackJackGameView;
import view.GameResultView;
import view.HomePageView;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class AppBuilder {

    private StartNewGameController startNewGameController;
    private StartNewGameViewModel startNewGameViewModel;
    private final ViewManagerModel viewManagerModel;

    private ViewGameResultController viewGameResultController;
    private ViewGameResultViewModel viewGameResultViewModel;

    public AppBuilder() {
        this.viewManagerModel = new ViewManagerModel();
        addStartNewGameUseCase();
        addViewGameResultUseCase();
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

    public void addViewGameResultUseCase() {
        ViewGameResultViewModel viewModel = new ViewGameResultViewModel();
        ViewGameResultPresenter presenter = new ViewGameResultPresenter(viewModel, viewManagerModel);
        ViewGameResultInteractor viewGameResultInteractor = new ViewGameResultInteractor(presenter);
        this.viewGameResultController = new ViewGameResultController(viewGameResultInteractor);
        this.viewGameResultViewModel = viewModel;
        System.out.println("AppBuilder viewManagerModel hash in addViewGameResultUseCase:");
        System.out.println(System.identityHashCode(viewManagerModel));
    }

    JFrame build() throws IOException {
        JFrame frame = new JFrame("BlackJack");

        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);


        HomePageView homePage = new HomePageView(startNewGameViewModel, startNewGameController);
        BlackJackGameView gamePage = new BlackJackGameView(viewGameResultController, startNewGameViewModel);
        GameResultView resultPage = new GameResultView(viewGameResultViewModel);

        System.out.println("Appbuilder viewGameResultController hash in build()");
        System.out.println(System.identityHashCode(viewGameResultController));

        mainPanel.add(homePage,"Home");
        mainPanel.add(gamePage,"Game");
        mainPanel.add(resultPage, "Result");

        System.out.println("AppBuilder viewManagerModel hash in build():");
        System.out.println(System.identityHashCode(viewManagerModel));

        viewManagerModel.addPropertyChangeListener(evt ->{
            System.out.println("Property changed: " + evt.getPropertyName());
            if("view".equals(evt.getPropertyName())){
                String activeView = (String) evt.getNewValue();
                System.out.println("Switching to view: " + activeView);
                cardLayout.show(mainPanel, activeView);
            }

            System.out.println("Current viewManagerModel hash in listener:");
            System.out.println(System.identityHashCode(viewManagerModel) + "\n" );
        });

        frame.add(mainPanel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        System.out.println("hi");
        return frame;
    }
}
