package entity;

import java.util.ArrayList;
import java.util.List;

public class GameHistory {

    private List<HistoryEntry> history;

    public GameHistory() {
        this.history = new ArrayList<>();
    }

    public void addEntry(HistoryEntry entry) {

        this.history.add(entry);
    }

    public List<HistoryEntry> getHistory() {
        return history;
    }
}
