package use_case.statistics;

public interface StatisticsOutputBoundary {
    void prepareSuccessView(StatisticsOutputData outputData);
    void prepareFailView(String errorMessage);
}
