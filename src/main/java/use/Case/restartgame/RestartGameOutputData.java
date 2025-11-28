package use.Case.restartgame;

import entity.CurrentGame;

public class RestartGameOutputData {
    private final CurrentGame currentGame;

    public RestartGameOutputData(CurrentGame currentGame) {
        this.currentGame = currentGame;
    }

    public CurrentGame getCurrentGame() {
        return currentGame;
    }
}
