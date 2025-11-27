package interface_adapter.restartgame;

import entity.User;
import use_case.restartgame.RestartGameInputBoundary;

public class RestartGameController {

    private final RestartGameInputBoundary restartGameInteractor;
    private final User currentUser;

    public RestartGameController(RestartGameInputBoundary restartGameInteractor,
                                 User currentUser) {
        this.restartGameInteractor = restartGameInteractor;
        this.currentUser = currentUser;
    }

    public void execute() {
        restartGameInteractor.execute(this.currentUser);
    }
}