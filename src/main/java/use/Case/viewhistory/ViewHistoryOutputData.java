package use.Case.viewhistory;

import entity.HistoryEntry;
import java.util.List;

public class ViewHistoryOutputData {
    private final List<HistoryEntry> entries;

    public ViewHistoryOutputData(List<HistoryEntry> entries) {
        this.entries = entries;
    }

    public List<HistoryEntry> getEntries() {
        return entries;
    }
}
