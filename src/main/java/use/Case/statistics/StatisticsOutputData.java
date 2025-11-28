package use.Case.statistics;

public class StatisticsOutputData {

    private final int totalGames;
    private final int wins;
    private final int losses;
    private final int ties;
    private final double winRate;
    private final int longestWinStreak;

    public StatisticsOutputData(int totalGames, int wins, int losses, int ties,
                                double winRate, int longestWinStreak) {
        this.totalGames = totalGames;
        this.wins = wins;
        this.losses = losses;
        this.ties = ties;
        this.winRate = winRate;
        this.longestWinStreak = longestWinStreak;
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
