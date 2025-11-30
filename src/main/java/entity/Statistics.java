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
            String outcome = entry.getOutcome().toLowerCase();

            if (outcome.contains("player wins") || outcome.contains("player won")) {
                wins++;
                currentStreak++;
                if (currentStreak > longestStreak) {
                    longestStreak = currentStreak;
                }
            } else if (outcome.contains("dealer wins") || outcome.contains("dealer won")) {
                losses++;
                currentStreak = 0;
            } else if (outcome.contains("push") || outcome.contains("draw")) {
                ties++;
                currentStreak = 0;
            } else {
                System.out.println("未知游戏结果: " + outcome);
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
