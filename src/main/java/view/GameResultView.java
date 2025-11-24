package view;

import javax.swing.*;
import interface_adapter.viewgameresult.ViewGameResultViewModel;


public class GameResultView extends JPanel {
    // Game outcome message
    private final JLabel resultMessage;
    private final JButton newGameButton;
    private final JButton statsButton;
    private final JButton historyButton;
    private final JLabel playerFinalScore;
    private final JLabel dealerFinalScore;

    public GameResultView(ViewGameResultViewModel viewGameResultViewModel) {

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

        // Horizontally aligned buttons for new game, stats, and history
        JPanel buttonPanel = new JPanel();
        newGameButton = new JButton("New Game");
        statsButton = new JButton("Stats");
        historyButton = new JButton("History");
        buttonPanel.add(newGameButton);
        buttonPanel.add(statsButton);
        buttonPanel.add(historyButton);
        this.add(buttonPanel);
    }

}
