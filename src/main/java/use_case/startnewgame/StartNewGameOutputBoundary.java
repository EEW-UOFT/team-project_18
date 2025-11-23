package use_case.startnewgame;

public interface StartNewGameOutputBoundary {

    void prepareSuccessView(StartNewGameOutputData outputData);

    void prepareFailView(String errorMessage);

}
