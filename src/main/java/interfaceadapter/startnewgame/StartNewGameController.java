package interfaceadapter.startnewgame;

import entity.*;
import use.Case.startnewgame.StartNewGameInputBoundary;

public class StartNewGameController {

    private StartNewGameInputBoundary startNewGameInteractor;
    private User currentUser;

    public StartNewGameController(StartNewGameInputBoundary StartNewGameInteractor, User currentUser) {
        this.startNewGameInteractor = StartNewGameInteractor;
        this.currentUser = currentUser;
    }

    public void execute() {
        startNewGameInteractor.execute(this.currentUser);
    }

}
