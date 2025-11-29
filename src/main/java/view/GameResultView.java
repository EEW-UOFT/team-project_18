package view;

import interfaceadapter.restartgame.RestartGameController;
import interfaceadapter.statistics.StatisticsController;
import interfaceadapter.viewgameresult.ViewGameResultViewModel;
import interfaceadapter.viewgamehistory.ViewHistoryController;
import interfaceadapter.ViewManagerModel;

import javax.swing.*;

public class GameResultView extends JPanel {

    // Game outcome message
    private final JLabel resultMessage;
    private final JButton newGameButton;
    private final JButton statsButton;
    private final JButton historyButton;
    private final JLabel playerFinalScore;
    private final JLabel dealerFinalScore;

    private final RestartGameController restartGameController;
    private final StatisticsController statisticsController;
    private final ViewHistoryController viewHistoryController;
    private final ViewManagerModel viewManagerModel;

    public GameResultView(ViewGameResultViewModel viewGameResultViewModel,
                          RestartGameController restartGameController,
                          StatisticsController statisticsController,
                          ViewHistoryController viewHistoryController,
                          ViewManagerModel viewManagerModel) {

        this.restartGameController = restartGameController;
        this.statisticsController = statisticsController;
        this.viewHistoryController = viewHistoryController;
        this.viewManagerModel = viewManagerModel;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // -----------------------------
        // Result labels
        // -----------------------------
        resultMessage = new JLabel(viewGameResultViewModel.getGameResult());
        resultMessage.setAlignmentX(CENTER_ALIGNMENT);
        this.add(resultMessage);

        playerFinalScore = new JLabel(viewGameResultViewModel.getPlayerScoreString());
        playerFinalScore.setAlignmentX(CENTER_ALIGNMENT);
        this.add(playerFinalScore);

        dealerFinalScore = new JLabel(viewGameResultViewModel.getDealerScoreString());
        dealerFinalScore.setAlignmentX(CENTER_ALIGNMENT);
        this.add(dealerFinalScore);

        // -----------------------------
        // Buttons
        // -----------------------------
        JPanel buttonPanel = new JPanel();
        newGameButton = new JButton("Play Again");
        statsButton = new JButton("Statistics");
        historyButton = new JButton("Game History");

        newGameButton.addActionListener(e -> restartGameController.execute());
        statsButton.addActionListener(e -> statisticsController.execute());

        historyButton.addActionListener(e -> {
            viewHistoryController.execute(
                    viewGameResultViewModel.getCurrentGame().getPlayer()
            );
            viewManagerModel.setActiveView("History");
        });

        buttonPanel.add(newGameButton);
        buttonPanel.add(statsButton);
        buttonPanel.add(historyButton);
        this.add(buttonPanel);
    }
}
