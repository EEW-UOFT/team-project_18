package interfaceadapter.statistics;

import use.Case.statistics.StatisticsOutputBoundary;
import use.Case.statistics.StatisticsOutputData;

import javax.swing.*;

public class StatisticsPresenter implements StatisticsOutputBoundary {

    private final StatisticsViewModel statisticsViewModel;

    public StatisticsPresenter(StatisticsViewModel statisticsViewModel) {
        this.statisticsViewModel = statisticsViewModel;
    }

    @Override
    public void prepareSuccessView(StatisticsOutputData outputData) {
        statisticsViewModel.setStats(
                outputData.getTotalGames(),
                outputData.getWins(),
                outputData.getLosses(),
                outputData.getTies(),
                outputData.getWinRate(),
                outputData.getLongestWinStreak()
        );
        String statsText = generateStatisticsText(outputData);
        JOptionPane.showMessageDialog(
                null,
                statsText,
                "Game Statistics",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    @Override
    public void prepareFailView(String errorMessage) {
        statisticsViewModel.setMessage(errorMessage);
        JOptionPane.showMessageDialog(
                null,
                errorMessage,
                "Statistics Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    private String generateStatisticsText(StatisticsOutputData data) {
        return String.format(
                "📊 Game Statistics\n\n" +
                        "Overall Performance:\n" +
                        "• Total Games: %d\n" +
                        "• Wins: %d\n" +
                        "• Losses: %d\n" +
                        "• Ties: %d\n" +
                        "• Win Rate: %.1f%%\n" +
                        "• Longest Win Streak: %d\n\n" +
                        "Keep playing to improve your stats!",
                data.getTotalGames(),
                data.getWins(),
                data.getLosses(),
                data.getTies(),
                data.getWinRate(),
                data.getLongestWinStreak()
        );
    }
}
