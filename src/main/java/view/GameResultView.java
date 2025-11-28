package view;

import javax.swing.*;

import interfaceadapter.restartgame.RestartGameController;
import interfaceadapter.startnewgame.StartNewGameController;
import interfaceadapter.statistics.StatisticsController;
import interfaceadapter.viewgameresult.ViewGameResultViewModel;

public class GameResultView extends JPanel {
    // Game outcome message
    private final JLabel resultMessage;
    private final JButton newGameButton;
    private final JButton statsButton;
    private final JButton historyButton;
    private final JLabel playerFinalScore;
    private final JLabel dealerFinalScore;
    private RestartGameController restartGameController;
    private StatisticsController statisticsController;

    public GameResultView(ViewGameResultViewModel viewGameResultViewModel,RestartGameController restartGameController,
                          StatisticsController statisticsController) {

        this.restartGameController =  restartGameController;
        this.statisticsController= statisticsController;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        // Centered game result message
        resultMessage = new JLabel(viewGameResultViewModel.getGameResult());
        resultMessage.setAlignmentX(CENTER_ALIGNMENT);
        this.add(resultMessage);

        // Player's final score
        playerFinalScore = new JLabel(viewGameResultViewModel.getPlayerScoreString());
        playerFinalScore.setAlignmentX(CENTER_ALIGNMENT);
        this.add(playerFinalScore);

        // Dealer's final score
        dealerFinalScore = new JLabel(viewGameResultViewModel.getDealerScoreString());
        dealerFinalScore.setAlignmentX(CENTER_ALIGNMENT);
        this.add(dealerFinalScore);

        final JPanel buttonPanel = new JPanel();
        newGameButton = new JButton("Play Again");
        statsButton = new JButton("Statistics");
        historyButton = new JButton("Game History");


        newGameButton.addActionListener(event ->
           this.restartGameController.execute());

        statsButton.addActionListener(event ->
                this.statisticsController.execute());

        buttonPanel.add(newGameButton);
        buttonPanel.add(statsButton);
        buttonPanel.add(historyButton);
        this.add(buttonPanel);
    }

}
