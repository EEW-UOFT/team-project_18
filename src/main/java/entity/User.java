package entity;

import java.util.List;

public class User {
    private List<HistoryEntry> gameHistory;

    public User(List<HistoryEntry> gameHistory) {
        this.gameHistory = gameHistory;
    }
}
