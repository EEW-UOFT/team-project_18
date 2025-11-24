package interface_adapter.startnewgame;

import entity.*;
import use_case.startnewgame.StartNewGameInputBoundary;

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
