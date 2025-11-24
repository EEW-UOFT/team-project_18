package use_case.ViewGameResult;

import entity.CurrentGame;

public interface ViewGameResultInputBoundary {
    void execute(CurrentGame currentGame);
}
