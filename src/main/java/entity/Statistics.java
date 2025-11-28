package entity;

import java.util.List;

public class Statistics {

    private final int totalGames;
    private final int wins;
    private final int losses;
    private final int ties;
    private final double winRate;
    private final int longestWinStreak;

    public Statistics(List<HistoryEntry> history) {
        int wins = 0;
        int losses = 0;
        int ties = 0;
        int currentStreak = 0;
        int longestStreak = 0;

        for (HistoryEntry entry : history) {
            String outcome = entry.getOutcome(); // you already have this

            if ("WIN".equalsIgnoreCase(outcome) || "Player Won".equalsIgnoreCase(outcome)) {
                wins++;
                currentStreak++;
                if (currentStreak > longestStreak) {
                    longestStreak = currentStreak;
                }
            } else if ("LOSS".equalsIgnoreCase(outcome) || "Dealer Won".equalsIgnoreCase(outcome)) {
                losses++;
                currentStreak = 0;
            } else {
                // treat everything else (e.g. "TIE", "Draw") as tie
                ties++;
                currentStreak = 0;
            }
        }

        int total = wins + losses + ties;
        double winRate = total == 0 ? 0.0 : (wins * 100.0) / total;

        this.totalGames = total;
        this.wins = wins;
        this.losses = losses;
        this.ties = ties;
        this.winRate = winRate;
        this.longestWinStreak = longestStreak;
    }

    public int getTotalGames() {
        return totalGames;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getTies() {
        return ties;
    }

    public double getWinRate() {
        return winRate;
    }

    public int getLongestWinStreak() {
        return longestWinStreak;
    }
}
