package interface_adapter.statistics;

import entity.User;
import use_case.statistics.StatisticsInputBoundary;

public class StatisticsController {

    private final StatisticsInputBoundary statisticsInteractor;
    private final User currentUser;

    public StatisticsController(StatisticsInputBoundary statisticsInteractor, User currentUser) {
        this.statisticsInteractor = statisticsInteractor;
        this.currentUser = currentUser;
    }

    public void execute() {
        statisticsInteractor.execute(currentUser);
    }
}
