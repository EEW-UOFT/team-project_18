package use_case.restartgame;

import entity.User;

public interface RestartGameInputBoundary {
    void execute(User currentUser);
}
