package use.Case.restartgame;

public interface RestartGameOutputBoundary {
    void prepareSuccessView(RestartGameOutputData outputData);

    void prepareFailView(String errorMessage);
}
