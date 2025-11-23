package app;

import entity.HistoryEntry;
import entity.User;
import interface_adapter.StartNewGame.StartNewGameController;
import interface_adapter.StartNewGame.StartNewGamePresenter;
import interface_adapter.StartNewGame.StartNewGameViewModel;
import interface_adapter.ViewManagerModel;
import use_case.StartNewGame.StartNewGameInputBoundary;
import use_case.StartNewGame.StartNewGameInteractor;
import use_case.StartNewGame.StartNewGameOutputBoundary;
import view.HomePageView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AppBuilder {

    private StartNewGameController startNewGameController;
    private StartNewGameViewModel startNewGameViewModel;
    private ViewManagerModel viewManagerModel;

    public AppBuilder() {
        this.viewManagerModel = new ViewManagerModel();
        addStartNewGameUseCase();
    }

    public void addStartNewGameUseCase() {
        StartNewGameViewModel viewModel = new StartNewGameViewModel();
        StartNewGameOutputBoundary presenter = new StartNewGamePresenter(viewModel, viewManagerModel);
        StartNewGameInputBoundary interactor = new StartNewGameInteractor(presenter);
        ArrayList<HistoryEntry> history = new ArrayList<>();
        StartNewGameController controller = new StartNewGameController(interactor, new User(history));
        this.startNewGameController = controller;
        this.startNewGameViewModel = viewModel;
    }

    JFrame build() {
        JFrame frame = new JFrame("BlackJack");

        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);


        HomePageView homePage = new HomePageView(startNewGameViewModel, startNewGameController);

        mainPanel.add(homePage,"HomePage");

        viewManagerModel.addPropertyChangeListener(evt ->{
            if("view".equals(evt.getPropertyName())){
                String activeView = (String) evt.getNewValue();
                cardLayout.show(mainPanel, activeView);
            }
        });

        frame.add(mainPanel);
        frame.setSize(250, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        System.out.println("hi");
        return frame;
    }
}
