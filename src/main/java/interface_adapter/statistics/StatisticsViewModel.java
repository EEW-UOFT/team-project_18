package interface_adapter.statistics;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class StatisticsViewModel {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    private int totalGames;
    private int wins;
    private int losses;
    private int ties;
    private double winRate;
    private int longestWinStreak;
    private String message; // for "No statistics available yet." or errors

    public void setStats(int totalGames, int wins, int losses, int ties,
                         double winRate, int longestWinStreak) {
        this.totalGames = totalGames;
        this.wins = wins;
        this.losses = losses;
        this.ties = ties;
        this.winRate = winRate;
        this.longestWinStreak = longestWinStreak;
        this.message = null;
        support.firePropertyChange("statistics", null, null);
    }

    public void setMessage(String message) {
        this.message = message;
        support.firePropertyChange("statisticsMessage", null, message);
    }

    public int getTotalGames() { return totalGames; }
    public int getWins() { return wins; }
    public int getLosses() { return losses; }
    public int getTies() { return ties; }
    public double getWinRate() { return winRate; }
    public int getLongestWinStreak() { return longestWinStreak; }
    public String getMessage() { return message; }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}

