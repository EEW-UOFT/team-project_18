package entity;

public class HistoryEntry {
    private final int timeStamp;
    private final int playerTotal;
    private final int dealerTotal;
    private final String outcome;

    public HistoryEntry(int timeStamp, int playerTotal, int dealerTotal, String outcome) {
        this.timeStamp = timeStamp;
        this.playerTotal = playerTotal;
        this.dealerTotal = dealerTotal;
        this.outcome = outcome;
    }

    public int getTimeStamp() {return timeStamp;}

    public int getPlayerTotal() {return playerTotal;}

    public int getDealerTotal() {return dealerTotal;}

    public String getOutcome(){return outcome;}




}
