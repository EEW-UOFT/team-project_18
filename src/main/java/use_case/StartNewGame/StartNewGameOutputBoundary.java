package use_case.StartNewGame;

public interface StartNewGameOutputBoundary {

    void prepareSuccessView(StartNewGameOutputData outputData);

    void prepareFailView(String errorMessage);

}
