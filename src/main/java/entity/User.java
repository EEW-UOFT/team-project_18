package entity;

import java.io.*;
import java.util.List;

public class User {
    private List<HistoryEntry> gameHistory;
    private static final String HISTORY_FILE = "game_history.txt";

    public User(List<HistoryEntry> gameHistory) {

        this.gameHistory = gameHistory;
        loadHistoryFromFile();
    }

    public List<HistoryEntry> getGameHistory() {
        return gameHistory;
    }

    public void addGameHistory(HistoryEntry entry) {
        if (gameHistory != null) {
            gameHistory.add(entry);
        }
        saveHistoryToFile();
    }

    private void saveHistoryToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(HISTORY_FILE))) {
            for (HistoryEntry entry : gameHistory) {
                writer.println(entry.getTimeStamp() + "," +
                        entry.getPlayerTotal() + "," +
                        entry.getDealerTotal() + "," +
                        entry.getOutcome());
            }
        } catch (IOException e) {
        }
    }

    private void loadHistoryFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(HISTORY_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    gameHistory.add(new HistoryEntry(
                            Integer.parseInt(parts[0]),
                            Integer.parseInt(parts[1]),
                            Integer.parseInt(parts[2]),
                            parts[3]
                    ));
                }
            }
        } catch (IOException e) {
        }
    }

}