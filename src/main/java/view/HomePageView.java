package view;

import interface_adapter.startnewgame.StartNewGameController;
import interface_adapter.startnewgame.StartNewGameViewModel;
import interface_adapter.statistics.StatisticsController;
import interface_adapter.statistics.StatisticsViewModel;

import javax.swing.*;
import java.awt.*;


public class HomePageView extends JPanel {

    private final JButton startNewGame;
    private final JButton gameRule;
    private final JButton statisticsButton;
    private StartNewGameController startNewGameController;
    private final StatisticsController statisticsController;
    private final JLabel errorLabel;

    public HomePageView(StartNewGameViewModel startNewGameViewModel,
                        StartNewGameController startNewGameController, StatisticsViewModel statisticsViewModel,
                        StatisticsController statisticsController) {

        this.startNewGameController = startNewGameController;
        this.statisticsController = statisticsController;

        JLabel title =  new JLabel("Home Page");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        final JPanel  buttons = new JPanel();
        this.gameRule = new JButton("？");
        buttons.add(gameRule);
        this.startNewGame = new JButton("Start New Game");
        buttons.add(startNewGame);
        this.statisticsButton = new JButton("Statistics");
        buttons.add(statisticsButton);

        errorLabel = new JLabel("", SwingConstants.CENTER);
        errorLabel.setForeground(Color.RED);
        errorLabel.setVisible(false);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 10, 0);
        this.add(buttons, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        this.add(errorLabel, gbc);

        startNewGame.addActionListener(event ->{
            errorLabel.setVisible(false);
            errorLabel.setText("");
            startNewGameController.execute();
        });

        startNewGame.addActionListener(event ->{
            errorLabel.setVisible(false);
            errorLabel.setText("");
            startNewGameController.execute();
        });

        statisticsButton.addActionListener(event -> {
            statisticsController.execute();
        });
    }
    public void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        this.revalidate();
        this.repaint();
    }
    public void setController(StartNewGameController controller) {
        this.startNewGameController = controller;
    }
}

