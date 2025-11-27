package use_case.statistics;

import entity.HistoryEntry;
import entity.Statistics;
import entity.User;

import java.util.List;

public class StatisticsInteractor implements StatisticsInputBoundary {

    private final StatisticsOutputBoundary statisticsPresenter;

    public StatisticsInteractor(StatisticsOutputBoundary statisticsPresenter) {
        this.statisticsPresenter = statisticsPresenter;
    }

    @Override
    public void execute(User currentUser) {
        List<HistoryEntry> history = currentUser.getGameHistory();

        if (history == null || history.isEmpty()) {
            statisticsPresenter.prepareFailView("No statistics available yet.");
            return;
        }

        Statistics stats = new Statistics(history);

        StatisticsOutputData outputData = new StatisticsOutputData(
                stats.getTotalGames(),
                stats.getWins(),
                stats.getLosses(),
                stats.getTies(),
                stats.getWinRate(),
                stats.getLongestWinStreak()
        );

        statisticsPresenter.prepareSuccessView(outputData);
    }
}
