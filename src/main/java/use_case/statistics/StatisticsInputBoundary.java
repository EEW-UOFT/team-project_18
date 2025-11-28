package use_case.statistics;

import entity.User;

public interface StatisticsInputBoundary {
    void execute(User currentUser);
}
