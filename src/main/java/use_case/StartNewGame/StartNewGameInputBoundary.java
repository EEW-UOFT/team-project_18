package use_case.StartNewGame;

import entity.User;

public interface StartNewGameInputBoundary {


    void execute(User currentUser);
}
