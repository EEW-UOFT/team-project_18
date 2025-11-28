package entity;

import java.util.List;

public class User {
    private List<HistoryEntry> gameHistory;

    public User(List<HistoryEntry> gameHistory) {
        this.gameHistory = gameHistory;
    }

    public void addHistoryEntry(HistoryEntry entry) {
        gameHistory.add(entry);
    }

    public List<HistoryEntry> getHistory() {
        return gameHistory;
    }
}
