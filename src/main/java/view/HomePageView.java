package view;

import interface_adapter.StartNewGame.StartNewGameController;
import interface_adapter.StartNewGame.StartNewGameViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class HomePageView extends JPanel {

    private final StartNewGameViewModel startNewGameViewModel;
    private final JButton startNewGame;
    private final JButton gameRule;
    private StartNewGameController startNewGameController= null;

    public HomePageView(StartNewGameViewModel startNewGameViewModel,
                        StartNewGameController startNewGameController) {

        this.startNewGameViewModel = startNewGameViewModel;

        JLabel title =  new JLabel("Home Page");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.setLayout(new GridBagLayout());

        final JPanel  buttons = new JPanel();
        this.gameRule = new JButton("？");
        buttons.add(gameRule);
        this.startNewGame = new JButton("Start New Game");
        buttons.add(startNewGame);

        this.add(buttons);


        startNewGame.addActionListener(event -> {
            startNewGameController.execute();

            switchToGamePage();

        });
    }

    private void switchToGamePage() {

    }
}

