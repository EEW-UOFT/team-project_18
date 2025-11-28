package interfaceadapter.startnewgame;

import entity.User;
import use.Case.startnewgame.StartNewGameInputBoundary;

public class StartNewGameController {

    private final StartNewGameInputBoundary startNewGameInteractor;
    private final User currentUser;

    public StartNewGameController(StartNewGameInputBoundary StartNewGameInteractor, User currentUser) {
        this.startNewGameInteractor = StartNewGameInteractor;
        this.currentUser = currentUser;
    }

    public void execute() {
        startNewGameInteractor.execute(this.currentUser);
    }

}
