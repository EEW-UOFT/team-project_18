package app;

import entity.CurrentGame;
import data_access.Deck;
import entity.HistoryEntry;
import entity.User;
import interface_adapter.StartNewGame.StartNewGameController;
import interface_adapter.StartNewGame.StartNewGamePresenter;
import interface_adapter.StartNewGame.StartNewGameViewModel;
import use_case.StartNewGame.StartNewGameInteractor;
import view.HomePageView;

import javax.swing.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        AppBuilder appBuilder = new AppBuilder();
        JFrame application = appBuilder.build();
        application.setVisible(true);

    }

}
