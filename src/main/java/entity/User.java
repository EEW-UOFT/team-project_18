package entity;

import java.util.List;

public class User {
    private List<HistoryEntry> gameHistory;

    public User(List<HistoryEntry> gameHistory) {
        this.gameHistory = gameHistory;
    }

    public List<HistoryEntry> getGameHistory() {
        return gameHistory;
    }

    // optional but nice if someone later wants to update it:
    public void setGameHistory(List<HistoryEntry> gameHistory) {
        this.gameHistory = gameHistory;
    }
}
