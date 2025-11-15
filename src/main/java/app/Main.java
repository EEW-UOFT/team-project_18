package app;

import entity.CurrentGame;
import entity.Deck;
import entity.User;
import interface_adapter.StartNewGame.StartNewGameViewModel;
import view.HomePageView;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame("BlackJack");

        StartNewGameViewModel viewModel = new StartNewGameViewModel(null);

        HomePageView homePage = new HomePageView(viewModel, null);

        frame.add(homePage);
        frame.setSize(250, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}
