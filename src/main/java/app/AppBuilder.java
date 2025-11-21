package app;

import entity.HistoryEntry;
import entity.User;
import interface_adapter.StartNewGame.StartNewGameController;
import interface_adapter.StartNewGame.StartNewGamePresenter;
import interface_adapter.StartNewGame.StartNewGameViewModel;
import use_case.StartNewGame.StartNewGameInteractor;
import view.HomePageView;

import javax.swing.*;
import java.util.ArrayList;

public class AppBuilder {

    private StartNewGameController startNewGameController;
    private StartNewGameViewModel startNewGameViewModel;

    public AppBuilder() {
        addStartNewGameUseCase();
    }

    public void addStartNewGameUseCase() {
        StartNewGameViewModel viewModel = new StartNewGameViewModel(null);
        StartNewGamePresenter presenter = new StartNewGamePresenter(viewModel);
        StartNewGameInteractor interactor = new StartNewGameInteractor(presenter);
        ArrayList<HistoryEntry> history = new ArrayList<>();
        StartNewGameController controller = new StartNewGameController(interactor, new User(history));
        this.startNewGameController = controller;
        this.startNewGameViewModel = viewModel;
    }

    JFrame build() {
        JFrame frame = new JFrame("BlackJack");
        HomePageView homePage = new HomePageView(startNewGameViewModel, startNewGameController);

        frame.add(homePage);
        frame.setSize(250, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        System.out.println("hi");
        return frame;
    }
}
