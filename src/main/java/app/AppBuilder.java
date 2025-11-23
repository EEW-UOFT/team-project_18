package app;

import data_access.Deck;
import data_access.DeckAPIInterface;
import entity.HistoryEntry;
import entity.User;
import interface_adapter.StartNewGame.StartNewGameController;
import interface_adapter.StartNewGame.StartNewGamePresenter;
import interface_adapter.StartNewGame.StartNewGameViewModel;
import interface_adapter.ViewManagerModel;
import use_case.startnewgame.StartNewGameInputBoundary;
import use_case.startnewgame.StartNewGameInteractor;
import use_case.startnewgame.StartNewGameOutputBoundary;
import view.BlackJackGameView;
import view.HomePageView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AppBuilder {

    private StartNewGameController startNewGameController;
    private StartNewGameViewModel startNewGameViewModel;
    private final ViewManagerModel viewManagerModel;

    public AppBuilder() {
        this.viewManagerModel = new ViewManagerModel();
        addStartNewGameUseCase();
    }

    public void addStartNewGameUseCase() {
        try {
            StartNewGameViewModel viewModel = new StartNewGameViewModel();
            StartNewGameOutputBoundary presenter = new StartNewGamePresenter(viewModel, viewManagerModel);

            DeckAPIInterface deck = new Deck();
            StartNewGameInputBoundary interactor = new StartNewGameInteractor(presenter, deck);
            ArrayList<HistoryEntry> history = new ArrayList<>();
            this.startNewGameController = new StartNewGameController(interactor, new User(history));
            this.startNewGameViewModel = viewModel;
        } catch (DeckAPIInterface.UnableToLoadDeck e) {
            System.err.println("Failed to create deck: " + e.getMessage());
        }

    }

    JFrame build() {
        JFrame frame = new JFrame("BlackJack");

        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);


        HomePageView homePage = new HomePageView(startNewGameViewModel, startNewGameController);
        BlackJackGameView gamePage = new BlackJackGameView();

        mainPanel.add(homePage,"home");
        mainPanel.add(gamePage,"game");

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
