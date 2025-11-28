package view;

import javax.swing.*;

import entity.HistoryEntry;
import interface_adapter.ViewGameResult.ViewGameResultViewModel;
import interface_adapter.ViewManagerModel;


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

        this.add(buttonPanel);

        JTextArea historyArea = new JTextArea(12, 40);
        historyArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(historyArea);
        this.add(scrollPane);

        // ---------------------------
        // Fill history text
        // ---------------------------
        StringBuilder sb = new StringBuilder();

        int index = 1;
        for (HistoryEntry entry :
                viewGameResultViewModel.getCurrentGame().getPlayer().getHistory()) {

            sb.append("Game #").append(index++).append("\n")
                    .append("Player: ").append(entry.getPlayerTotal()).append("\n")
                    .append("Dealer: ").append(entry.getDealerTotal()).append("\n")
                    .append("Outcome: ").append(entry.getOutcome()).append("\n")
                    .append("---------------------------\n");
        }

        historyArea.setText(sb.toString());


        buttonPanel.add(newGameButton);
        buttonPanel.add(statsButton);
        buttonPanel.add(historyButton);
        this.add(buttonPanel);
    }

}
