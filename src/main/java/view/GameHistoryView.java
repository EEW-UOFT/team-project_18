package view;
import interfaceadapter.ViewManagerModel;
import interfaceadapter.viewgamehistory.ViewHistoryViewModel;
import entity.HistoryEntry;
import javax.swing.*;
import java.awt.*;


public class GameHistoryView extends JPanel {
    private final JTextArea historyArea;
    private final ViewHistoryViewModel viewModel;

    public GameHistoryView(ViewHistoryViewModel viewModel, ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        this.setLayout(new BorderLayout());

        historyArea = new JTextArea();
        historyArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(historyArea);
        this.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e ->
                viewManagerModel.setActiveView("GameResult")
        );

        this.add(backButton, BorderLayout.SOUTH);
    }

    public void refresh() {
        StringBuilder sb = new StringBuilder();
        int index = 1;

        if (viewModel.getHistory().isEmpty()) {
            sb.append("No game history available.");
        } else {
            for (HistoryEntry entry : viewModel.getHistory()) {
                sb.append("Game #").append(index++).append("\n")
                        .append("Player: ").append(entry.getPlayerTotal()).append("\n")
                        .append("Dealer: ").append(entry.getDealerTotal()).append("\n")
                        .append("Outcome: ").append(entry.getOutcome()).append("\n")
                        .append("----------------------\n");
            }
        }

        historyArea.setText(sb.toString());
    }
}

