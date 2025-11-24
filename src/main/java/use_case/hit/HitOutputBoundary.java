package use_case.hit;

import use_case.startnewgame.StartNewGameOutputData;

public interface HitOutputBoundary {

    void prepareSuccessView(HitOutputData outputData);

    void prepareFailView(String errorMessage);
}
