package use_case.restartgame;

public interface RestartGameOutputBoundary {
    void prepareSuccessView(RestartGameOutputData outputData);
    void prepareFailView(String errorMessage);
}
