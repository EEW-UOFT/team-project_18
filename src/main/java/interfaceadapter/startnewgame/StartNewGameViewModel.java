package interfaceadapter.startnewgame;

import entity.CurrentGame;

public class StartNewGameViewModel {

    public static final String TITLE_LABEL = "HomePage";
    private CurrentGame currentGame;
    private String errorMessage;

    public StartNewGameViewModel() {
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
