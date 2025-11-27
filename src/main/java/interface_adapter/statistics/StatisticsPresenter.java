package interface_adapter.statistics;

import interface_adapter.ViewManagerModel;
import use_case.statistics.StatisticsOutputBoundary;
import use_case.statistics.StatisticsOutputData;

public class StatisticsPresenter implements StatisticsOutputBoundary {

    private final StatisticsViewModel statisticsViewModel;
    private final ViewManagerModel viewManagerModel;

    public StatisticsPresenter(StatisticsViewModel statisticsViewModel,
                               ViewManagerModel viewManagerModel) {
        this.statisticsViewModel = statisticsViewModel;
        this.viewManagerModel = viewManagerModel;
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
        viewManagerModel.setActiveView("Statistics");
    }

    @Override
    public void prepareFailView(String errorMessage) {
        statisticsViewModel.setMessage(errorMessage);
        viewManagerModel.setActiveView("Statistics");
    }
}
