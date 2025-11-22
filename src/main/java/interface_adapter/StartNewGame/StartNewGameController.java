package interface_adapter.StartNewGame;

import entity.User;
import use_case.StartNewGame.StartNewGameInputBoundary;

public class StartNewGameController {

    private StartNewGameInputBoundary StartNewGameInteractor;
    private User currentUser;

    public StartNewGameController(StartNewGameInputBoundary StartNewGameInteractor, User currentUser) {
        this.StartNewGameInteractor = StartNewGameInteractor;
        this.currentUser = currentUser;
    }

    public void execute() {
        StartNewGameInteractor.execute(this.currentUser);
    }

}
