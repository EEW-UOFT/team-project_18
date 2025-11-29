package interfaceadapter.viewgamehistory;
import entity.HistoryEntry;

import java.util.ArrayList;
import java.util.List;

public class ViewHistoryViewModel {
    private List<HistoryEntry> history = new ArrayList<>();

    public List<HistoryEntry> getHistory() {
        return history;
    }

    public void setHistory(List<HistoryEntry> history) {
        this.history = history;
    }
}
