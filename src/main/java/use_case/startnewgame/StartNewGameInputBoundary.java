package use_case.startnewgame;

import entity.User;

public interface StartNewGameInputBoundary {


    void execute(User currentUser);
}
