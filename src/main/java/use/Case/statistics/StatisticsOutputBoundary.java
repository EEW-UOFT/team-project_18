package use.Case.statistics;

public interface StatisticsOutputBoundary {
    void prepareSuccessView(StatisticsOutputData outputData);

    void prepareFailView(String errorMessage);
}
