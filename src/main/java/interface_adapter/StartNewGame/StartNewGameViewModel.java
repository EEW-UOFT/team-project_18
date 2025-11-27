package interface_adapter.StartNewGame;

import entity.CurrentGame;

public class StartNewGameViewModel {

    public static final String TITLE_LABEL = "HomePage";
    public String errorMessage;
    private CurrentGame currentGame;

    public StartNewGameViewModel(CurrentGame currentGame) {
        this.currentGame = currentGame;
    }

    public CurrentGame getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(CurrentGame currentGame) {
        this.currentGame = currentGame;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


}