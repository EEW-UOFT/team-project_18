package view;

import interfaceadapter.restartgame.RestartGameController;
import interfaceadapter.statistics.StatisticsController;
import interfaceadapter.viewgameresult.ViewGameResultViewModel;
import interfaceadapter.viewgamehistory.ViewHistoryController;
import interfaceadapter.ViewManagerModel;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class GameResultView extends JPanel implements PropertyChangeListener {
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

    public GameResultView(ViewGameResultViewModel viewGameResultViewModel, RestartGameController restartGameController,
                          StatisticsController statisticsController,
                          ViewHistoryController viewHistoryController,
                          ViewManagerModel viewManagerModel) {
        viewGameResultViewModel.addPropertyChangeListener(this);

        this.restartGameController = restartGameController;
        this.statisticsController = statisticsController;
        this.viewHistoryController = viewHistoryController;
        this.viewManagerModel = viewManagerModel;

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
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "gameResult":
                resultMessage.setText((String) evt.getNewValue());
                break;
            case "playerScore":
                playerFinalScore.setText((String) evt.getNewValue());
                break;
            case "dealerScore":
                dealerFinalScore.setText((String) evt.getNewValue());
                break;
        }
    }

}
