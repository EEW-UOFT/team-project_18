package interfaceadapter.restartgame;

import entity.CurrentGame;

public class RestartGameViewModel {

    private CurrentGame currentGame;
    private String errorMessage;

    public void setCurrentGame(CurrentGame currentGame) {
        this.currentGame = currentGame;
    }

    public CurrentGame getCurrentGame() {
        return currentGame;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
