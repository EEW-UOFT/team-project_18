package interface_adapter.StartNewGame;

import entity.*;
import use_case.StartNewGame.StartNewGameInputBoundary;
import use_case.StartNewGame.StartNewGameInteractor;

public class StartNewGameController{

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
