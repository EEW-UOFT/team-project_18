package use.Case.startnewgame;

public interface StartNewGameOutputBoundary {

    void prepareSuccessView(StartNewGameOutputData outputData);

    void prepareFailView(String errorMessage);

}
